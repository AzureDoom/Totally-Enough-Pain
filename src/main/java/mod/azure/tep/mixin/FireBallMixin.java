package mod.azure.tep.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import mod.azure.tep.TotallyEnoughPainMod;
import net.minecraft.world.entity.projectile.LargeFireball;

@Mixin(LargeFireball.class)
public class FireBallMixin {
	@Shadow
	private int explosionPower = TotallyEnoughPainMod.config.ghast_fire_power;
}
