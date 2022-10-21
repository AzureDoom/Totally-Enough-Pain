package mod.azure.tep.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import mod.azure.tep.config.TEPConfig;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;

@Mixin(BreakDoorGoal.class)
public class ZombieDoorMixin {

	@Overwrite
	public int getDoorBreakTime() {
		return Math.max(TEPConfig.zombies_break_door, -1);
	}
}
