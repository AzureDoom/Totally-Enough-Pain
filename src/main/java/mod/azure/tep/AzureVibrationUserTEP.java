package mod.azure.tep;

import org.jetbrains.annotations.Nullable;

import mod.azure.azurelib.helper.AzureVibrationUser;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.gameevent.GameEvent;

public class AzureVibrationUserTEP extends AzureVibrationUser {

	public AzureVibrationUserTEP(Mob entity, float speed, int range) {
		super(entity, speed, range);
	}

	@Override
	public void onReceiveVibration(ServerLevel serverLevel, BlockPos blockPos, GameEvent gameEvent, @Nullable Entity entity, @Nullable Entity entity2, float f) {
		if (this.mob.isDeadOrDying())
			return;
		if (this.mob.isVehicle())
			return;
		if (TotallyEnoughPainMod.config.monsters_can_warden_sense == true)
			mob.getNavigation().moveTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0.9F);
	}

	@Override
	public boolean canTargetEntity(@Nullable Entity entity) {
		if (TotallyEnoughPainMod.config.monsters_can_warden_sense == false)
			return false;
		return super.canTargetEntity(entity);
	}
}
