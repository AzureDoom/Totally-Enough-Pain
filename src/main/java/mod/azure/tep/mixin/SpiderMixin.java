package mod.azure.tep.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.tep.TotallyEnoughPainMod;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

@Mixin(SpiderEntity.class)
public abstract class SpiderMixin extends HostileEntity {

	protected SpiderMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Inject(method = "initGoals", at = @At("HEAD"))
	private void attackGoals(CallbackInfo ci) {
		if (TotallyEnoughPainMod.config.spiders.spider_always_attack == true)
			this.targetSelector.add(1, new ActiveTargetGoal(this, PlayerEntity.class, false));
		if (TotallyEnoughPainMod.config.spiders.spider_attacks_villagers == true)
			this.targetSelector.add(1, new ActiveTargetGoal(this, MerchantEntity.class, false));
	}

	@Nullable
	@Overwrite
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
			@Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		if (TotallyEnoughPainMod.config.spiders.spider_always_jockeys == true || world.getRandom().nextInt(100) == 0) {
			SkeletonEntity skeletonEntity = (SkeletonEntity) EntityType.SKELETON.create(this.world);
			skeletonEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0F);
			skeletonEntity.initialize(world, difficulty, spawnReason, (EntityData) null, (NbtCompound) null);
			skeletonEntity.startRiding(this);
		}
		if (entityData == null) {
			entityData = new SpiderEntity.SpiderData();
			if (world.getDifficulty() == Difficulty.HARD
					&& world.getRandom().nextFloat() < 0.1F * difficulty.getClampedLocalDifficulty()) {
				((SpiderEntity.SpiderData) entityData).setEffect(world.getRandom());
			}
		}
		if (entityData instanceof SpiderEntity.SpiderData) {
			StatusEffect statusEffect = ((SpiderEntity.SpiderData) entityData).effect;
			if (statusEffect != null) {
				this.addStatusEffect(new StatusEffectInstance(statusEffect, Integer.MAX_VALUE));
			}
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}
}
