package mod.azure.tep;

import mod.azure.azurelib.common.internal.common.AzureLibMod;
import mod.azure.azurelib.common.internal.common.config.format.ConfigFormats;
import mod.azure.tep.config.TEPConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(CommonMod.MOD_ID)
public final class NeoForgeMod {

    public NeoForgeMod(IEventBus modEventBus) {
        CommonMod.config = AzureLibMod.registerConfig(TEPConfig.class, ConfigFormats.json()).getConfigInstance();
    }
}
