package mod.azure.tep;

import mod.azure.tep.config.TEPConfig;
import net.minecraft.resources.ResourceLocation;

public class CommonMod {
	public static TEPConfig config;
    public static final String MOD_ID = "tep";

    public static final ResourceLocation modResource(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
