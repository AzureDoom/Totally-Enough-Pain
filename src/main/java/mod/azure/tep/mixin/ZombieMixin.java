package mod.azure.tep.mixin;

import java.util.SplittableRandom;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.azure.tep.TotallyEnoughPainMod;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

@Mixin(ZombieEntity.class)
public abstract class ZombieMixin extends HostileEntity {

	EntityAttributeInstance entityAttributeInstance = this
			.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);

	protected ZombieMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Overwrite
	public boolean burnsInDaylight() {
		return TotallyEnoughPainMod.config.zombies.zombies_dont_burn ? false : true;
	}

	@Inject(method = "initialize", at = @At("HEAD"))
	private void enchantedArmor(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
			@Nullable EntityData entityData, @Nullable NbtCompound entityNbt, CallbackInfoReturnable<EntityData> ci) {
		this.updateEnchantments(difficulty);
		SplittableRandom random = new SplittableRandom();
		int r = random.nextInt(0, 10);
		if (TotallyEnoughPainMod.config.zombies.zombies_runners == true) {
			if (r <= 3) {
				entityAttributeInstance.addTemporaryModifier(
						new EntityAttributeModifier(UUID.fromString("2cd5b1d6-6ce6-44ab-ac3b-1d0aecd1d0cd"),
								"Speed boost", 0.5D, EntityAttributeModifier.Operation.MULTIPLY_BASE));
			}
		}
	}

	@Inject(method = "initEquipment", at = @At("HEAD"))
	private void moreEquipment(LocalDifficulty difficulty, CallbackInfo ci) {
		if (TotallyEnoughPainMod.config.zombies.zombies_better_gear == true) {
			int i = this.random.nextInt(3);
			if (i == 0) {
				this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(this.world.getDifficulty() == Difficulty.HARD
						? Items.DIAMOND_SWORD
						: this.world.getDifficulty() == Difficulty.EASY ? Items.GOLDEN_SWORD : Items.IRON_SWORD));
			} else {
				this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(this.world.getDifficulty() == Difficulty.HARD
						? Items.DIAMOND_SHOVEL
						: this.world.getDifficulty() == Difficulty.EASY ? Items.GOLDEN_SHOVEL : Items.IRON_SHOVEL));
			}
		}
	}

	protected void updateEnchantments(LocalDifficulty difficulty) {
		float f = difficulty.getClampedLocalDifficulty();
		this.enchantMainHandItem(f * TotallyEnoughPainMod.config.zombies.zombies_enchanted_more);
		EquipmentSlot[] var3 = EquipmentSlot.values();
		int var4 = var3.length;
		for (int var5 = 0; var5 < var4; ++var5) {
			EquipmentSlot equipmentSlot = var3[var5];
			if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR) {
				this.enchantEquipment(f * TotallyEnoughPainMod.config.zombies.zombies_enchanted_more, equipmentSlot);
			}
		}
	}

}
