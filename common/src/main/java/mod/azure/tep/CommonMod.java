package mod.azure.tep;

import mod.azure.azurelib.common.internal.common.AzureLibMod;
import mod.azure.azurelib.common.internal.common.config.format.ConfigFormats;
import mod.azure.tep.config.TEPConfig;
import net.minecraft.resources.ResourceLocation;

public record CommonMod() {
	public static TEPConfig config;
    public static final String MOD_ID = "tep";

    public static void init() {
        config = AzureLibMod.registerConfig(TEPConfig.class, ConfigFormats.json()).getConfigInstance();
    }

    public static ResourceLocation modResource(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
