package mod.azure.tep.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.tep.TotallyEnoughPainMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.CreeperIgniteGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.CreeperEntity;

@Mixin(CreeperIgniteGoal.class)
public abstract class CreeperExplodeMixin extends Goal {

	@Shadow
	private final CreeperEntity creeper;

	@Shadow
	private LivingEntity target;

	public CreeperExplodeMixin(CreeperEntity creeper) {
		this.creeper = creeper;
	}

	@Inject(method = "start", at = @At("TAIL"))
	private void attackStart(CallbackInfo ci) {
		if (TotallyEnoughPainMod.config.creepers.creeper_doesnt_stop == true)
			this.creeper.getNavigation().startMovingTo(target, 1.0D);
	}

	@Overwrite
	public void tick() {
		if (this.target == null) {
			this.creeper.setFuseSpeed(-1);
		} else if (TotallyEnoughPainMod.config.creepers.creeper_blowsup_door == true
				&& this.creeper.getVisibilityCache().canSee(this.target)
				&& this.creeper.squaredDistanceTo(this.target) <= 3.0D) {
			this.creeper.setFuseSpeed(-1);
		} else {
			this.creeper.setFuseSpeed(1);
		}
	}
}
