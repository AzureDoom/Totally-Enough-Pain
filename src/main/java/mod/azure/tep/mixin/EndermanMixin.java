package mod.azure.tep.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.tep.TotallyEnoughPainMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

@Mixin(EndermanEntity.class)
public abstract class EndermanMixin extends HostileEntity {

	protected EndermanMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Inject(method = "initGoals", at = @At("HEAD"))
	private void attackGoals(CallbackInfo ci) {
		if (TotallyEnoughPainMod.config.enderman.enderman_always_attack == true)
			this.targetSelector.add(1, new ActiveTargetGoal(this, PlayerEntity.class, false));
		if (TotallyEnoughPainMod.config.enderman.enderman_attacks_villagers == true)
			this.targetSelector.add(1, new ActiveTargetGoal(this, MerchantEntity.class, false));
	}
}
