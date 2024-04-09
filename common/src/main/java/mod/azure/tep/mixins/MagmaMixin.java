package mod.azure.tep.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.azure.tep.CommonMod;
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
		return !CommonMod.config.magma_render_onfire && super.isOnFire();
	}

	@Inject(at = @At("RETURN"), method = "isOnFire", cancellable = true)
	private void onFireYo(CallbackInfoReturnable<Boolean> cir) {
		if (CommonMod.config.magma_onfire)
			cir.setReturnValue(true);
	}

}
