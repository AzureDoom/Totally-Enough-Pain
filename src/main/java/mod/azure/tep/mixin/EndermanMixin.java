package mod.azure.tep.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.tep.TotallyEnoughPainMod;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Mixin(EndermanEntity.class)
public abstract class EndermanMixin extends HostileEntity {

	protected EndermanMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Inject(method = "initGoals", at = @At("HEAD"))
	private void attackGoals(CallbackInfo ci) {
		if (TotallyEnoughPainMod.config.enderman.enderman_always_attack == true)
			this.targetSelector.add(1, new ActiveTargetGoal(this, PlayerEntity.class, false));
		if (TotallyEnoughPainMod.config.enderman.enderman_attacks_villagers == true)
			this.targetSelector.add(1, new ActiveTargetGoal(this, MerchantEntity.class, false));
	}

	@Overwrite
	public boolean isPlayerStaring(PlayerEntity player) {
		if (TotallyEnoughPainMod.config.enderman.enderman_always_attack == true) {
			return true;
		} else {
			ItemStack itemStack = (ItemStack) player.getInventory().armor.get(3);
			if (itemStack.isOf(Blocks.CARVED_PUMPKIN.asItem())) {
				return false;
			} else {
				Vec3d vec3d = player.getRotationVec(1.0F).normalize();
				Vec3d vec3d2 = new Vec3d(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(),
						this.getZ() - player.getZ());
				double d = vec3d2.length();
				vec3d2 = vec3d2.normalize();
				double e = vec3d.dotProduct(vec3d2);
				return e > 1.0D - 0.025D / d ? player.canSee(this) : false;
			}
		}
	}
}
