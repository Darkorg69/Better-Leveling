package darkorg.betterleveling.config;

import darkorg.betterleveling.BetterLeveling;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;
import org.apache.commons.lang3.tuple.Pair;


public class ModConfig {
    public static final Client CLIENT;
    public static final Common COMMON;
    public static final Gameplay GAMEPLAY;
    public static final Specializations SPECIALIZATIONS;
    public static final Skills SKILLS;
    static final ForgeConfigSpec clientSpec;
    static final ForgeConfigSpec commonSpec;
    static final ForgeConfigSpec gameplaySpec;
    static final ForgeConfigSpec specializationsSpec;
    static final ForgeConfigSpec skillsSpec;

    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    static {
        final Pair<Gameplay, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Gameplay::new);
        gameplaySpec = specPair.getRight();
        GAMEPLAY = specPair.getLeft();
    }

    static {
        final Pair<Specializations, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Specializations::new);
        specializationsSpec = specPair.getRight();
        SPECIALIZATIONS = specPair.getLeft();
    }

    static {
        final Pair<Skills, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Skills::new);
        skillsSpec = specPair.getRight();
        SKILLS = specPair.getLeft();
    }

    public static void init() {
        ModLoadingContext.get().registerConfig(Type.CLIENT, clientSpec, BetterLeveling.MOD_ID + "-client.toml");
        //ModLoadingContext.get().registerConfig(Type.COMMON, commonSpec, BetterLeveling.MOD_ID + "-common.toml");
        ModLoadingContext.get().registerConfig(Type.SERVER, gameplaySpec, BetterLeveling.MOD_ID + "-gameplay.toml");
        ModLoadingContext.get().registerConfig(Type.SERVER, specializationsSpec, BetterLeveling.MOD_ID + "-specializations.toml");
        ModLoadingContext.get().registerConfig(Type.SERVER, skillsSpec, BetterLeveling.MOD_ID + "-skills.toml");
    }

    public static class Client {
        public final IntValue strengthRenderRow;
        public final IntValue strengthRenderColumn;
        public final IntValue criticalStrikeRenderRow;
        public final IntValue criticalStrikeRenderColumn;
        public final IntValue quickDrawRenderRow;
        public final IntValue quickDrawRenderColumn;
        public final IntValue arrowSpeedRenderRow;
        public final IntValue arrowSpeedRenderColumn;
        public final IntValue ironSkinRenderRow;
        public final IntValue ironSkinRenderColumn;
        public final IntValue sneakSpeedRenderRow;
        public final IntValue sneakSpeedRenderColumn;
        public final IntValue greenThumbRenderRow;
        public final IntValue greenThumbRenderColumn;
        public final IntValue harvestProficiencyRenderRow;
        public final IntValue harvestProficiencyRenderColumn;
        public final IntValue skinningRenderRow;
        public final IntValue skinningRenderColumn;
        public final IntValue meatGatheringRenderRow;
        public final IntValue meatGatheringRenderColumn;
        public final IntValue swimSpeedRenderRow;
        public final IntValue swimSpeedRenderColumn;
        public final IntValue cookingSpeedRenderRow;
        public final IntValue cookingSpeedRenderColumn;
        public final IntValue stonecuttingRenderRow;
        public final IntValue stonecuttingRenderColumn;
        public final IntValue prospectingRenderRow;
        public final IntValue prospectingRenderColumn;
        public final IntValue woodcuttingRenderRow;
        public final IntValue woodcuttingRenderColumn;
        public final IntValue treasureHuntingRenderRow;
        public final IntValue treasureHuntingRenderColumn;
        public final IntValue softLandingRenderRow;
        public final IntValue softLandingRenderColumn;
        public final IntValue sprintSpeedRenderRow;
        public final IntValue sprintSpeedRenderColumn;

        Client(ForgeConfigSpec.Builder pBuilder) {
            pBuilder.comment("Increase melee damage").push("strength");
            strengthRenderRow = pBuilder.defineInRange("renderRow", 1, 1, 2);
            strengthRenderColumn = pBuilder.defineInRange("renderColumn", 1, 1, 3);
            pBuilder.pop();

            pBuilder.comment("Increase critical chance").push("criticalstrike");
            criticalStrikeRenderRow = pBuilder.defineInRange("renderRow", 2, 1, 2);
            criticalStrikeRenderColumn = pBuilder.defineInRange("renderColumn", 1, 1, 3);
            pBuilder.pop();

            pBuilder.comment("Increase bow charge speed").push("quickdraw");
            quickDrawRenderRow = pBuilder.defineInRange("renderRow", 1, 1, 2);
            quickDrawRenderColumn = pBuilder.defineInRange("renderColumn", 2, 1, 3);
            pBuilder.pop();

            pBuilder.comment("Increase arrow velocity").push("arrowspeed");
            arrowSpeedRenderRow = pBuilder.defineInRange("renderRow", 2, 1, 2);
            arrowSpeedRenderColumn = pBuilder.defineInRange("renderColumn", 2, 1, 3);
            pBuilder.pop();

            pBuilder.comment("Decrease damage taken").push("ironskin");
            ironSkinRenderRow = pBuilder.defineInRange("renderRow", 1, 1, 2);
            ironSkinRenderColumn = pBuilder.defineInRange("renderColumn", 3, 1, 3);
            pBuilder.pop();

            pBuilder.comment("Increase speed while sneaking").push("sneakspeed");
            sneakSpeedRenderRow = pBuilder.defineInRange("renderRow", 2, 1, 2);
            sneakSpeedRenderColumn = pBuilder.defineInRange("renderColumn", 3, 1, 3);
            pBuilder.pop();

            pBuilder.comment("Every X ticks, chance to grow crops around").push("greenthumb");
            greenThumbRenderRow = pBuilder.defineInRange("renderRow", 1, 1, 2);
            greenThumbRenderColumn = pBuilder.defineInRange("renderColumn", 1, 1, 3);
            pBuilder.pop();

            pBuilder.comment("Chance to gather extra crops on harvest").push("harvestproficiency");
            harvestProficiencyRenderRow = pBuilder.defineInRange("renderRow", 2, 1, 2);
            harvestProficiencyRenderColumn = pBuilder.defineInRange("renderColumn", 1, 1, 3);
            pBuilder.pop();

            pBuilder.comment("Chance to gather extra skins from animals").push("skinning");
            skinningRenderRow = pBuilder.defineInRange("renderRow", 1, 1, 2);
            skinningRenderColumn = pBuilder.defineInRange("renderColumn", 2, 1, 3);
            pBuilder.pop();

            pBuilder.comment("Chance to drop extra meat from animals").push("meatgathering");
            meatGatheringRenderRow = pBuilder.defineInRange("renderRow", 2, 1, 2);
            meatGatheringRenderColumn = pBuilder.defineInRange("renderColumn", 2, 1, 3);
            pBuilder.pop();

            pBuilder.comment("Increase speed under water").push("swimspeed");
            swimSpeedRenderRow = pBuilder.defineInRange("renderRow", 1, 1, 2);
            swimSpeedRenderColumn = pBuilder.defineInRange("renderColumn", 3, 1, 3);
            pBuilder.pop();

            pBuilder.comment("Decrease total cooking time").push("cookingspeed");
            cookingSpeedRenderRow = pBuilder.defineInRange("renderRow", 2, 1, 2);
            cookingSpeedRenderColumn = pBuilder.defineInRange("renderColumn", 3, 1, 3);
            pBuilder.pop();

            pBuilder.comment("Increase stone mining speed").push("stonecutting");
            stonecuttingRenderRow = pBuilder.defineInRange("renderRow", 2, 1, 2);
            stonecuttingRenderColumn = pBuilder.defineInRange("renderColumn", 1, 1, 3);
            pBuilder.pop();

            pBuilder.comment("Chance to increase yield from ores").push("prospecting");
            prospectingRenderRow = pBuilder.defineInRange("renderRow", 1, 1, 2);
            prospectingRenderColumn = pBuilder.defineInRange("renderColumn", 2, 1, 3);
            pBuilder.pop();

            pBuilder.comment("Increase wood chopping speed").push("woodcutting");
            woodcuttingRenderRow = pBuilder.defineInRange("renderRow", 1, 1, 2);
            woodcuttingRenderColumn = pBuilder.defineInRange("renderColumn", 1, 1, 3);
            pBuilder.pop();

            pBuilder.comment("Random chance to find treasure while digging").push("treasurehunting");
            treasureHuntingRenderRow = pBuilder.defineInRange("renderRow", 2, 1, 2);
            treasureHuntingRenderColumn = pBuilder.defineInRange("renderColumn", 2, 1, 3);
            pBuilder.pop();

            pBuilder.comment("Decrease fall damage taken").push("softlanding");
            softLandingRenderRow = pBuilder.defineInRange("renderRow", 1, 1, 2);
            softLandingRenderColumn = pBuilder.defineInRange("renderColumn", 3, 1, 3);
            pBuilder.pop();

            pBuilder.comment("Increase movement speed while sprinting").push("sprintspeed");
            sprintSpeedRenderRow = pBuilder.defineInRange("renderRow", 2, 1, 2);
            sprintSpeedRenderColumn = pBuilder.defineInRange("renderColumn", 3, 1, 3);
            pBuilder.pop();
        }
    }

    public static class Common {
        Common(ForgeConfigSpec.Builder pBuilder) {

        }
    }

    public static class Gameplay {
        public final IntValue firstSpecCost;
        public final BooleanValue resetOnDeath;
        public final DoubleValue xpRefundFactor;
        public final BooleanValue lockBoundMachines;
        public final BooleanValue rightClickHarvest;

        Gameplay(ForgeConfigSpec.Builder pBuilder) {
            pBuilder.comment("Settings related to gameplay").push("gameplay");
            firstSpecCost = pBuilder.comment("Define how much levels the player needs to unlock his FIRST specialization").defineInRange("firstSpecCost", 5, 0, Integer.MAX_VALUE);
            resetOnDeath = pBuilder.comment("If all progress should be lost on death").define("resetOnDeath", false);
            xpRefundFactor = pBuilder.comment("Define the amount of XP refunded when decreasing skill level").defineInRange("xpRefundFactor", 0.5D, 0.0D, 1.0D);
            lockBoundMachines = pBuilder.comment("If enabled, bound machines will be usable only by their owner").define("lockBoundMachines", false);
            rightClickHarvest = pBuilder.comment("If right-click harvesting should be enabled.").define("rightClickHarvest", true);
            pBuilder.pop();
        }
    }

    public static class Specializations {
        public final IntValue combatCost;
        public final DoubleValue combatBonus;
        public final IntValue craftingCost;
        public final DoubleValue craftingBonus;
        public final ConfigValue<String> craftingBlacklist;
        public final IntValue miningCost;
        public final DoubleValue miningBonus;

        Specializations(ForgeConfigSpec.Builder pBuilder) {
            pBuilder.comment("Combat Specialization", "Protector of the realm", "Earn bonus XP on kill").push("combat");
            combatCost = pBuilder.comment("Define how much levels the player needs to unlock the Combat specialization").defineInRange("cost", 30, 0, Integer.MAX_VALUE);
            combatBonus = pBuilder.comment("Define the maximum amount of bonus XP on kill, based on the original dropped XP").defineInRange("bonus", 1.0D, 0.0D, Double.MAX_VALUE);
            pBuilder.pop();

            pBuilder.comment("Crafting Specialization", "Craft and gather resources", "Earn bonus XP when crafting").push("crafting");
            craftingCost = pBuilder.comment("Define how much levels the player needs to unlock the Crafting specialization").defineInRange("cost", 30, 0, Integer.MAX_VALUE);
            craftingBonus = pBuilder.comment("Define the multiplier for the amount of bonus XP when crafting").defineInRange("bonusMultiplier", 1.0D, 0.0D, Double.MAX_VALUE);
            craftingBlacklist = pBuilder.comment("Define items which when crafted will grant no bonus XP").define("blacklist", "modid:example_item,modid:another_example_item");
            pBuilder.pop();

            pBuilder.comment("Mining Specialization", "Branch mining is for cowards", "Earn bonus XP for mining ores").push("mining");
            miningCost = pBuilder.comment("Define how much levels the player needs to unlock the Mining specialization").defineInRange("unlockCost", 30, 0, Integer.MAX_VALUE);
            miningBonus = pBuilder.comment("Define the amount of bonus XP dropped when mining, based on the original dropped XP").defineInRange("bonusBound", 1.0D, 0.0D, Double.MAX_VALUE);
            pBuilder.pop();
        }
    }

    public static class Skills {
        public final IntValue strengthMaxLevel;
        public final IntValue strengthCostPerLevel;
        public final DoubleValue strengthBonusPerLevel;
        public final ConfigValue<String> strengthPrerequisites;
        public final IntValue criticalStrikeMaxLevel;
        public final IntValue criticalStrikeCostPerLevel;
        public final DoubleValue criticalStrikeBonusPerLevel;
        public final ConfigValue<String> criticalStrikePrerequisites;
        public final BooleanValue disableVanillaCrits;
        public final IntValue quickDrawMaxLevel;
        public final IntValue quickDrawCostPerLevel;
        public final DoubleValue quickDrawBonusPerLevel;
        public final ConfigValue<String> quickDrawPrerequisites;
        public final IntValue arrowSpeedMaxLevel;
        public final IntValue arrowSpeedCostPerLevel;
        public final DoubleValue arrowSpeedBonusPerLevel;
        public final ConfigValue<String> arrowSpeedPrerequisites;
        public final IntValue ironSkinMaxLevel;
        public final IntValue ironSkinCostPerLevel;
        public final DoubleValue ironSkinBonusPerLevel;
        public final ConfigValue<String> ironSkinPrerequisites;
        public final IntValue sneakSpeedMaxLevel;
        public final IntValue sneakSpeedCostPerLevel;
        public final DoubleValue sneakSpeedBonusPerLevel;
        public final ConfigValue<String> sneakSpeedPrerequisites;
        public final IntValue greenThumbTickRate;
        public final IntValue greenThumbMaxLevel;
        public final IntValue greenThumbCostPerLevel;
        public final DoubleValue greenThumbBonusPerLevel;
        public final ConfigValue<String> greenThumbPrerequisites;
        public final IntValue harvestProficiencyMaxLevel;
        public final IntValue harvestProficiencyCostPerLevel;
        public final DoubleValue harvestProficiencyBonusPerLevel;
        public final DoubleValue harvestProficiencyPotentialLootBound;
        public final ConfigValue<String> harvestProficiencyPrerequisites;
        public final IntValue skinningMaxLevel;
        public final IntValue skinningCostPerLevel;
        public final DoubleValue skinningBonusPerLevel;
        public final DoubleValue skinningPotentialLootBound;
        public final ConfigValue<String> skinningPrerequisites;
        public final IntValue meatGatheringMaxLevel;
        public final IntValue meatGatheringCostPerLevel;
        public final DoubleValue meatGatheringBonusPerLevel;
        public final DoubleValue meatGatheringPotentialLootBound;
        public final ConfigValue<String> meatGatheringPrerequisites;
        public final IntValue swimSpeedMaxLevel;
        public final IntValue swimSpeedCostPerLevel;
        public final DoubleValue swimSpeedBonusPerLevel;
        public final ConfigValue<String> swimSpeedPrerequisites;
        public final IntValue cookingSpeedMaxLevel;
        public final IntValue cookingSpeedCostPerLevel;
        public final DoubleValue cookingSpeedBonusPerLevel;
        public final ConfigValue<String> cookingSpeedPrerequisites;
        public final IntValue stonecuttingMaxLevel;
        public final IntValue stonecuttingCostPerLevel;
        public final DoubleValue stonecuttingBonusPerLevel;
        public final ConfigValue<String> stonecuttingPrerequisites;
        public final IntValue prospectingMaxLevel;
        public final IntValue prospectingCostPerLevel;
        public final DoubleValue prospectingBonusPerLevel;
        public final DoubleValue prospectingPotentialLootBound;
        public final ConfigValue<String> prospectingPrerequisites;
        public final IntValue woodcuttingMaxLevel;
        public final IntValue woodcuttingCostPerLevel;
        public final DoubleValue woodcuttingBonusPerLevel;
        public final ConfigValue<String> woodcuttingPrerequisites;
        public final IntValue treasureHuntingMaxLevel;
        public final IntValue treasureHuntingCostPerLevel;
        public final DoubleValue treasureHuntingBonusPerLevel;
        public final IntValue treasureHuntingUncommonWeight;
        public final IntValue treasureHuntingCommonWeight;
        public final IntValue treasureHuntingRareWeight;
        public final ConfigValue<String> treasureHuntingPrerequisites;
        public final IntValue softLandingMaxLevel;
        public final IntValue softLandingCostPerLevel;
        public final DoubleValue softLandingBonusPerLevel;
        public final ConfigValue<String> softLandingPrerequisites;
        public final IntValue sprintSpeedMaxLevel;
        public final IntValue sprintSpeedCostPerLevel;
        public final DoubleValue sprintSpeedBonusPerLevel;
        public final ConfigValue<String> sprintSpeedPrerequisites;

        Skills(ForgeConfigSpec.Builder pBuilder) {
            pBuilder.comment("Increase melee damage").push("strength");
            strengthMaxLevel = pBuilder.defineInRange("maxLevel", 10, 1, Integer.MAX_VALUE);
            strengthCostPerLevel = pBuilder.defineInRange("costPerLevel", 40, 1, Integer.MAX_VALUE);
            strengthBonusPerLevel = pBuilder.defineInRange("bonusPerLevel", 0.075D, 0.0D, Double.MAX_VALUE);
            strengthPrerequisites = pBuilder.define("prerequisites", "");
            pBuilder.pop();

            pBuilder.comment("Increase critical chance").push("criticalstrike");
            criticalStrikeMaxLevel = pBuilder.defineInRange("maxLevel", 10, 1, Integer.MAX_VALUE);
            criticalStrikeCostPerLevel = pBuilder.defineInRange("costPerLevel", 50, 1, Integer.MAX_VALUE);
            criticalStrikeBonusPerLevel = pBuilder.defineInRange("bonusPerLevel", 0.05D, 0.0D, 1.0D);
            criticalStrikePrerequisites = pBuilder.define("prerequisites", "strength:4");
            disableVanillaCrits = pBuilder.comment("If set to true, vanilla crits will be disabled").define("disableVanillaCrits", true);
            pBuilder.pop();

            pBuilder.comment("Increase bow charge speed").push("quickdraw");
            quickDrawMaxLevel = pBuilder.defineInRange("maxLevel", 10, 1, Integer.MAX_VALUE);
            quickDrawCostPerLevel = pBuilder.defineInRange("costPerLevel", 50, 1, Integer.MAX_VALUE);
            quickDrawBonusPerLevel = pBuilder.defineInRange("bonusPerLevel", 0.05D, 0.0D, Double.MAX_VALUE);
            quickDrawPrerequisites = pBuilder.define("prerequisites", "");
            pBuilder.pop();

            pBuilder.comment("Increase arrow velocity").push("arrowspeed");
            arrowSpeedMaxLevel = pBuilder.defineInRange("maxLevel", 10, 1, Integer.MAX_VALUE);
            arrowSpeedCostPerLevel = pBuilder.defineInRange("costPerLevel", 40, 1, Integer.MAX_VALUE);
            arrowSpeedBonusPerLevel = pBuilder.defineInRange("bonusPerLevel", 0.03D, 0.0D, Double.MAX_VALUE);
            arrowSpeedPrerequisites = pBuilder.define("prerequisites", "quick_draw:4");
            pBuilder.pop();

            pBuilder.comment("Decrease damage taken").push("ironskin");
            ironSkinMaxLevel = pBuilder.defineInRange("maxLevel", 10, 1, Integer.MAX_VALUE);
            ironSkinCostPerLevel = pBuilder.defineInRange("costPerLevel", 40, 1, Integer.MAX_VALUE);
            ironSkinBonusPerLevel = pBuilder.defineInRange("bonusPerLevel", 0.035D, 0.0D, 1.0D);
            ironSkinPrerequisites = pBuilder.define("prerequisites", "strength:7");
            pBuilder.pop();

            pBuilder.comment("Increase speed while sneaking").push("sneakspeed");
            sneakSpeedMaxLevel = pBuilder.defineInRange("maxLevel", 10, 1, Integer.MAX_VALUE);
            sneakSpeedCostPerLevel = pBuilder.defineInRange("costPerLevel", 40, 1, Integer.MAX_VALUE);
            sneakSpeedBonusPerLevel = pBuilder.defineInRange("bonusPerLevel", 0.075D, 0.0D, Double.MAX_VALUE);
            sneakSpeedPrerequisites = pBuilder.define("prerequisites", "");
            pBuilder.pop();

            pBuilder.comment("Every X ticks, chance to grow crops around").push("greenthumb");
            greenThumbTickRate = pBuilder.defineInRange("greenThumbTickRate", 100, 20, Integer.MAX_VALUE);
            greenThumbMaxLevel = pBuilder.defineInRange("maxLevel", 10, 1, Integer.MAX_VALUE);
            greenThumbCostPerLevel = pBuilder.defineInRange("costPerLevel", 40, 1, Integer.MAX_VALUE);
            greenThumbBonusPerLevel = pBuilder.defineInRange("bonusPerLevel", 0.015D, 0.0D, Double.MAX_VALUE);
            greenThumbPrerequisites = pBuilder.define("prerequisites", "");
            pBuilder.pop();

            pBuilder.comment("Chance to gather extra crops on harvest").push("harvestproficiency");
            harvestProficiencyMaxLevel = pBuilder.defineInRange("maxLevel", 10, 1, Integer.MAX_VALUE);
            harvestProficiencyCostPerLevel = pBuilder.defineInRange("costPerLevel", 50, 1, Integer.MAX_VALUE);
            harvestProficiencyBonusPerLevel = pBuilder.defineInRange("bonusPerLevel", 0.075D, 0.0D, Double.MAX_VALUE);
            harvestProficiencyPotentialLootBound = pBuilder.defineInRange("potentialLootBound", 1.0D, 0.0D, Double.MAX_VALUE);
            harvestProficiencyPrerequisites = pBuilder.define("prerequisites", "green_thumb:4");
            pBuilder.pop();

            pBuilder.comment("Chance to gather extra skins from animals").push("skinning");
            skinningMaxLevel = pBuilder.defineInRange("maxLevel", 10, 1, Integer.MAX_VALUE);
            skinningCostPerLevel = pBuilder.defineInRange("costPerLevel", 40, 1, Integer.MAX_VALUE);
            skinningBonusPerLevel = pBuilder.defineInRange("bonusPerLevel", 0.075D, 0.0D, Double.MAX_VALUE);
            skinningPotentialLootBound = pBuilder.defineInRange("potentialLootBound", 1.0D, 0.0D, Double.MAX_VALUE);
            skinningPrerequisites = pBuilder.define("prerequisites", "");
            pBuilder.pop();

            pBuilder.comment("Chance to drop extra meat from animals").push("meatgathering");
            meatGatheringMaxLevel = pBuilder.defineInRange("maxLevel", 10, 1, Integer.MAX_VALUE);
            meatGatheringCostPerLevel = pBuilder.defineInRange("costPerLevel", 50, 1, Integer.MAX_VALUE);
            meatGatheringBonusPerLevel = pBuilder.defineInRange("bonusPerLevel", 0.05D, 0.0D, Double.MAX_VALUE);
            meatGatheringPotentialLootBound = pBuilder.defineInRange("potentialLootBound", 1.0D, 0.0D, Integer.MAX_VALUE);
            meatGatheringPrerequisites = pBuilder.define("prerequisites", "skinning:4");
            pBuilder.pop();

            pBuilder.comment("Increase speed under water").push("swimspeed");
            swimSpeedMaxLevel = pBuilder.defineInRange("maxLevel", 10, 1, Integer.MAX_VALUE);
            swimSpeedCostPerLevel = pBuilder.defineInRange("costPerLevel", 40, 1, Integer.MAX_VALUE);
            swimSpeedBonusPerLevel = pBuilder.defineInRange("bonusPerLevel", 0.125D, 0.0D, Double.MAX_VALUE);
            swimSpeedPrerequisites = pBuilder.define("prerequisites", "");
            pBuilder.pop();

            pBuilder.comment("Decrease total cooking time").push("cookingspeed");
            cookingSpeedMaxLevel = pBuilder.defineInRange("maxLevel", 10, 1, Integer.MAX_VALUE);
            cookingSpeedCostPerLevel = pBuilder.defineInRange("costPerLevel", 50, 1, Integer.MAX_VALUE);
            cookingSpeedBonusPerLevel = pBuilder.defineInRange("bonusPerLevel", 0.09D, 0.0D, 1.0D);
            cookingSpeedPrerequisites = pBuilder.define("prerequisites", "");
            pBuilder.pop();

            pBuilder.comment("Increase stone mining speed").push("stonecutting");
            stonecuttingMaxLevel = pBuilder.defineInRange("maxLevel", 10, 1, Integer.MAX_VALUE);
            stonecuttingCostPerLevel = pBuilder.defineInRange("costPerLevel", 40, 1, Integer.MAX_VALUE);
            stonecuttingBonusPerLevel = pBuilder.defineInRange("bonusPerLevel", 0.05D, 0.0D, Double.MAX_VALUE);
            stonecuttingPrerequisites = pBuilder.define("prerequisites", "");
            pBuilder.pop();

            pBuilder.comment("Chance to increase yield from ores").push("prospecting");
            prospectingMaxLevel = pBuilder.defineInRange("maxLevel", 10, 1, Integer.MAX_VALUE);
            prospectingCostPerLevel = pBuilder.defineInRange("costPerLevel", 50, 1, Integer.MAX_VALUE);
            prospectingBonusPerLevel = pBuilder.defineInRange("bonusPerLevel", 0.075D, 0.0D, Double.MAX_VALUE);
            prospectingPotentialLootBound = pBuilder.defineInRange("potentialLootBound", 1.0D, 0.0D, Double.MAX_VALUE);
            prospectingPrerequisites = pBuilder.define("prerequisites", "stonecutting:4");
            pBuilder.pop();

            pBuilder.comment("Increase wood chopping speed").push("woodcutting");
            woodcuttingMaxLevel = pBuilder.defineInRange("maxLevel", 10, 1, Integer.MAX_VALUE);
            woodcuttingCostPerLevel = pBuilder.defineInRange("costPerLevel", 40, 1, Integer.MAX_VALUE);
            woodcuttingBonusPerLevel = pBuilder.defineInRange("bonusPerLevel", 0.05D, 0.0D, Double.MAX_VALUE);
            woodcuttingPrerequisites = pBuilder.define("prerequisites", "");
            pBuilder.pop();

            pBuilder.comment("Random chance to find treasure while digging").push("treasurehunting");
            treasureHuntingMaxLevel = pBuilder.defineInRange("maxLevel", 10, 1, Integer.MAX_VALUE);
            treasureHuntingCostPerLevel = pBuilder.defineInRange("costPerLevel", 60, 1, Integer.MAX_VALUE);
            treasureHuntingBonusPerLevel = pBuilder.defineInRange("bonusPerLevel", 0.0125D, 0.0D, Double.MAX_VALUE);
            treasureHuntingRareWeight = pBuilder.defineInRange("rareTreasureWeight", 3, 1, Integer.MAX_VALUE);
            treasureHuntingUncommonWeight = pBuilder.defineInRange("uncommonTreasureWeight", 10, 1, Integer.MAX_VALUE);
            treasureHuntingCommonWeight = pBuilder.defineInRange("commonTreasureWeight", 87, 1, Integer.MAX_VALUE);
            treasureHuntingPrerequisites = pBuilder.define("prerequisites", "prospecting:3,stonecutting:8");
            pBuilder.pop();

            pBuilder.comment("Decrease fall damage taken").push("softlanding");
            softLandingMaxLevel = pBuilder.defineInRange("maxLevel", 10, 1, Integer.MAX_VALUE);
            softLandingCostPerLevel = pBuilder.defineInRange("costPerLevel", 20, 1, Integer.MAX_VALUE);
            softLandingBonusPerLevel = pBuilder.defineInRange("bonusPerLevel", 0.035D, 0.0D, Double.MAX_VALUE);
            softLandingPrerequisites = pBuilder.define("prerequisites", "");
            pBuilder.pop();

            pBuilder.comment("Increase movement speed while sprinting").push("sprintspeed");
            sprintSpeedMaxLevel = pBuilder.defineInRange("maxLevel", 10, 1, Integer.MAX_VALUE);
            sprintSpeedCostPerLevel = pBuilder.defineInRange("costPerLevel", 40, 1, Integer.MAX_VALUE);
            sprintSpeedBonusPerLevel = pBuilder.defineInRange("bonusPerLevel", 0.05D, 0.0D, Double.MAX_VALUE);
            sprintSpeedPrerequisites = pBuilder.define("prerequisites", "");
            pBuilder.pop();
        }
    }
}
