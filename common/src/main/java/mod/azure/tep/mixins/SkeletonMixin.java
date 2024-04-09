package mod.azure.tep.mixins;

import mod.azure.tep.CommonMod;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(AbstractSkeleton.class)
public abstract class SkeletonMixin extends Monster {

    private static final Predicate<Difficulty> DOOR_BREAK_DIFFICULTY_CHECKER = difficulty -> difficulty == Difficulty.HARD || difficulty == Difficulty.EASY || difficulty == Difficulty.NORMAL;

    protected SkeletonMixin(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public boolean isOnFire() {
        return !CommonMod.config.skeletons_dont_burn && super.isOnFire();
    }

    @Override
    public void setSecondsOnFire(int seconds) {
        super.setSecondsOnFire(CommonMod.config.skeletons_dont_burn ? 0 : seconds);
    }

    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void attackGoals(CallbackInfo ci) {
        this.targetSelector.addGoal(1, new BreakDoorGoal(this, DOOR_BREAK_DIFFICULTY_CHECKER));
        if (CommonMod.config.skeletons_attacks_villagers)
            this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
    }

    protected void populateDefaultEquipmentEnchantments(DifficultyInstance difficulty) {
        float f = difficulty.getSpecialMultiplier();
        this.enchantSpawnedWeapon(random, f * CommonMod.config.skeletons_enchanted_more);
        EquipmentSlot[] var3 = EquipmentSlot.values();

        for (EquipmentSlot equipmentSlot : var3) {
            if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR) {
                this.enchantSpawnedArmor(random, f * CommonMod.config.skeletons_enchanted_more, equipmentSlot);
            }
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return source != damageSources().inWall() && super.hurt(source, amount);
    }
}
