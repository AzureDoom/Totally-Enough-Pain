package mod.azure.tep.config;

import mod.azure.azurelib.config.Config;
import mod.azure.azurelib.config.Configurable;

@Config(id = "tep")
public class TEPConfig {

	@Configurable
	public boolean monsters_can_warden_sense = true;
	@Configurable
	public int monster_sensing_range = 32;

	@Configurable
	public boolean creeper_doesnt_stop = true;
	@Configurable
	public boolean creeper_always_charged = true;
	@Configurable
	public boolean creeper_explodes_faster = true;
	@Configurable
	public boolean creeper_attacks_villagers = true;
	@Configurable
	public boolean creeper_attacks_irongolems = true;
	@Configurable
	public boolean creeper_blowsup_door = true;
	@Configurable
	public int creeper_power = 3;

	@Configurable
	public int ghast_fire_power = 4;
	@Configurable
	public boolean ghast_attacks_villagers = true;

	@Configurable
	public int zombies_break_door = 80;
	@Configurable
	public boolean zombies_dont_burn = true;
	@Configurable
	public float zombies_enchanted_more = 80;
	@Configurable
	public boolean zombies_better_gear = true;
	@Configurable
	public boolean zombies_runners = true;
	@Configurable
	public boolean zombiepiglin_onsight = true;
	@Configurable
	public boolean zombiepiglin_attacks_villagers = true;

	@Configurable
	public float skeletons_enchanted_more = 80;
	@Configurable
	public boolean skeletons_attacks_villagers = true;
	@Configurable
	public boolean skeletons_dont_burn = true;

	@Configurable
	public boolean enderman_always_attack = true;
	@Configurable
	public boolean enderman_attacks_villagers = true;

	@Configurable
	public boolean spider_always_attack = true;
	@Configurable
	public boolean spider_attacks_villagers = true;
	@Configurable
	public boolean spider_always_jockeys = true;

	@Configurable
	public boolean phantoms_always_spawn = true;

	@Configurable
	public boolean blaze_attacks_villagers = true;
	@Configurable
	public boolean blaze_always_onfire = true;

	@Configurable
	public boolean magma_render_onfire = false;
	@Configurable
	public boolean magma_onfire = true;

	@Configurable
	public boolean endermite_attacks_villagers = true;

	@Configurable
	public boolean shulker_attacks_villagers = true;

	@Configurable
	public boolean silverfish_attacks_villagers = true;

	@Configurable
	public boolean slimes_attacks_villagers = true;

	@Configurable
	public boolean vex_attacks_villagers = true;

	@Configurable
	public boolean wither_attacks_villagers = true;

}
