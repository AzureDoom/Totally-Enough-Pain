package mod.azure.tep;

import mod.azure.tep.config.TEPConfig;
import net.fabricmc.api.ModInitializer;

public final class FabricLibMod implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonMod.init();
    }
}
