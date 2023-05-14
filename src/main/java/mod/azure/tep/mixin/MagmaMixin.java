package mod.azure.tep.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.azure.tep.TotallyEnoughPainMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.level.Level;

@Mixin(MagmaCube.class)
public class MagmaMixin extends Mob {

	protected MagmaMixin(EntityType<? extends Mob> entityType, Level world) {
		super(entityType, world);
	}

	public boolean doesRenderOnFire() {
		return TotallyEnoughPainMod.config.magma_render_onfire == false ? false : super.isOnFire();
	}

	@Inject(at = @At("RETURN"), method = "isOnFire", cancellable = true)
	private void onFireYo(CallbackInfoReturnable<Boolean> cir) {
		if (TotallyEnoughPainMod.config.magma_onfire == true)
			cir.setReturnValue(true);
	}

}
