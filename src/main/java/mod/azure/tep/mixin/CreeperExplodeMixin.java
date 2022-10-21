package mod.azure.tep.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.tep.config.TEPConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.SwellGoal;
import net.minecraft.world.entity.monster.Creeper;

@Mixin(SwellGoal.class)
public abstract class CreeperExplodeMixin extends Goal {

	@Shadow
	private final Creeper creeper;

	@Shadow
	private LivingEntity target;

	public CreeperExplodeMixin(Creeper creeper) {
		this.creeper = creeper;
	}

	@Inject(method = "start", at = @At("TAIL"))
	private void attackStart(CallbackInfo ci) {
		if (TEPConfig.creeper_doesnt_stop == true && target != null)
			this.creeper.getNavigation().moveTo(target, 1.0D);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void tickStart(CallbackInfo ci) {
		if (TEPConfig.creeper_blowsup_door == true && this.creeper.getSensing().hasLineOfSight(this.target)
				&& this.creeper.distanceToSqr(this.target) <= 3.0D) {
			this.creeper.setSwellDir(-1);
		}
	}
}
