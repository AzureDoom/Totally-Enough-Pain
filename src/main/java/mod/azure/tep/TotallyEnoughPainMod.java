package mod.azure.tep;

import eu.midnightdust.lib.config.MidnightConfig;
import mod.azure.tep.config.TEPConfig;
import net.fabricmc.api.ModInitializer;

public class TotallyEnoughPainMod implements ModInitializer {

	public static TEPConfig config;

	@Override
	public void onInitialize() {
		MidnightConfig.init("tep", TEPConfig.class);
	}
}
