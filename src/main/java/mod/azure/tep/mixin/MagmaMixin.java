package mod.azure.tep.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.azure.tep.config.TEPConfig;
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
		return TEPConfig.magma_render_onfire == false ? false : super.isOnFire();
	}

	@Inject(at = @At("RETURN"), method = "isOnFire", cancellable = true)
	private void onFireYo(CallbackInfoReturnable<Boolean> cir) {
		if (TEPConfig.magma_onfire == true)
			cir.setReturnValue(true);
	}

}
