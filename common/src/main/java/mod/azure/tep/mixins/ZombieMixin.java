package mod.azure.tep.mixins;

import mod.azure.tep.CommonMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.SplittableRandom;
import java.util.UUID;
import java.util.function.Predicate;

@Mixin(Zombie.class)
public abstract class ZombieMixin extends Monster {

    AttributeInstance entityAttributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);

    private static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE = difficulty ->
            difficulty == Difficulty.HARD || difficulty == Difficulty.EASY || difficulty == Difficulty.NORMAL;

    protected ZombieMixin(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(at = @At("RETURN"), method = "isSunSensitive", cancellable = true)
    private void noBurny(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(!CommonMod.config.zombies_dont_burn);
    }

    @Inject(at = @At("RETURN"), method = "canBreakDoors", cancellable = true)
    public void canBreakDoors(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(CommonMod.config.zombies_break_doors_always);
    }

    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void attackGoals(CallbackInfo ci) {
        this.goalSelector.addGoal(1, new BreakDoorGoal(this, DOOR_BREAKING_PREDICATE));
    }

    @Inject(method = "finalizeSpawn", at = @At("HEAD"))
    private void enchantedArmor(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason,
                                @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt,
                                CallbackInfoReturnable<SpawnGroupData> ci) {
        this.populateDefaultEquipmentEnchantments(difficulty);
        SplittableRandom random = new SplittableRandom();
        int r = random.nextInt(0, 10);
        if (CommonMod.config.zombies_runners && r <= 3) {
            entityAttributeInstance.addTransientModifier(
                    new AttributeModifier(UUID.fromString("2cd5b1d6-6ce6-44ab-ac3b-1d0aecd1d0cd"), "Speed boost",
                            0.5D, AttributeModifier.Operation.MULTIPLY_BASE));

        }
    }

    @Inject(method = "populateDefaultEquipmentSlots", at = @At("HEAD"))
    private void moreEquipment(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci) {
        if (CommonMod.config.zombies_better_gear) {
            int i = this.random.nextInt(3);
            if (i == 0) {
                this.setItemSlot(EquipmentSlot.MAINHAND,
                        new ItemStack(this.getCommandSenderWorld().getDifficulty() == Difficulty.HARD
                                ? Items.DIAMOND_SWORD
                                : this.getCommandSenderWorld().getDifficulty() == Difficulty.EASY ? Items.GOLDEN_SWORD : Items.IRON_SWORD));
            } else {
                this.setItemSlot(EquipmentSlot.MAINHAND,
                        new ItemStack(this.getCommandSenderWorld().getDifficulty() == Difficulty.HARD
                                ? Items.DIAMOND_SHOVEL
                                : this.getCommandSenderWorld().getDifficulty() == Difficulty.EASY ? Items.GOLDEN_SHOVEL : Items.IRON_SHOVEL));
            }
        }
    }

    protected void populateDefaultEquipmentEnchantments(DifficultyInstance difficulty) {
        float f = difficulty.getSpecialMultiplier();
        this.enchantSpawnedWeapon(random, f * CommonMod.config.zombies_enchanted_more);
        EquipmentSlot[] var3 = EquipmentSlot.values();
        for (EquipmentSlot equipmentSlot : var3) {
            if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR) {
                this.enchantSpawnedArmor(random, f * CommonMod.config.zombies_enchanted_more, equipmentSlot);
            }
        }
    }

}
