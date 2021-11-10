package mod.azure.tep.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import mod.azure.tep.TotallyEnoughPainMod;
import net.minecraft.entity.ai.goal.BreakDoorGoal;

@Mixin(BreakDoorGoal.class)
public class ZombieDoorMixin {

	@Overwrite
	public int getMaxProgress() {
		return Math.max(TotallyEnoughPainMod.config.zombies.zombies_break_door, -1);
	}
}
