package mod.azure.tep.mixins;

import mod.azure.tep.CommonMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderMan.class)
public abstract class EndermanMixin extends Monster {

    protected EndermanMixin(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void attackGoals(CallbackInfo ci) {
        if (CommonMod.config.enderman_always_attack)
            this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
        if (CommonMod.config.enderman_attacks_villagers)
            this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
    }
}
