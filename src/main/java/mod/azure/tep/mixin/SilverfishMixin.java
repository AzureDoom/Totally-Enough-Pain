package mod.azure.tep.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.tep.config.TEPConfig;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.level.Level;

@Mixin(Silverfish.class)
public abstract class SilverfishMixin extends Monster {

	protected SilverfishMixin(EntityType<? extends Monster> entityType, Level world) {
		super(entityType, world);
	}

	@Inject(method = "registerGoals", at = @At("TAIL"))
	private void attackGoals(CallbackInfo ci) {
		if (TEPConfig.SERVER.silverfish_attacks_villagers.get() == true)
			this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, AbstractVillager.class, false));
	}
}
