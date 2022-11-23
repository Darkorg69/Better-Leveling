package darkorg.betterleveling.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ServerConfig {
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> RESET_ON_DEATH;
    public static final ForgeConfigSpec.ConfigValue<Boolean> LOCK_BOUND_MACHINES;
    public static final ForgeConfigSpec.ConfigValue<Integer> FIRST_SPEC_COST;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SIMPLE_HARVEST;

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    static {
        BUILDER.push("Better Leveling Server Configuration");

        RESET_ON_DEATH = BUILDER.comment("If all progress should be lost on death")
                .define("Reset on death", false);
        LOCK_BOUND_MACHINES = BUILDER.comment("If bound machines should be usable only by their owners")
                .define("Lock bound machines", false);
        FIRST_SPEC_COST = BUILDER.comment("How much levels the player needs before choosing his first specialization")
                .defineInRange("First specialization cost", 5, 0, Integer.MAX_VALUE);
        SIMPLE_HARVEST = BUILDER.comment("If simple right-click harvesting should be enabled.")
                .comment("Compatible with the Harvest-Efficiency skill by default")
                .comment("Other mods with the same feature might be incompatible (spawn too much loot, not spawning bonus loot from skills...)")
                .define("Simple Harvest", true);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    public static void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SPEC);
    }
}
