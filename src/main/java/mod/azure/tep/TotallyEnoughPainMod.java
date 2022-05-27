package mod.azure.tep;

import mod.azure.tep.config.CustomMidnightConfig;
import mod.azure.tep.config.TEPConfig;
import net.fabricmc.api.ModInitializer;

public class TotallyEnoughPainMod implements ModInitializer {

	public static TEPConfig config;

	@Override
	public void onInitialize() {
		CustomMidnightConfig.init("tep", TEPConfig.class);
	}
}
