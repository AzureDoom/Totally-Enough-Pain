package mod.azure.tep.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.azure.tep.config.TEPConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

@Mixin(Spider.class)
public abstract class SpiderMixin extends Monster {

	protected SpiderMixin(EntityType<? extends Monster> entityType, Level world) {
		super(entityType, world);
	}

	@Inject(method = "registerGoals", at = @At("HEAD"))
	private void attackGoals(CallbackInfo ci) {
		if (TEPConfig.SERVER.spider_always_attack.get()  == true)
			this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, false));
		if (TEPConfig.SERVER.spider_attacks_villagers.get()  == true)
			this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, AbstractVillager.class, false));
	}

	@Inject(method = "finalizeSpawn", at = @At("HEAD"), cancellable = true)
	private void spiderJockeys(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason,
			@Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt,
			CallbackInfoReturnable<SpawnGroupData> cir) {
		if (TEPConfig.SERVER.spider_always_jockeys.get()  == true || world.getRandom().nextInt(100) == 0) {
			Skeleton skeletonEntity = (Skeleton) EntityType.SKELETON.create(this.level);
			skeletonEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
			skeletonEntity.finalizeSpawn(world, difficulty, spawnReason, (SpawnGroupData) null, null);
			skeletonEntity.startRiding(this);
		}
	}
}
