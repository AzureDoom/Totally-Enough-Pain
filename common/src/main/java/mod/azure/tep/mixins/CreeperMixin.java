package mod.azure.tep.mixins;

import mod.azure.tep.CommonMod;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(Creeper.class)
public abstract class CreeperMixin extends Monster {

    @Shadow
    private int explosionRadius = CommonMod.config.creeper_power;

    protected CreeperMixin(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    @Shadow
    private static final EntityDataAccessor<Boolean> DATA_IS_POWERED = SynchedEntityData.defineId(Creeper.class,
            EntityDataSerializers.BOOLEAN);

    @Shadow
    private static final EntityDataAccessor<Integer> DATA_SWELL_DIR = SynchedEntityData.defineId(Creeper.class,
            EntityDataSerializers.INT);

    private static final Predicate<Difficulty> DOOR_BREAK_DIFFICULTY_CHECKER = difficulty ->
            difficulty == Difficulty.HARD || difficulty == Difficulty.EASY || difficulty == Difficulty.NORMAL;

    @Inject(method = "tick", at = @At("HEAD"))
    private void superCharged(CallbackInfo ci) {
        if (CommonMod.config.creeper_always_charged)
            this.entityData.set(DATA_IS_POWERED, true);
    }

    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void attackGoals(CallbackInfo ci) {
        if (CommonMod.config.creeper_attacks_irongolems)
            this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, IronGolem.class, false));
        if (CommonMod.config.creeper_attacks_villagers)
            this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(1, new BreakDoorGoal(this, DOOR_BREAK_DIFFICULTY_CHECKER));
    }
}
