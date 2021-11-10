package mod.azure.tep.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import mod.azure.tep.TotallyEnoughPainMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

@Mixin(MagmaCubeEntity.class)
public class MagmaMixin extends MobEntity {

	protected MagmaMixin(EntityType<? extends MobEntity> entityType, World world) {
		super(entityType, world);
	}

	public boolean doesRenderOnFire() {
		return TotallyEnoughPainMod.config.magma.magma_render_onfire == false ? false : super.isOnFire();
	}

	@Overwrite
	public boolean isOnFire() {
		return TotallyEnoughPainMod.config.magma.magma_onfire == true ? true : super.isOnFire();
	}

}
