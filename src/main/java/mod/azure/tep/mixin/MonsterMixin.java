package mod.azure.tep.mixin;

import java.util.Collections;
import java.util.Optional;
import java.util.function.BiConsumer;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;

import mod.azure.tep.config.TEPConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.AngerLevel;
import net.minecraft.world.entity.monster.warden.AngerManagement;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;

@Mixin(Monster.class)
public abstract class MonsterMixin extends PathfinderMob implements VibrationSystem {

	private static final Logger LOGGER = LogUtils.getLogger();
	public DynamicGameEventListener<VibrationSystem.Listener> dynamicGameEventListener;
	private VibrationSystem.User vibrationUser;
	private VibrationSystem.Data vibrationData;
	private AngerManagement angerManagement = new AngerManagement(this::canTargetEntity, Collections.emptyList());
	private static final EntityDataAccessor<Integer> CLIENT_ANGER_LEVEL = SynchedEntityData.defineId(Monster.class, EntityDataSerializers.INT);

	protected MonsterMixin(EntityType<? extends PathfinderMob> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(at = @At("TAIL"), method = "<init>")
	private void addShitz(EntityType<? extends PathfinderMob> entityType, Level level, CallbackInfo cir) {
		this.dynamicGameEventListener = new DynamicGameEventListener<VibrationSystem.Listener>(new VibrationSystem.Listener(this));
	}

	public int getClientAngerLevel() {
		return this.entityData.get(CLIENT_ANGER_LEVEL);
	}

	private void syncClientAngerLevel() {
		this.entityData.set(CLIENT_ANGER_LEVEL, this.getActiveAnger());
	}

	public AngerLevel getAngerLevel() {
		return AngerLevel.byAnger(this.getActiveAnger());
	}

	private int getActiveAnger() {
		return this.angerManagement.getActiveAnger(this.getTarget());
	}

	public void clearAnger(Entity entity) {
		this.angerManagement.clearAnger(entity);
	}

	@VisibleForTesting
	public AngerManagement getAngerManagement() {
		return this.angerManagement;
	}

	public Optional<LivingEntity> getEntityAngryAt() {
		if (this.getAngerLevel().isAngry()) {
			return this.angerManagement.getActiveEntity();
		}
		return Optional.empty();
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public void defineSynchedData() {
		super.defineSynchedData();
		if (TEPConfig.SERVER.monsters_can_warden_sense.get() == true)
			this.entityData.define(CLIENT_ANGER_LEVEL, 0);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		if (TEPConfig.SERVER.monsters_can_warden_sense.get() == true) {
			VibrationSystem.Data.CODEC.encodeStart(NbtOps.INSTANCE, this.vibrationData).resultOrPartial(LOGGER::error).ifPresent(tag -> compound.put("listener", (Tag) tag));
			AngerManagement.codec(this::canTargetEntity).encodeStart(NbtOps.INSTANCE, this.angerManagement).resultOrPartial(LOGGER::error).ifPresent(tag -> compound.put("anger", (Tag) tag));
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (TEPConfig.SERVER.monsters_can_warden_sense.get() == true) {
			if (compound.contains("listener", 10)) {
				AngerManagement.codec(this::canTargetEntity).parse(new Dynamic<Tag>(NbtOps.INSTANCE, compound.get("anger"))).resultOrPartial(LOGGER::error).ifPresent(angerManagement -> {
					this.angerManagement = angerManagement;
				});
			}
			if (compound.contains("anger")) {
				AngerManagement.codec(this::canTargetEntity).parse(new Dynamic<Tag>(NbtOps.INSTANCE, compound.get("anger"))).resultOrPartial(LOGGER::error).ifPresent(angerManagement -> {
					this.angerManagement = angerManagement;
				});
				this.syncClientAngerLevel();
			}
		}
	}

	@Override
	public void tick() {
		super.tick();
		Level level = this.getCommandSenderWorld();
		if (level instanceof ServerLevel) {
			ServerLevel serverLevel = (ServerLevel) level;
			if (TEPConfig.SERVER.monsters_can_warden_sense.get() == true)
				VibrationSystem.Ticker.tick(serverLevel, this.vibrationData, this.vibrationUser);
		}
	}

	@Override
	public void updateDynamicGameEventListener(BiConsumer<DynamicGameEventListener<?>, ServerLevel> biConsumer) {
		Level level = this.getCommandSenderWorld();
		if (level instanceof ServerLevel) {
			ServerLevel serverLevel = (ServerLevel) level;
			if (TEPConfig.SERVER.monsters_can_warden_sense.get() == true)
				biConsumer.accept(this.dynamicGameEventListener, serverLevel);
		}
	}

	/*
	 * Enabled force condition propagation Lifted jumps to return sites
	 */
	@Contract(value = "null->false")
	public boolean canTargetEntity(@Nullable Entity entity) {
		if (!(entity instanceof LivingEntity))
			return false;
		LivingEntity livingEntity = (LivingEntity) entity;
		if (this.getCommandSenderWorld() != entity.getCommandSenderWorld())
			return false;
		if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity))
			return false;
		if (this.isAlliedTo(entity))
			return false;
		if (livingEntity.getType() == EntityType.ARMOR_STAND)
			return false;
		if (livingEntity instanceof Bat)
			return false;
		if (livingEntity.isInvulnerable())
			return false;
		if (livingEntity.isDeadOrDying())
			return false;
		if (!this.getCommandSenderWorld().getWorldBorder().isWithinBounds(livingEntity.getBoundingBox()))
			return false;
		if (TEPConfig.SERVER.monsters_can_warden_sense.get() == false)
			return false;
		return true;
	}

	class VibrationUser implements VibrationSystem.User {
		private final PositionSource positionSource;
		private final PathfinderMob mob;

		VibrationUser(PathfinderMob mob) {
			this.mob = mob;
			this.positionSource = new EntityPositionSource(mob, mob.getEyeHeight());
		}

		@Override
		public int getListenerRadius() {
			return TEPConfig.SERVER.monster_sensing_range.get();
		}

		@Override
		public PositionSource getPositionSource() {
			return this.positionSource;
		}

		@Override
		public TagKey<GameEvent> getListenableEvents() {
			return GameEventTags.VIBRATIONS;
		}

		@Override
		public boolean canTriggerAvoidVibration() {
			if (TEPConfig.SERVER.monsters_can_warden_sense.get() == false)
				return false;
			return true;
		}

		@Override
		public boolean canReceiveVibration(ServerLevel serverLevel, BlockPos blockPos, GameEvent gameEvent, GameEvent.Context context) {
			@SuppressWarnings("unused")
			LivingEntity livingEntity;
			if (mob.isNoAi() || mob.isDeadOrDying() || mob.getBrain().hasMemoryValue(MemoryModuleType.VIBRATION_COOLDOWN) || !serverLevel.getWorldBorder().isWithinBounds(blockPos)) {
				return false;
			}
			Entity entity = context.sourceEntity();
			return !(entity instanceof LivingEntity);
		}

		@Override
		public void onReceiveVibration(ServerLevel serverLevel, BlockPos blockPos, GameEvent gameEvent, @Nullable Entity entity, @Nullable Entity entity2, float f) {
			if (mob.isDeadOrDying())
				return;
			if (TEPConfig.SERVER.monsters_can_warden_sense.get() == true)
				mob.getNavigation().moveTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0.9F);
		}
	}

}
