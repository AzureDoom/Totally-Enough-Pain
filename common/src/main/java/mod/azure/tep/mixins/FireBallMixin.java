package mod.azure.tep.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import mod.azure.tep.CommonMod;
import net.minecraft.world.entity.projectile.LargeFireball;

@Mixin(LargeFireball.class)
public class FireBallMixin {
	@Shadow
	private int explosionPower = CommonMod.config.ghast_fire_power;
}
