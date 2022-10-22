package mod.azure.tep;

import mod.azure.tep.config.TEPConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(TotallyEnoughPainMod.MODID)
public class TotallyEnoughPainMod {

	public static TotallyEnoughPainMod instance;
	public static final String MODID = "tep";

	public TotallyEnoughPainMod() {
		instance = this;
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, TEPConfig.SERVER_SPEC, "doom-newconfig.toml");
		TEPConfig.loadConfig(TEPConfig.SERVER_SPEC, FMLPaths.CONFIGDIR.get().resolve("doom-newconfig.toml").toString());
		MinecraftForge.EVENT_BUS.register(this);
	}
}
