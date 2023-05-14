package mod.azure.tep;

import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.format.ConfigFormats;
import mod.azure.tep.config.TEPConfig;
import net.fabricmc.api.ModInitializer;

public class TotallyEnoughPainMod implements ModInitializer {

	public static TEPConfig config;

	@Override
	public void onInitialize() {
		config = Configuration.registerConfig(TEPConfig.class, ConfigFormats.json()).getConfigInstance();
	}
}
