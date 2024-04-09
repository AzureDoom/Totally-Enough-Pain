package mod.azure.tep;

import mod.azure.azurelib.common.internal.common.AzureLibMod;
import mod.azure.azurelib.common.internal.common.config.format.ConfigFormats;
import mod.azure.tep.config.TEPConfig;
import net.fabricmc.api.ModInitializer;

public final class FabricLibMod implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonMod.config = AzureLibMod.registerConfig(TEPConfig.class, ConfigFormats.json()).getConfigInstance();
    }
}
