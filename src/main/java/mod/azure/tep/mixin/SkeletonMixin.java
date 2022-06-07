package mod.azure.tep.mixin;

import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.tep.config.TEPConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.BreakDoorGoal;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;

@Mixin(AbstractSkeletonEntity.class)
public abstract class SkeletonMixin extends HostileEntity {

	private static final Predicate<Difficulty> DOOR_BREAK_DIFFICULTY_CHECKER = (difficulty) -> {
		return difficulty == Difficulty.HARD || difficulty == Difficulty.EASY || difficulty == Difficulty.NORMAL;
	};

	protected SkeletonMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	@Override
	public void setOnFireFor(int seconds) {
		super.setOnFireFor(0);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Inject(method = "initGoals", at = @At("HEAD"))
	private void attackGoals(CallbackInfo ci) {
		this.targetSelector.add(1, new BreakDoorGoal(this, DOOR_BREAK_DIFFICULTY_CHECKER));
		if (TEPConfig.skeletons_attacks_villagers == true)
			this.targetSelector.add(1, new ActiveTargetGoal(this, MerchantEntity.class, false));
	}

	protected void updateEnchantments(LocalDifficulty difficulty) {
		float f = difficulty.getClampedLocalDifficulty();
		this.enchantMainHandItem(random, f * TEPConfig.skeletons_enchanted_more);
		EquipmentSlot[] var3 = EquipmentSlot.values();
		int var4 = var3.length;

		for (int var5 = 0; var5 < var4; ++var5) {
			EquipmentSlot equipmentSlot = var3[var5];
			if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR) {
				this.enchantEquipment(random, f * TEPConfig.skeletons_enchanted_more, equipmentSlot);
			}
		}
	}
}
