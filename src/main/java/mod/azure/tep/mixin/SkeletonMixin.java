package mod.azure.tep.mixin;

import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.tep.config.TEPConfig;
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

@Mixin(AbstractSkeleton.class)
public abstract class SkeletonMixin extends Monster {

	private static final Predicate<Difficulty> DOOR_BREAK_DIFFICULTY_CHECKER = (difficulty) -> {
		return difficulty == Difficulty.HARD || difficulty == Difficulty.EASY || difficulty == Difficulty.NORMAL;
	};

	protected SkeletonMixin(EntityType<? extends Monster> entityType, Level world) {
		super(entityType, world);
	}

	@Override
	public boolean isOnFire() {
		return TEPConfig.skeletons_dont_burn == true ? false : super.isOnFire();
	}

	@Override
	public void setSecondsOnFire(int seconds) {
		super.setSecondsOnFire(TEPConfig.skeletons_dont_burn == true ? 0 : seconds);
	}

	@Inject(method = "registerGoals", at = @At("HEAD"))
	private void attackGoals(CallbackInfo ci) {
		this.targetSelector.addGoal(1, new BreakDoorGoal(this, DOOR_BREAK_DIFFICULTY_CHECKER));
		if (TEPConfig.skeletons_attacks_villagers == true)
			this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, AbstractVillager.class, false));
	}

	protected void populateDefaultEquipmentEnchantments(DifficultyInstance difficulty) {
		float f = difficulty.getSpecialMultiplier();
		this.enchantSpawnedWeapon(random, f * TEPConfig.skeletons_enchanted_more);
		EquipmentSlot[] var3 = EquipmentSlot.values();
		int var4 = var3.length;

		for (int var5 = 0; var5 < var4; ++var5) {
			EquipmentSlot equipmentSlot = var3[var5];
			if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR) {
				this.enchantSpawnedArmor(random, f * TEPConfig.skeletons_enchanted_more, equipmentSlot);
			}
		}
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		return source == DamageSource.IN_WALL ? false : super.hurt(source, amount);
	}
}
