package mod.azure.tep.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import mod.azure.tep.config.TEPConfig;
import net.minecraft.entity.projectile.FireballEntity;

@Mixin(FireballEntity.class)
public class FireBallMixin {
	@Shadow
	private int explosionPower = TEPConfig.ghast_fire_power;
}
