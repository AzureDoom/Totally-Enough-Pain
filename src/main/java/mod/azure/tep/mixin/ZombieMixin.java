package mod.azure.tep.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import mod.azure.tep.TotallyEnoughPainMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;

@Mixin(ZombieEntity.class)
public abstract class ZombieMixin extends HostileEntity {

	protected ZombieMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Overwrite
	public boolean burnsInDaylight() {
		return TotallyEnoughPainMod.config.zombies.zombies_dont_burn ? false : true;
	}

}
