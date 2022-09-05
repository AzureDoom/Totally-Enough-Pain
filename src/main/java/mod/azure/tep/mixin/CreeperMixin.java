package mod.azure.tep.mixin;

import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.tep.config.TEPConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.BreakDoorGoal;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

@Mixin(CreeperEntity.class)
public abstract class CreeperMixin extends HostileEntity {

	@Shadow
	private int explosionRadius = TEPConfig.creeper_power;

	protected CreeperMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Shadow
	private static final TrackedData<Boolean> CHARGED = DataTracker.registerData(CreeperEntity.class,
			TrackedDataHandlerRegistry.BOOLEAN);

	@Shadow
	private static final TrackedData<Integer> FUSE_SPEED = DataTracker.registerData(CreeperEntity.class,
			TrackedDataHandlerRegistry.INTEGER);

	private static final Predicate<Difficulty> DOOR_BREAK_DIFFICULTY_CHECKER = (difficulty) -> {
		return difficulty == Difficulty.HARD || difficulty == Difficulty.EASY || difficulty == Difficulty.NORMAL;
	};
	protected BlockPos doorPos;

	@Inject(method = "tick", at = @At("HEAD"))
	private void superCharged(CallbackInfo ci) {
		if (TEPConfig.creeper_always_charged == true)
			this.dataTracker.set(CHARGED, true);
	}

	@Inject(method = "initGoals", at = @At("HEAD"))
	private void attackGoals(CallbackInfo ci) {
		if (TEPConfig.creeper_attacks_irongolems == true)
			this.targetSelector.add(1, new ActiveTargetGoal(this, IronGolemEntity.class, false));
		if (TEPConfig.creeper_attacks_villagers == true)
			this.targetSelector.add(1, new ActiveTargetGoal(this, MerchantEntity.class, false));
		this.targetSelector.add(1, new BreakDoorGoal(this, DOOR_BREAK_DIFFICULTY_CHECKER));
	}
}
