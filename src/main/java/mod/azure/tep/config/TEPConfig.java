package mod.azure.tep.config;

import java.io.File;

import org.apache.commons.lang3.tuple.Pair;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class TEPConfig {
	public static class Server {

		public final ConfigValue<Boolean> monsters_can_warden_sense;

		public final ConfigValue<Integer> monster_sensing_range;

		public final ConfigValue<Boolean> creeper_doesnt_stop;

		public final ConfigValue<Boolean> creeper_always_charged;

		public final ConfigValue<Boolean> creeper_explodes_faster;

		public final ConfigValue<Boolean> creeper_attacks_villagers;

		public final ConfigValue<Boolean> creeper_attacks_irongolems;

		public final ConfigValue<Boolean> creeper_blowsup_door;

		public final ConfigValue<Integer> creeper_power;

		public final ConfigValue<Integer> ghast_fire_power;

		public final ConfigValue<Boolean> ghast_attacks_villagers;

		public final ConfigValue<Integer> zombies_break_door;

		public final ConfigValue<Boolean> zombies_dont_burn;

		public final ConfigValue<Integer> zombies_enchanted_more;

		public final ConfigValue<Boolean> zombies_better_gear;

		public final ConfigValue<Boolean> zombies_runners;

		public final ConfigValue<Boolean> zombiepiglin_onsight;

		public final ConfigValue<Boolean> zombiepiglin_attacks_villagers;

		public final ConfigValue<Integer> skeletons_enchanted_more;

		public final ConfigValue<Boolean> skeletons_attacks_villagers;

		public final ConfigValue<Boolean> skeletons_dont_burn;

		public final ConfigValue<Boolean> enderman_always_attack;

		public final ConfigValue<Boolean> enderman_attacks_villagers;

		public final ConfigValue<Boolean> spider_always_attack;

		public final ConfigValue<Boolean> spider_attacks_villagers;

		public final ConfigValue<Boolean> spider_always_jockeys;

		public final ConfigValue<Boolean> phantoms_always_spawn;

		public final ConfigValue<Boolean> blaze_attacks_villagers;

		public final ConfigValue<Boolean> blaze_always_onfire;

		public final ConfigValue<Boolean> magma_render_onfire;

		public final ConfigValue<Boolean> magma_onfire;

		public final ConfigValue<Boolean> endermite_attacks_villagers;

		public final ConfigValue<Boolean> shulker_attacks_villagers;

		public final ConfigValue<Boolean> silverfish_attacks_villagers;

		public final ConfigValue<Boolean> slimes_attacks_villagers;

		public final ConfigValue<Boolean> vex_attacks_villagers;

		public final ConfigValue<Boolean> wither_attacks_villagers;

		public Server(ForgeConfigSpec.Builder builder) {
			builder.push("General");
			this.monsters_can_warden_sense = builder.translation("tep.midnightconfig.monsters_can_warden_sense")
					.define("Monsters Can Warden Sense", true);
			this.monster_sensing_range = builder.translation("tep.midnightconfig.monster_sensing_range")
					.defineInRange("Monster Sensing Range", 32, 1, Integer.MAX_VALUE);
			builder.pop();
			builder.push("Creeper");
			this.creeper_doesnt_stop = builder.translation("tep.midnightconfig.creeper_doesnt_stop")
					.define("Creepers Don't Stop When Exploding", true);
			this.creeper_always_charged = builder.translation("tep.midnightconfig.creeper_always_charged")
					.define("Creeper Always Charged", true);
			this.creeper_explodes_faster = builder.translation("tep.midnightconfig.creeper_explodes_faster")
					.define("Creeper Explodes Faster", true);
			this.creeper_attacks_villagers = builder.translation("tep.midnightconfig.creeper_attacks_villagers")
					.define("Creeper Attacks Villagers", true);
			this.creeper_attacks_irongolems = builder.translation("tep.midnightconfig.creeper_attacks_irongolems")
					.define("Creepers Attack Iron Golems", true);
			this.creeper_blowsup_door = builder.translation("tep.midnightconfig.creeper_blowsup_door")
					.define("Creepers Blowup Doors", true);
			this.creeper_power = builder.translation("tep.midnightconfig.creeper_power")
					.defineInRange("Creeper Explosion Power", 3, 1, Integer.MAX_VALUE);
			builder.pop();
			builder.push("Ghast");
			this.ghast_fire_power = builder.translation("tep.midnightconfig.ghast_fire_power")
					.defineInRange("Ghast Fireball Power", 4, 1, Integer.MAX_VALUE);
			this.ghast_attacks_villagers = builder.translation("tep.midnightconfig.wither_attacks_villagers")
					.define("Ghast Attacks Villagers", true);
			builder.pop();
			builder.push("Zombie");
			this.zombies_break_door = builder.translation("tep.midnightconfig.zombies_break_door")
					.defineInRange("Zombie Break Door Chance", 80, 1, Integer.MAX_VALUE);
			this.zombies_dont_burn = builder.translation("tep.midnightconfig.zombies_dont_burn")
					.define("Zombies Don't Burn", true);
			this.zombies_enchanted_more = builder.translation("tep.midnightconfig.zombies_enchanted_more")
					.defineInRange("Zombie Enchanted Items Chance", 80, 1, Integer.MAX_VALUE);
			this.zombies_better_gear = builder.translation("tep.midnightconfig.zombies_better_gear")
					.define("Zombies Spawn With Better Gear", true);
			this.zombies_runners = builder.translation("tep.midnightconfig.zombies_runners").define("Runner Zombies",
					true);
			this.zombiepiglin_onsight = builder.translation("tep.midnightconfig.zombiepiglin_onsight")
					.define("Zombie Piglins Attack On Sight", true);
			this.zombiepiglin_attacks_villagers = builder
					.translation("tep.midnightconfig.zombiepiglin_attacks_villagers")
					.define("Zombie Piglins Attacks Villagers", true);
			builder.pop();
			builder.push("Skeletons");
			this.skeletons_enchanted_more = builder.translation("tep.midnightconfig.skeletons_enchanted_more")
					.defineInRange("Skeleton Enchanted Items Chance", 80, 1, Integer.MAX_VALUE);
			this.skeletons_attacks_villagers = builder.translation("tep.midnightconfig.skeletons_attacks_villagers")
					.define("Skeletons Attacks Villagers", true);
			this.skeletons_dont_burn = builder.translation("tep.midnightconfig.skeletons_dont_burn")
					.define("Skeletons Don't Burn", true);
			builder.pop();
			builder.push("Enderman");
			this.enderman_always_attack = builder.translation("tep.midnightconfig.enderman_always_attack")
					.define("Enderman Always Attacks", true);
			this.enderman_attacks_villagers = builder.translation("tep.midnightconfig.enderman_attacks_villagers")
					.define("Enderman Attacks Villagers", true);
			builder.pop();
			builder.push("Spiders");
			this.spider_always_attack = builder.translation("tep.midnightconfig.spider_always_attack")
					.define("Spiders Always Attack", true);
			this.spider_attacks_villagers = builder.translation("tep.midnightconfig.spider_attacks_villagers")
					.define("Spiders Attacks Villagers", true);
			this.spider_always_jockeys = builder.translation("tep.midnightconfig.spider_always_jockeys")
					.define("Spiders Always Jockeys", true);
			builder.pop();
			builder.push("Phantoms");
			this.phantoms_always_spawn = builder.translation("tep.midnightconfig.phantoms_always_spawn")
					.define("Phantoms Always Spawn", true);
			builder.pop();
			builder.push("Blaze");
			this.blaze_attacks_villagers = builder.translation("tep.midnightconfig.blaze_attacks_villagers")
					.define("Blaze Attacks Villagers", true);
			this.blaze_always_onfire = builder.translation("tep.midnightconfig.blaze_always_onfire")
					.define("Blaze Always On Fire", true);
			builder.pop();
			builder.push("Magma Cube");
			this.magma_render_onfire = builder.translation("tep.midnightconfig.magma_render_onfire")
					.define("Magma Render On Fire", false);
			this.magma_onfire = builder.translation("tep.midnightconfig.magma_onfire").define("Magma Always On Fire",
					true);
			builder.pop();
			builder.push("Endermite");
			this.endermite_attacks_villagers = builder.translation("tep.midnightconfig.endermite_attacks_villagers")
					.define("Endermite Attacks Villagers", true);
			builder.pop();
			builder.push("Shulker");
			this.shulker_attacks_villagers = builder.translation("tep.midnightconfig.shulker_attacks_villagers")
					.define("Shulker Attacks Villagers", true);
			builder.pop();
			builder.push("Silverfish");
			this.silverfish_attacks_villagers = builder.translation("tep.midnightconfig.silverfish_attacks_villagers")
					.define("Silverfish Attacks Villagers", true);
			builder.pop();
			builder.push("Slimes");
			this.slimes_attacks_villagers = builder.translation("tep.midnightconfig.slimes_attacks_villagers")
					.define("Slimes Attacks Villagers", true);
			builder.pop();
			builder.push("Vex");
			this.vex_attacks_villagers = builder.translation("tep.midnightconfig.vex_attacks_villagers")
					.define("Vex Attacks Villagers", true);
			builder.pop();
			builder.push("Wither");
			this.wither_attacks_villagers = builder.translation("tep.midnightconfig.wither_attacks_villagers")
					.define("Wither Attacks Villagers", true);
			builder.pop();
		}
	}

	public static final Server SERVER;
	public static final ForgeConfigSpec SERVER_SPEC;

	static {
		Pair<Server, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Server::new);
		SERVER = commonSpecPair.getLeft();
		SERVER_SPEC = commonSpecPair.getRight();
	}

	public static void loadConfig(ForgeConfigSpec config, String path) {
		final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave()
				.writingMode(WritingMode.REPLACE).build();
		file.load();
		config.setConfig(file);
	}

}
