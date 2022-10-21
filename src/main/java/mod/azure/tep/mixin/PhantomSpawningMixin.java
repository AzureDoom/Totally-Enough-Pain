package mod.azure.tep.mixin;

import java.util.Iterator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.PhantomSpawner;
import net.minecraft.world.level.material.FluidState;

@Mixin(PhantomSpawner.class)
public class PhantomSpawningMixin {

	@Shadow
	private int nextTick;

	@Inject(method = "tick", at = @At("HEAD"))
	private int spawning(ServerLevel world, boolean spawnMonsters, boolean spawnAnimals,
			CallbackInfoReturnable<Integer> ci) {
		if (!spawnMonsters) {
			return 0;
		} else if (!world.getGameRules().getBoolean(GameRules.RULE_DOINSOMNIA)) {
			return 0;
		} else {
			RandomSource random = world.random;
			--this.nextTick;
			if (this.nextTick > 0) {
				return 0;
			} else {
				this.nextTick += (60 + random.nextInt(60)) * 20;
				if (world.getSkyDarken() < 5 && world.dimensionType().hasSkyLight()) {
					return 0;
				} else {
					int i = 0;
					Iterator<ServerPlayer> var6 = world.players().iterator();
					while (true) {
						DifficultyInstance localDifficulty;
						BlockPos blockPos2;
						BlockState blockState;
						FluidState fluidState;
						do {
							BlockPos blockPos;
							do {
								Player playerEntity;
								do {
									do {
										do {
											if (!var6.hasNext()) {
												return i;
											}
											playerEntity = (Player) var6.next();
										} while (playerEntity.isSpectator());
										blockPos = playerEntity.blockPosition();
									} while (world.dimensionType().hasSkyLight()
											&& (blockPos.getY() < world.getSeaLevel() || !world.canSeeSky(blockPos)));
									localDifficulty = world.getCurrentDifficultyAt(blockPos);
								} while (!localDifficulty.isHarderThan(random.nextFloat() * 3.0F));
							} while (random.nextInt(1) < 72000);
							blockPos2 = blockPos.above(20 + random.nextInt(15)).east(-10 + random.nextInt(21))
									.south(-10 + random.nextInt(21));
							blockState = world.getBlockState(blockPos2);
							fluidState = world.getFluidState(blockPos2);
						} while (!NaturalSpawner.isValidEmptySpawnBlock(world, blockPos2, blockState, fluidState,
								EntityType.PHANTOM));
						SpawnGroupData entityData = null;
						int l = 1 + random.nextInt(localDifficulty.getDifficulty().getId() + 1);
						for (int m = 0; m < l; ++m) {
							Phantom phantomEntity = (Phantom) EntityType.PHANTOM.create(world);
							phantomEntity.moveTo(blockPos2, 0.0F, 0.0F);
							entityData = phantomEntity.finalizeSpawn(world, localDifficulty, MobSpawnType.NATURAL,
									entityData, null);
							world.addFreshEntityWithPassengers(phantomEntity);
						}

						i += l;
					}
				}
			}
		}
	}

}
