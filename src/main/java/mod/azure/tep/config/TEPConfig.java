package mod.azure.tep.config;

public class TEPConfig extends CustomMidnightConfig {

	@Entry
	public static boolean monsters_can_warden_sense = true;
	@Entry
	public static int monster_sensing_range = 32;
	
	@Entry
	public static boolean creeper_doesnt_stop = true;
	@Entry
	public static boolean creeper_always_charged = true;
	@Entry
	public static boolean creeper_explodes_faster = true;
	@Entry
	public static boolean creeper_attacks_villagers = true;
	@Entry
	public static boolean creeper_attacks_irongolems = true;
	@Entry
	public static boolean creeper_blowsup_door = true;
	@Entry
	public static int creeper_power = 3;

	@Entry
	public static int ghast_fire_power = 4;
	@Entry
	public static boolean ghast_attacks_villagers = true;

	@Entry
	public static int zombies_break_door = 80;
	@Entry
	public static boolean zombies_dont_burn = true;
	@Entry
	public static float zombies_enchanted_more = 80;
	@Entry
	public static boolean zombies_better_gear = true;
	@Entry
	public static boolean zombies_runners = true;
	@Entry
	public static boolean zombiepiglin_onsight = true;
	@Entry
	public static boolean zombiepiglin_attacks_villagers = true;

	@Entry
	public static float skeletons_enchanted_more = 80;
	@Entry
	public static boolean skeletons_attacks_villagers = true;
	@Entry
	public static boolean skeletons_dont_burn = true;

	@Entry
	public static boolean enderman_always_attack = true;
	@Entry
	public static boolean enderman_attacks_villagers = true;

	@Entry
	public static boolean spider_always_attack = true;
	@Entry
	public static boolean spider_attacks_villagers = true;
	@Entry
	public static boolean spider_always_jockeys = true;

	@Entry
	public static boolean phantoms_always_spawn = true;

	@Entry
	public static boolean blaze_attacks_villagers = true;
	@Entry
	public static boolean blaze_always_onfire = true;

	@Entry
	public static boolean magma_render_onfire = false;
	@Entry
	public static boolean magma_onfire = true;

	@Entry
	public static boolean endermite_attacks_villagers = true;

	@Entry
	public static boolean shulker_attacks_villagers = true;

	@Entry
	public static boolean silverfish_attacks_villagers = true;

	@Entry
	public static boolean slimes_attacks_villagers = true;

	@Entry
	public static boolean vex_attacks_villagers = true;

	@Entry
	public static boolean wither_attacks_villagers = true;

}
