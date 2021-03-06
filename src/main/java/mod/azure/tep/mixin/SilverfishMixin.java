package mod.azure.tep.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.tep.TotallyEnoughPainMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.world.World;

@Mixin(SilverfishEntity.class)
public abstract class SilverfishMixin extends HostileEntity {

	protected SilverfishMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Inject(method = "initGoals", at = @At("HEAD"))
	private void attackGoals(CallbackInfo ci) {
		if (TotallyEnoughPainMod.config.silverfish.silverfish_attacks_villagers == true)
			this.targetSelector.add(1, new ActiveTargetGoal(this, MerchantEntity.class, false));
	}
}
