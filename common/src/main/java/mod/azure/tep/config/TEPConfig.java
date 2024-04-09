package mod.azure.tep.config;

import mod.azure.azurelib.common.api.common.config.Config;
import mod.azure.azurelib.common.internal.common.config.Configurable;

@Config(id = "tep")
public class TEPConfig {

    @Configurable
    @Configurable.Synchronized
    public boolean monsters_can_warden_sense = true;
    @Configurable
    @Configurable.Synchronized
    public int monster_sensing_range = 32;

    @Configurable
    @Configurable.Synchronized
    public boolean creeper_doesnt_stop = true;
    @Configurable
    @Configurable.Synchronized
    public boolean creeper_always_charged = true;
    @Configurable
    @Configurable.Synchronized
    public boolean creeper_explodes_faster = true;
    @Configurable
    @Configurable.Synchronized
    public boolean creeper_attacks_villagers = true;
    @Configurable
    @Configurable.Synchronized
    public boolean creeper_attacks_irongolems = true;
    @Configurable
    @Configurable.Synchronized
    public boolean creeper_blowsup_door = true;
    @Configurable
    @Configurable.Synchronized
    public int creeper_power = 3;

    @Configurable
    @Configurable.Synchronized
    public int ghast_fire_power = 4;
    @Configurable
    @Configurable.Synchronized
    public boolean ghast_attacks_villagers = true;

    @Configurable
    @Configurable.Synchronized
    public boolean zombies_break_doors_always = true;
    @Configurable
    @Configurable.Synchronized
    public int zombies_break_door = 80;
    @Configurable
    @Configurable.Synchronized
    public boolean zombies_dont_burn = true;
    @Configurable
    @Configurable.Synchronized
    public float zombies_enchanted_more = 80;
    @Configurable
    @Configurable.Synchronized
    public boolean zombies_better_gear = true;
    @Configurable
    @Configurable.Synchronized
    public boolean zombies_runners = true;
    @Configurable
    @Configurable.Synchronized
    public boolean zombiepiglin_onsight = true;
    @Configurable
    @Configurable.Synchronized
    public boolean zombiepiglin_attacks_villagers = true;

    @Configurable
    @Configurable.Synchronized
    public float skeletons_enchanted_more = 80;
    @Configurable
    @Configurable.Synchronized
    public boolean skeletons_attacks_villagers = true;
    @Configurable
    @Configurable.Synchronized
    public boolean skeletons_dont_burn = true;

    @Configurable
    @Configurable.Synchronized
    public boolean enderman_always_attack = true;
    @Configurable
    @Configurable.Synchronized
    public boolean enderman_attacks_villagers = true;

    @Configurable
    @Configurable.Synchronized
    public boolean spider_always_attack = true;
    @Configurable
    @Configurable.Synchronized
    public boolean spider_attacks_villagers = true;
    @Configurable
    @Configurable.Synchronized
    public boolean spider_always_jockeys = true;

    @Configurable
    @Configurable.Synchronized
    public boolean phantoms_always_spawn = true;

    @Configurable
    @Configurable.Synchronized
    public boolean blaze_attacks_villagers = true;
    @Configurable
    @Configurable.Synchronized
    public boolean blaze_always_onfire = true;

    @Configurable
    @Configurable.Synchronized
    public boolean magma_render_onfire = false;
    @Configurable
    @Configurable.Synchronized
    public boolean magma_onfire = true;

    @Configurable
    @Configurable.Synchronized
    public boolean endermite_attacks_villagers = true;

    @Configurable
    @Configurable.Synchronized
    public boolean shulker_attacks_villagers = true;

    @Configurable
    @Configurable.Synchronized
    public boolean silverfish_attacks_villagers = true;

    @Configurable
    @Configurable.Synchronized
    public boolean slimes_attacks_villagers = true;

    @Configurable
    @Configurable.Synchronized
    public boolean vex_attacks_villagers = true;

    @Configurable
    @Configurable.Synchronized
    public boolean wither_attacks_villagers = true;

}
