package mod.azure.tep;

import mod.azure.azurelib.AzureLibMod;
import mod.azure.azurelib.config.format.ConfigFormats;
import mod.azure.tep.config.TEPConfig;
import net.fabricmc.api.ModInitializer;

public class TotallyEnoughPainMod implements ModInitializer {

	public static TEPConfig config;

	@Override
	public void onInitialize() {
		config = AzureLibMod.registerConfig(TEPConfig.class, ConfigFormats.json()).getConfigInstance();
	}
}
