package mod.azure.tep.mixin;

import java.util.function.BiConsumer;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;

import mod.azure.azurelib.helper.AzureTicker;
import mod.azure.tep.AzureVibrationUserTEP;
import mod.azure.tep.TotallyEnoughPainMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;

@Mixin(Monster.class)
public abstract class MonsterMixin extends PathfinderMob implements VibrationSystem {

	private static final Logger LOGGER = LogUtils.getLogger();
	public DynamicGameEventListener<VibrationSystem.Listener> dynamicGameEventListener;
	private VibrationSystem.User vibrationUser;
	private VibrationSystem.Data vibrationData;

	protected MonsterMixin(EntityType<? extends PathfinderMob> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(at = @At("TAIL"), method = "<init>")
	private void addShitz(EntityType<? extends PathfinderMob> entityType, Level level, CallbackInfo cir) {
		this.vibrationData = new VibrationSystem.Data();
		this.vibrationUser = new AzureVibrationUserTEP(this, 0.9F, TotallyEnoughPainMod.config.monster_sensing_range);
		this.dynamicGameEventListener = new DynamicGameEventListener<VibrationSystem.Listener>(new VibrationSystem.Listener(this));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		if (TotallyEnoughPainMod.config.monsters_can_warden_sense == true) {
			VibrationSystem.Data.CODEC.encodeStart(NbtOps.INSTANCE, this.vibrationData).resultOrPartial(LOGGER::error).ifPresent(tag -> compound.put("listener", (Tag) tag));
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (TotallyEnoughPainMod.config.monsters_can_warden_sense == true) {
			if (compound.contains("listener", 10)) {
				VibrationSystem.Data.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, compound.getCompound("listener"))).resultOrPartial(LOGGER::error).ifPresent(data -> {
					this.vibrationData = data;
				});
			}
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level()instanceof ServerLevel serverLevel)
			if (TotallyEnoughPainMod.config.monsters_can_warden_sense == true)
				AzureTicker.tick(serverLevel, this.vibrationData, this.vibrationUser);
	}

	@Override
	public VibrationSystem.Data getVibrationData() {
		return this.vibrationData;
	}

	@Override
	public VibrationSystem.User getVibrationUser() {
		return this.vibrationUser;
	}

	@Override
	public void updateDynamicGameEventListener(BiConsumer<DynamicGameEventListener<?>, ServerLevel> biConsumer) {
		if (level()instanceof ServerLevel serverLevel)
			biConsumer.accept(this.dynamicGameEventListener, serverLevel);
	}

	/*
	 * Enabled force condition propagation Lifted jumps to return sites
	 */
	@Contract(value = "null->false")
	public boolean canTargetEntity(@Nullable Entity entity) {
		if (!(entity instanceof LivingEntity))
			return false;
		LivingEntity livingEntity = (LivingEntity) entity;
		if (this.level() != entity.level())
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
		if (!this.level().getWorldBorder().isWithinBounds(livingEntity.getBoundingBox()))
			return false;
		if (TotallyEnoughPainMod.config.monsters_can_warden_sense == false)
			return false;
		return true;
	}

}
