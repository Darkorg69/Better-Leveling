package darkorg.betterleveling.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ServerConfig {
    public static ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.BooleanValue resetOnDeath;
    public static ForgeConfigSpec.BooleanValue lockBoundFurnaces;
    public static ForgeConfigSpec.IntValue firstSpecLevelCost;

    private static void build() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("Better Leveling Server Config");

        resetOnDeath = builder.comment("If all progress should be lost on death. (Hardcore mode)").define("resetOnDeath", false);
        lockBoundFurnaces = builder.comment("If set to true, bound machines (ex.Furnace) will be usable only by their owners").define("lockBoundFurnaces", false);
        firstSpecLevelCost = builder.comment("How much levels should the player have before choosing his first specialization (if set to 0, none will be required)").defineInRange("firstSpecLevelCost", 5, 0, Integer.MAX_VALUE);

        builder.pop();
        SERVER_CONFIG = builder.build();
    }

    public static void init() {
        build();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG);
    }
}
