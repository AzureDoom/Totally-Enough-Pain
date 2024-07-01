package mod.azure.tep;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(CommonMod.MOD_ID)
public final class NeoForgeMod {

    public NeoForgeMod(IEventBus modEventBus) {
        CommonMod.init();
    }
}
