package mod.azure.tep.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.tep.TotallyEnoughPainMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

@Mixin(ZombifiedPiglin.class)
public abstract class ZombiePiglinMixin extends Monster {

	protected ZombiePiglinMixin(EntityType<? extends Monster> entityType, Level world) {
		super(entityType, world);
	}

	@Inject(method = "addBehaviourGoals", at = @At("HEAD"))
	private void attackGoals(CallbackInfo ci) {
		if (TotallyEnoughPainMod.config.zombiepiglin_onsight == true)
			this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, false));
		if (TotallyEnoughPainMod.config.zombiepiglin_attacks_villagers == true)
			this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, AbstractVillager.class, false));
	}

}
