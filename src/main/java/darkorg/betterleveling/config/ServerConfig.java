package darkorg.betterleveling.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ServerConfig {
    public static final ForgeConfigSpec.ConfigValue<Boolean> RESET_ON_DEATH;
    public static final ForgeConfigSpec.ConfigValue<Boolean> LOCK_BOUND_MACHINES;
    public static final ForgeConfigSpec.ConfigValue<Boolean> VANILLA_CRIT_DISABLED;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SIMPLE_HARVEST_ENABLED;

    public static final ForgeConfigSpec.ConfigValue<Integer> FIRST_SPEC_COST;
    public static final ForgeConfigSpec.ConfigValue<Integer> SPEC_LEVEL_COST;

    public static final ForgeConfigSpec.ConfigValue<Double> XP_REFUND_FACTOR;

    public static final ForgeConfigSpec.ConfigValue<Integer> STRENGTH_MAX_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> STRENGTH_COST_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Double> STRENGTH_BONUS_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<String> STRENGTH_PREREQUISITES;

    public static final ForgeConfigSpec.ConfigValue<Integer> CRITICAL_STRIKE_MAX_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> CRITICAL_STRIKE_COST_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Double> CRITICAL_STRIKE_BONUS_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<String> CRITICAL_STRIKE_PREREQUISITES;

    public static final ForgeConfigSpec.ConfigValue<Integer> ARROW_SPEED_MAX_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> ARROW_SPEED_COST_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Double> ARROW_SPEED_BONUS_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<String> ARROW_SPEED_PREREQUISITES;

    public static final ForgeConfigSpec.ConfigValue<Integer> QUICK_DRAW_MAX_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> QUICK_DRAW_COST_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Double> QUICK_DRAW_BONUS_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<String> QUICK_DRAW_PREREQUISITES;

    public static final ForgeConfigSpec.ConfigValue<Integer> IRON_SKIN_MAX_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> IRON_SKIN_COST_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Double> IRON_SKIN_BONUS_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<String> IRON_SKIN_PREREQUISITES;

    public static final ForgeConfigSpec.ConfigValue<Integer> SNEAK_SPEED_MAX_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> SNEAK_SPEED_COST_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Double> SNEAK_SPEED_BONUS_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<String> SNEAK_SPEED_PREREQUISITES;

    public static final ForgeConfigSpec.ConfigValue<Integer> GREEN_THUMB_TICK_RATE;
    public static final ForgeConfigSpec.ConfigValue<Integer> GREEN_THUMB_MAX_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> GREEN_THUMB_COST_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Double> GREEN_THUMB_BONUS_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<String> GREEN_THUMB_PREREQUISITES;

    public static final ForgeConfigSpec.ConfigValue<Integer> HARVEST_PROFICIENCY_MAX_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> HARVEST_PROFICIENCY_COST_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Double> HARVEST_PROFICIENCY_BONUS_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Double> HARVEST_PROFICIENCY_POTENTIAL_LOOT_BOUND;
    public static final ForgeConfigSpec.ConfigValue<String> HARVEST_PROFICIENCY_PREREQUISITES;

    public static final ForgeConfigSpec.ConfigValue<Integer> SKINNING_MAX_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> SKINNING_COST_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Double> SKINNING_BONUS_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Double> SKINNING_POTENTIAL_LOOT_BOUND;
    public static final ForgeConfigSpec.ConfigValue<String> SKINNING_PREREQUISITES;

    public static final ForgeConfigSpec.ConfigValue<Integer> MEAT_GATHERING_MAX_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> MEAT_GATHERING_COST_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Double> MEAT_GATHERING_BONUS_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Double> MEAT_GATHERING_POTENTIAL_LOOT_BOUND;
    public static final ForgeConfigSpec.ConfigValue<String> MEAT_GATHERING_PREREQUISITES;

    public static final ForgeConfigSpec.ConfigValue<Integer> SWIM_SPEED_MAX_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> SWIM_SPEED_COST_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Double> SWIM_SPEED_BONUS_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<String> SWIM_SPEED_PREREQUISITES;

    public static final ForgeConfigSpec.ConfigValue<Integer> COOKING_SPEED_MAX_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> COOKING_SPEED_COST_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Double> COOKING_SPEED_BONUS_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<String> COOKING_SPEED_PREREQUISITES;

    public static final ForgeConfigSpec.ConfigValue<Integer> STONECUTTING_MAX_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> STONECUTTING_COST_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Double> STONECUTTING_BONUS_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<String> STONECUTTING_PREREQUISITES;

    public static final ForgeConfigSpec.ConfigValue<Integer> PROSPECTING_MAX_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> PROSPECTING_COST_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Double> PROSPECTING_BONUS_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Double> PROSPECTING_POTENTIAL_LOOT_BOUND;
    public static final ForgeConfigSpec.ConfigValue<String> PROSPECTING_PREREQUISITES;

    public static final ForgeConfigSpec.ConfigValue<Integer> WOODCUTTING_MAX_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> WOODCUTTING_COST_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Double> WOODCUTTING_BONUS_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<String> WOODCUTTING_PREREQUISITES;

    public static final ForgeConfigSpec.ConfigValue<Integer> TREASURE_HUNTING_MAX_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> TREASURE_HUNTING_COST_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Double> TREASURE_HUNTING_BONUS_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> TREASURE_HUNTING_UNCOMMON_WEIGHT;
    public static final ForgeConfigSpec.ConfigValue<Integer> TREASURE_HUNTING_COMMON_WEIGHT;
    public static final ForgeConfigSpec.ConfigValue<Integer> TREASURE_HUNTING_RARE_WEIGHT;
    public static final ForgeConfigSpec.ConfigValue<String> TREASURE_HUNTING_PREREQUISITES;

    public static final ForgeConfigSpec.ConfigValue<Integer> SOFT_LANDING_MAX_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> SOFT_LANDING_COST_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Double> SOFT_LANDING_BONUS_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<String> SOFT_LANDING_PREREQUISITES;

    public static final ForgeConfigSpec.ConfigValue<Integer> SPRINT_SPEED_MAX_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> SPRINT_SPEED_COST_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Double> SPRINT_SPEED_BONUS_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<String> SPRINT_SPEED_PREREQUISITES;

    private static final ForgeConfigSpec SPEC;
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    static {
        BUILDER.push("General");
        RESET_ON_DEATH = BUILDER.comment("If all progress should be lost on death").define("Reset on death", false);
        SIMPLE_HARVEST_ENABLED = BUILDER.comment("If right-click harvesting should be enabled.").define("Simple Harvest", true);
        VANILLA_CRIT_DISABLED = BUILDER.comment("If set to true, vanilla crits will be disabled").define("Vanilla Crit Disabled", true);
        LOCK_BOUND_MACHINES = BUILDER.comment("If enabled, bound machines will be usable only by their owner").define("Lock bound machines", false);
        BUILDER.pop();

        BUILDER.push("Specializations");
        SPEC_LEVEL_COST = BUILDER.comment("Define how much levels the player needs to unlock a specialization").defineInRange("Specialization Level Cost", 30, 0, Integer.MAX_VALUE);
        FIRST_SPEC_COST = BUILDER.comment("Define how much levels the player needs to unlock his FIRST specialization").defineInRange("First Specialization Level Cost", 5, 0, Integer.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Skills");
        XP_REFUND_FACTOR = BUILDER.comment("Define amount of XP refunded when decreasing skill level").defineInRange("XP Refund Factor", 0.5D, 0.0D, 1.0D);

        BUILDER.push("Combat");

        BUILDER.push("Strength").comment("Increase damage output");
        STRENGTH_MAX_LEVEL = BUILDER.defineInRange("Maximum level", 10, 1, Integer.MAX_VALUE);
        STRENGTH_COST_PER_LEVEL = BUILDER.defineInRange("Cost per level", 40, 1, Integer.MAX_VALUE);
        STRENGTH_BONUS_PER_LEVEL = BUILDER.defineInRange("Bonus per level", 0.075D, 0.0D, Double.MAX_VALUE);
        STRENGTH_PREREQUISITES = BUILDER.define("Prerequisites", "");
        BUILDER.pop();

        BUILDER.push("Critical-Strike").comment("Increase critical chance");
        CRITICAL_STRIKE_MAX_LEVEL = BUILDER.defineInRange("Maximum level", 10, 1, Integer.MAX_VALUE);
        CRITICAL_STRIKE_COST_PER_LEVEL = BUILDER.defineInRange("Cost per level", 50, 1, Integer.MAX_VALUE);
        CRITICAL_STRIKE_BONUS_PER_LEVEL = BUILDER.defineInRange("Bonus per level", 0.05D, 0.0D, 1.0D);
        CRITICAL_STRIKE_PREREQUISITES = BUILDER.define("Prerequisites", "strength:4");
        BUILDER.pop();

        BUILDER.push("Arrow-Speed").comment("Increase arrow velocity");
        ARROW_SPEED_MAX_LEVEL = BUILDER.defineInRange("Maximum level", 10, 1, Integer.MAX_VALUE);
        ARROW_SPEED_COST_PER_LEVEL = BUILDER.defineInRange("Cost per level", 40, 1, Integer.MAX_VALUE);
        ARROW_SPEED_BONUS_PER_LEVEL = BUILDER.defineInRange("Bonus per level", 0.035D, 0.0D, Double.MAX_VALUE);
        ARROW_SPEED_PREREQUISITES = BUILDER.define("Prerequisites", "");
        BUILDER.pop();

        BUILDER.push("Quick-Draw").comment("Increase bow charge speed");
        QUICK_DRAW_MAX_LEVEL = BUILDER.defineInRange("Maximum level", 10, 1, Integer.MAX_VALUE);
        QUICK_DRAW_COST_PER_LEVEL = BUILDER.defineInRange("Cost per level", 50, 1, Integer.MAX_VALUE);
        QUICK_DRAW_BONUS_PER_LEVEL = BUILDER.defineInRange("Bonus per level", 0.05D, Double.MIN_VALUE, Double.MAX_VALUE);
        QUICK_DRAW_PREREQUISITES = BUILDER.define("Prerequisites", "arrow_speed:4");
        BUILDER.pop();

        BUILDER.push("Iron-Skin").comment("Decrease damage taken");
        IRON_SKIN_MAX_LEVEL = BUILDER.defineInRange("Maximum level", 10, 1, Integer.MAX_VALUE);
        IRON_SKIN_COST_PER_LEVEL = BUILDER.defineInRange("Cost per level", 40, 1, Integer.MAX_VALUE);
        IRON_SKIN_BONUS_PER_LEVEL = BUILDER.defineInRange("Bonus per level", 0.035D, 0.0D, 1.0D);
        IRON_SKIN_PREREQUISITES = BUILDER.define("Prerequisites", "strength:7");
        BUILDER.pop();

        BUILDER.push("Sneak-Speed").comment("Increase speed while sneaking");
        SNEAK_SPEED_MAX_LEVEL = BUILDER.defineInRange("Maximum level", 10, 1, Integer.MAX_VALUE);
        SNEAK_SPEED_COST_PER_LEVEL = BUILDER.defineInRange("Cost per level", 40, 1, Integer.MAX_VALUE);
        SNEAK_SPEED_BONUS_PER_LEVEL = BUILDER.defineInRange("Bonus per level", 0.05D, 0.0D, Double.MAX_VALUE);
        SNEAK_SPEED_PREREQUISITES = BUILDER.define("Prerequisites", "");
        BUILDER.pop(2);

        BUILDER.push("Crafting");

        BUILDER.push("Green-Thumb").comment("Every X ticks, chance to grow crops around");
        GREEN_THUMB_TICK_RATE = BUILDER.defineInRange("Tick rate", 100, 20, Integer.MAX_VALUE);
        GREEN_THUMB_MAX_LEVEL = BUILDER.defineInRange("Maximum level", 10, 1, Integer.MAX_VALUE);
        GREEN_THUMB_COST_PER_LEVEL = BUILDER.defineInRange("Cost per level", 40, 1, Integer.MAX_VALUE);
        GREEN_THUMB_BONUS_PER_LEVEL = BUILDER.defineInRange("Bonus per level", 0.02D, 0.0D, Double.MAX_VALUE);
        GREEN_THUMB_PREREQUISITES = BUILDER.define("Prerequisites", "");
        BUILDER.pop();

        BUILDER.push("Harvest-Proficiency").comment("Chance to gather extra crops on harvest");
        HARVEST_PROFICIENCY_MAX_LEVEL = BUILDER.defineInRange("Maximum level", 10, 1, Integer.MAX_VALUE);
        HARVEST_PROFICIENCY_COST_PER_LEVEL = BUILDER.defineInRange("Cost per level", 50, 1, Integer.MAX_VALUE);
        HARVEST_PROFICIENCY_BONUS_PER_LEVEL = BUILDER.defineInRange("Bonus per level", 0.075D, 0.0D, Double.MAX_VALUE);
        HARVEST_PROFICIENCY_POTENTIAL_LOOT_BOUND = BUILDER.defineInRange("Potential loot bound", 1.0D, 0.0D, Double.MAX_VALUE);
        HARVEST_PROFICIENCY_PREREQUISITES = BUILDER.define("Prerequisites", "green_thumb:4");
        BUILDER.pop();

        BUILDER.push("Skinning").comment("Chance to gather extra skins from animals");
        SKINNING_MAX_LEVEL = BUILDER.defineInRange("Maximum level", 10, 1, Integer.MAX_VALUE);
        SKINNING_COST_PER_LEVEL = BUILDER.defineInRange("Cost per level", 40, 1, Integer.MAX_VALUE);
        SKINNING_BONUS_PER_LEVEL = BUILDER.defineInRange("Bonus per level", 0.075D, 0.0D, Double.MAX_VALUE);
        SKINNING_POTENTIAL_LOOT_BOUND = BUILDER.defineInRange("Potential loot bound", 1.0D, 0.0D, Double.MAX_VALUE);
        SKINNING_PREREQUISITES = BUILDER.define("Prerequisites", "");
        BUILDER.pop();

        BUILDER.push("Meat-Gathering").comment("Chance to drop extra meat from animals");
        MEAT_GATHERING_MAX_LEVEL = BUILDER.defineInRange("Maximum level", 10, 1, Integer.MAX_VALUE);
        MEAT_GATHERING_COST_PER_LEVEL = BUILDER.defineInRange("Cost per level", 50, 1, Integer.MAX_VALUE);
        MEAT_GATHERING_BONUS_PER_LEVEL = BUILDER.defineInRange("Bonus per level", 0.05D, 0.0D, Double.MAX_VALUE);
        MEAT_GATHERING_POTENTIAL_LOOT_BOUND = BUILDER.defineInRange("Potential loot bound", 1.0D, 0.0D, Integer.MAX_VALUE);
        MEAT_GATHERING_PREREQUISITES = BUILDER.define("Prerequisites", "skinning:4");
        BUILDER.pop();

        BUILDER.push("Swim-Speed").comment("Increases speed under water");
        SWIM_SPEED_MAX_LEVEL = BUILDER.defineInRange("Maximum level", 10, 1, Integer.MAX_VALUE);
        SWIM_SPEED_COST_PER_LEVEL = BUILDER.defineInRange("Cost per level", 40, 1, Integer.MAX_VALUE);
        SWIM_SPEED_BONUS_PER_LEVEL = BUILDER.defineInRange("Bonus per level", 0.125D, 0.0D, Double.MAX_VALUE);
        SWIM_SPEED_PREREQUISITES = BUILDER.define("Prerequisites", "");
        BUILDER.pop();

        BUILDER.push("Cooking-Speed").comment("Decrease total cooking time");
        COOKING_SPEED_MAX_LEVEL = BUILDER.defineInRange("Maximum level", 10, 1, Integer.MAX_VALUE);
        COOKING_SPEED_COST_PER_LEVEL = BUILDER.defineInRange("Cost per level", 50, 1, Integer.MAX_VALUE);
        COOKING_SPEED_BONUS_PER_LEVEL = BUILDER.defineInRange("Bonus per level", 0.09D, 0.0D, 1.0D);
        COOKING_SPEED_PREREQUISITES = BUILDER.define("Prerequisites", "");
        BUILDER.pop(2);

        BUILDER.push("Mining");
        BUILDER.push("Stonecutting").comment("Increases stone mining speed");
        STONECUTTING_MAX_LEVEL = BUILDER.defineInRange("Maximum level", 10, 1, Integer.MAX_VALUE);
        STONECUTTING_COST_PER_LEVEL = BUILDER.defineInRange("Cost per level", 40, 1, Integer.MAX_VALUE);
        STONECUTTING_BONUS_PER_LEVEL = BUILDER.defineInRange("Bonus per level", 0.05D, 0.0D, Double.MAX_VALUE);
        STONECUTTING_PREREQUISITES = BUILDER.define("Prerequisites", "");
        BUILDER.pop();

        BUILDER.push("Prospecting").comment("Chance to increase yield from ores");
        PROSPECTING_MAX_LEVEL = BUILDER.defineInRange("Maximum level", 10, 1, Integer.MAX_VALUE);
        PROSPECTING_COST_PER_LEVEL = BUILDER.defineInRange("Cost per level", 50, 1, Integer.MAX_VALUE);
        PROSPECTING_BONUS_PER_LEVEL = BUILDER.defineInRange("Bonus per level", 0.05D, 0.0D, Double.MAX_VALUE);
        PROSPECTING_POTENTIAL_LOOT_BOUND = BUILDER.defineInRange("Potential loot bound", 1.0D, 0.0D, Double.MAX_VALUE);
        PROSPECTING_PREREQUISITES = BUILDER.define("Prerequisites", "stonecutting:4");
        BUILDER.pop();

        BUILDER.push("Woodcutting").comment("Increases wood mining speed");
        WOODCUTTING_MAX_LEVEL = BUILDER.defineInRange("Maximum level", 10, 1, Integer.MAX_VALUE);
        WOODCUTTING_COST_PER_LEVEL = BUILDER.defineInRange("Cost per level", 40, 1, Integer.MAX_VALUE);
        WOODCUTTING_BONUS_PER_LEVEL = BUILDER.defineInRange("Bonus per level", 0.05D, 0.0D, Double.MAX_VALUE);
        WOODCUTTING_PREREQUISITES = BUILDER.define("Prerequisites", "");
        BUILDER.pop();

        BUILDER.push("Treasure-Hunting").comment("Random chance to find treasure while digging");
        TREASURE_HUNTING_MAX_LEVEL = BUILDER.defineInRange("Maximum level", 10, 1, Integer.MAX_VALUE);
        TREASURE_HUNTING_COST_PER_LEVEL = BUILDER.defineInRange("Cost per level", 60, 1, Integer.MAX_VALUE);
        TREASURE_HUNTING_BONUS_PER_LEVEL = BUILDER.defineInRange("Bonus per level", 0.025D, 0.0D, Double.MAX_VALUE);
        TREASURE_HUNTING_RARE_WEIGHT = BUILDER.defineInRange("Rare weight", 1, 1, Integer.MAX_VALUE);
        TREASURE_HUNTING_UNCOMMON_WEIGHT = BUILDER.defineInRange("Uncommon weight", 3, 1, Integer.MAX_VALUE);
        TREASURE_HUNTING_COMMON_WEIGHT = BUILDER.defineInRange("Common weight", 6, 1, Integer.MAX_VALUE);
        TREASURE_HUNTING_PREREQUISITES = BUILDER.define("Prerequisites", "prospecting:3");
        BUILDER.pop();

        BUILDER.push("Soft-Landing").comment("Decrease fall damage taken");
        SOFT_LANDING_MAX_LEVEL = BUILDER.defineInRange("Maximum level", 10, 1, Integer.MAX_VALUE);
        SOFT_LANDING_COST_PER_LEVEL = BUILDER.defineInRange("Cost per level", 20, 1, Integer.MAX_VALUE);
        SOFT_LANDING_BONUS_PER_LEVEL = BUILDER.defineInRange("Bonus per level", 0.035D, 0.0D, Double.MAX_VALUE);
        SOFT_LANDING_PREREQUISITES = BUILDER.define("Prerequisites", "");
        BUILDER.pop();

        BUILDER.push("Sprint-Speed").comment("Increases movement speed while sprinting");
        SPRINT_SPEED_MAX_LEVEL = BUILDER.defineInRange("Maximum level", 10, 1, Integer.MAX_VALUE);
        SPRINT_SPEED_COST_PER_LEVEL = BUILDER.defineInRange("Cost per level", 40, 1, Integer.MAX_VALUE);
        SPRINT_SPEED_BONUS_PER_LEVEL = BUILDER.defineInRange("Bonus per level", 0.05D, 0.0D, Double.MAX_VALUE);
        SPRINT_SPEED_PREREQUISITES = BUILDER.define("Prerequisites", "");
        BUILDER.pop(3);

        SPEC = BUILDER.build();
    }

    public static void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SPEC);
    }
}
