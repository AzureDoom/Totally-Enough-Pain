package mod.azure.tep.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.tep.config.TEPConfig;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

@Mixin(EnderMan.class)
public abstract class EndermanMixin extends Monster {

	protected EndermanMixin(EntityType<? extends Monster> entityType, Level world) {
		super(entityType, world);
	}

	@Inject(method = "registerGoals", at = @At("HEAD"))
	private void attackGoals(CallbackInfo ci) {
		if (TEPConfig.SERVER.enderman_always_attack.get() == true)
			this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, false));
		if (TEPConfig.SERVER.enderman_attacks_villagers.get() == true)
			this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, AbstractVillager.class, false));
	}
}
