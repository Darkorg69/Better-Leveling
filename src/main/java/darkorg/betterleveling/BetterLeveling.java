package darkorg.betterleveling;

import darkorg.betterleveling.config.ServerConfig;
import darkorg.betterleveling.registry.SkillRegistry;
import darkorg.betterleveling.registry.SpecRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;

@Mod(BetterLeveling.MOD_ID)
public class BetterLeveling {
    public static final String MOD_ID = "betterleveling";

    public static IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

    public BetterLeveling() {
        ServerConfig.init();
        SpecRegistry.init();
        SkillRegistry.init();
        forgeEventBus.register(this);
    }
}