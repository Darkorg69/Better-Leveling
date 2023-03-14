package darkorg.betterleveling;

import darkorg.betterleveling.config.ModConfig;
import darkorg.betterleveling.registry.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BetterLeveling.MOD_ID)
public class BetterLeveling {
    public static final String MOD_ID = "betterleveling";
    public static final Logger LOGGER = LogManager.getLogger();

    public BetterLeveling() {
        ModConfig.init();
        ModBlocks.init();
        ModItems.init();
        GlobalLootModifiers.init();
        Specializations.init();
        Skills.init();
        MinecraftForge.EVENT_BUS.register(this);
    }
}