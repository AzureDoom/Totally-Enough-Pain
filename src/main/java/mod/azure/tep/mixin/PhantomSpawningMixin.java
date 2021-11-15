package mod.azure.tep.mixin;

import java.util.Iterator;
import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.gen.PhantomSpawner;

@Mixin(PhantomSpawner.class)
public class PhantomSpawningMixin {

	@Shadow
	private int ticksUntilNextSpawn;

	@Inject(method = "spawn", at = @At("HEAD"))
	private int spawning(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals, CallbackInfoReturnable<Integer> ci) {
		if (!spawnMonsters) {
			return 0;
		} else if (!world.getGameRules().getBoolean(GameRules.DO_INSOMNIA)) {
			return 0;
		} else {
			Random random = world.random;
			--this.ticksUntilNextSpawn;
			if (this.ticksUntilNextSpawn > 0) {
				return 0;
			} else {
				this.ticksUntilNextSpawn += (60 + random.nextInt(60)) * 20;
				if (world.getAmbientDarkness() < 5 && world.getDimension().hasSkyLight()) {
					return 0;
				} else {
					int i = 0;
					Iterator<ServerPlayerEntity> var6 = world.getPlayers().iterator();
					while (true) {
						LocalDifficulty localDifficulty;
						BlockPos blockPos2;
						BlockState blockState;
						FluidState fluidState;
						do {
							BlockPos blockPos;
							do {
								PlayerEntity playerEntity;
								do {
									do {
										do {
											if (!var6.hasNext()) {
												return i;
											}
											playerEntity = (PlayerEntity) var6.next();
										} while (playerEntity.isSpectator());
										blockPos = playerEntity.getBlockPos();
									} while (world.getDimension().hasSkyLight()
											&& (blockPos.getY() < world.getSeaLevel()
													|| !world.isSkyVisible(blockPos)));
									localDifficulty = world.getLocalDifficulty(blockPos);
								} while (!localDifficulty.isHarderThan(random.nextFloat() * 3.0F));
							} while (random.nextInt(1) < 72000);
							blockPos2 = blockPos.up(20 + random.nextInt(15)).east(-10 + random.nextInt(21))
									.south(-10 + random.nextInt(21));
							blockState = world.getBlockState(blockPos2);
							fluidState = world.getFluidState(blockPos2);
						} while (!SpawnHelper.isClearForSpawn(world, blockPos2, blockState, fluidState,
								EntityType.PHANTOM));
						EntityData entityData = null;
						int l = 1 + random.nextInt(localDifficulty.getGlobalDifficulty().getId() + 1);
						for (int m = 0; m < l; ++m) {
							PhantomEntity phantomEntity = (PhantomEntity) EntityType.PHANTOM.create(world);
							phantomEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							entityData = phantomEntity.initialize(world, localDifficulty, SpawnReason.NATURAL,
									entityData, (NbtCompound) null);
							world.spawnEntityAndPassengers(phantomEntity);
						}

						i += l;
					}
				}
			}
		}
	}

}
