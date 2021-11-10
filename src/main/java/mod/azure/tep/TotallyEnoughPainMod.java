package mod.azure.tep;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import mod.azure.tep.config.TEPConfig;
import net.fabricmc.api.ModInitializer;

public class TotallyEnoughPainMod implements ModInitializer {

	public static TEPConfig config;

	@Override
	public void onInitialize() {
		AutoConfig.register(TEPConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(TEPConfig.class).getConfig();
	}
}
