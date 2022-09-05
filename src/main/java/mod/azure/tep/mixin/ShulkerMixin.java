package mod.azure.tep.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.tep.config.TEPConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.world.World;

@Mixin(ShulkerEntity.class)
public abstract class ShulkerMixin extends GolemEntity {

	protected ShulkerMixin(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "initGoals", at = @At("HEAD"))
	private void attackGoals(CallbackInfo ci) {
		if (TEPConfig.shulker_attacks_villagers == true)
			this.targetSelector.add(1, new ActiveTargetGoal(this, MerchantEntity.class, false));
	}
}
