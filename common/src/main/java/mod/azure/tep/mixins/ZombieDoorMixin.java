package mod.azure.tep.mixins;

import mod.azure.tep.CommonMod;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BreakDoorGoal.class)
public class ZombieDoorMixin {

    @Inject(method = "getDoorBreakTime", at = @At("HEAD"))
    private void setBreakTime(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(Math.max(CommonMod.config.zombies_break_door, -1));
    }
}
