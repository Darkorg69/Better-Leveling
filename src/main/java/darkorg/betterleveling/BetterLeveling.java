package darkorg.betterleveling;

import darkorg.betterleveling.config.ServerConfig;
import darkorg.betterleveling.registry.SkillRegistry;
import darkorg.betterleveling.registry.SpecRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BetterLeveling.MOD_ID)
public class BetterLeveling {
    public static final String MOD_ID = "betterleveling";
    public static final Logger LOGGER = LogManager.getLogger();

    public BetterLeveling() {
        ServerConfig.init();
        SpecRegistry.init();
        SkillRegistry.init();
        MinecraftForge.EVENT_BUS.register(this);
    }
}