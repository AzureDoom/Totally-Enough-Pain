package mod.azure.tep.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.tep.CommonMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.level.Level;

@Mixin(Ghast.class)
public abstract class GhastMixin extends FlyingMob {

	protected GhastMixin(EntityType<? extends FlyingMob> entityType, Level world) {
		super(entityType, world);
	}

	@Inject(method = "registerGoals", at = @At("HEAD"))
	private void attackGoals(CallbackInfo ci) {
		if (CommonMod.config.ghast_attacks_villagers)
			this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
	}
}
