package mod.azure.tep.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import mod.azure.tep.TotallyEnoughPainMod;
import net.minecraft.entity.projectile.FireballEntity;

@Mixin(FireballEntity.class)
public class FireBallMixin {
	@Shadow
	private int explosionPower = TotallyEnoughPainMod.config.ghasts.ghast_fire_power;
}
