package mod.azure.tep.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "tep")
public class TEPConfig implements ConfigData {

	@ConfigEntry.Gui.CollapsibleObject
	public Blaze blaze = new Blaze();

	@ConfigEntry.Gui.CollapsibleObject
	public Creepers creepers = new Creepers();

	@ConfigEntry.Gui.CollapsibleObject
	public Enderman enderman = new Enderman();

	@ConfigEntry.Gui.CollapsibleObject
	public Endermite endermite = new Endermite();

	@ConfigEntry.Gui.CollapsibleObject
	public Ghasts ghasts = new Ghasts();

	@ConfigEntry.Gui.CollapsibleObject
	public Magma magma = new Magma();

	@ConfigEntry.Gui.CollapsibleObject
	public Phantoms phantoms = new Phantoms();
	
	@ConfigEntry.Gui.CollapsibleObject
	public Shulker shulker = new Shulker();

	@ConfigEntry.Gui.CollapsibleObject
	public Silverfish silverfish = new Silverfish();

	@ConfigEntry.Gui.CollapsibleObject
	public Skeletons skeletons = new Skeletons();

	@ConfigEntry.Gui.CollapsibleObject
	public Slime slimes = new Slime();

	@ConfigEntry.Gui.CollapsibleObject
	public Spiders spiders = new Spiders();

	@ConfigEntry.Gui.CollapsibleObject
	public Vex vexs = new Vex();

	@ConfigEntry.Gui.CollapsibleObject
	public Wither wither = new Wither();

	@ConfigEntry.Gui.CollapsibleObject
	public Zombies zombies = new Zombies();

	public static class Creepers {
		public boolean creeper_doesnt_stop = true;
		public boolean creeper_always_charged = true;
		public boolean creeper_explodes_faster = true;
		public boolean creeper_attacks_villagers = true;
		public boolean creeper_attacks_irongolems = true;
		public boolean creeper_blowsup_door = true;
		public int creeper_power = 3;
	}

	public static class Ghasts {
		public int ghast_fire_power = 4;
		public boolean ghast_attacks_villagers = true;
	}

	public static class Zombies {
		public int zombies_break_door = 80;
		public boolean zombies_dont_burn = true;
		public float zombies_enchanted_more = 80;
		public boolean zombies_better_gear = true;
		public boolean zombies_runners = true;
		public boolean zombiepiglin_onsight = true;
		public boolean zombiepiglin_attacks_villagers = true;
	}

	public static class Skeletons {
		public float skeletons_enchanted_more = 80;
		public boolean skeletons_attacks_villagers = true;
		public boolean skeletons_dont_burn = true;
	}

	public static class Enderman {
		public boolean enderman_always_attack = true;
		public boolean enderman_attacks_villagers = true;
	}

	public static class Spiders {
		public boolean spider_always_attack = true;
		public boolean spider_attacks_villagers = true;
		public boolean spider_always_jockeys = true;
	}

	public static class Phantoms {
		public boolean phantoms_always_spawn = true;
	}

	public static class Blaze {
		public boolean blaze_attacks_villagers = true;
		public boolean blaze_always_onfire = true;
	}

	public static class Magma {
		public boolean magma_render_onfire = false;
		public boolean magma_onfire = true;
	}

	public static class Endermite {
		public boolean endermite_attacks_villagers = true;
	}

	public static class Shulker {
		public boolean shulker_attacks_villagers = true;
	}

	public static class Silverfish {
		public boolean silverfish_attacks_villagers = true;
	}

	public static class Slime {
		public boolean slimes_attacks_villagers = true;
	}

	public static class Vex {
		public boolean vex_attacks_villagers = true;
	}

	public static class Wither {
		public boolean wither_attacks_villagers = true;
	}

}
