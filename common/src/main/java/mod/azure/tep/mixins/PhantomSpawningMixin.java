package mod.azure.tep.mixins;

import mod.azure.tep.CommonMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.PhantomSpawner;

@Mixin(PhantomSpawner.class)
public class PhantomSpawningMixin {

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	private void spawning(ServerLevel world, boolean spawnMonsters, boolean spawnAnimals,
			CallbackInfoReturnable<Integer> ci) {
		if (CommonMod.config.phantoms_always_spawn)
			ci.setReturnValue(1);
	}

}
