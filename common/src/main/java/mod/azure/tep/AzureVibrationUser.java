package mod.azure.tep;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.GameEventTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AzureVibrationUser implements VibrationSystem.User {
    public final Mob mob;
    private final float moveSpeed;
    private final PositionSource positionSource;

    public AzureVibrationUser(Mob entity, float speed) {
        this.positionSource = new EntityPositionSource(entity, entity.getEyeHeight());
        this.mob = entity;
        this.moveSpeed = speed;
    }

    @Override
    public int getListenerRadius() {
        return CommonMod.config.monster_sensing_range;
    }

    @Override
    public @NotNull PositionSource getPositionSource() {
        return this.positionSource;
    }

    @Override
    public boolean canTriggerAvoidVibration() {
        return true;
    }

    @Override
    public boolean isValidVibration(Holder<GameEvent> gameEvent, @NotNull GameEvent.Context context) {
        if (!gameEvent.is(this.getListenableEvents())) return false;

        var entity = context.sourceEntity();
        if (entity != null) {
            if (entity.isSpectator()) return false;
            if (entity.isSteppingCarefully() && gameEvent.is(GameEventTags.IGNORE_VIBRATIONS_SNEAKING)) return false;
            if (entity.dampensVibrations()) return false;
        }
        if (context.affectedState() != null)
            return !context.affectedState().is(BlockTags.DAMPENS_VIBRATIONS);
        return true;
    }

    @Override
    public boolean canReceiveVibration(@NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull Holder<GameEvent> gameEvent, @NotNull GameEvent.Context context) {
        if (mob.isNoAi() || mob.isDeadOrDying() || !mob.level().getWorldBorder().isWithinBounds(
                blockPos) || mob.isRemoved()) return false;
        var entity = context.sourceEntity();
        return !(entity instanceof LivingEntity);
    }

    @Override
    public void onReceiveVibration(@NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull Holder<GameEvent> gameEvent, @Nullable Entity entity, @Nullable Entity entity2, float f) {
        if (this.mob.isDeadOrDying()) return;
        if (this.mob.isVehicle()) return;
        if (CommonMod.config.monsters_can_warden_sense)
            mob.getNavigation().moveTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), this.moveSpeed);
    }
}