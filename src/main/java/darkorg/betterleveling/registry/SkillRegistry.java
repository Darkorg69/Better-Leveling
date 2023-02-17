package darkorg.betterleveling.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.config.ServerConfig;
import darkorg.betterleveling.impl.Skill;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillRegistry {
    public static final ISkill STRENGTH = createSkill("strength", Items.IRON_SWORD, SpecRegistry.COMBAT, ServerConfig.STRENGTH_MAX_LEVEL, ServerConfig.STRENGTH_COST_PER_LEVEL, ServerConfig.STRENGTH_BONUS_PER_LEVEL, ServerConfig.STRENGTH_PREREQUISITES);
    public static final ISkill CRITICAL_STRIKE = createSkill("crit_strike", Items.GOLDEN_SWORD, SpecRegistry.COMBAT, ServerConfig.CRITICAL_STRIKE_MAX_LEVEL, ServerConfig.CRITICAL_STRIKE_COST_PER_LEVEL, ServerConfig.CRITICAL_STRIKE_BONUS_PER_LEVEL, ServerConfig.CRITICAL_STRIKE_PREREQUISITES);
    public static final ISkill ARROW_SPEED = createSkill("arrow_speed", Items.TIPPED_ARROW, SpecRegistry.COMBAT, ServerConfig.ARROW_SPEED_MAX_LEVEL, ServerConfig.ARROW_SPEED_COST_PER_LEVEL, ServerConfig.ARROW_SPEED_BONUS_PER_LEVEL, ServerConfig.ARROW_SPEED_PREREQUISITES);
    public static final ISkill QUICK_DRAW = createSkill("quick_draw", Items.BOW, SpecRegistry.COMBAT, ServerConfig.QUICK_DRAW_MAX_LEVEL, ServerConfig.QUICK_DRAW_COST_PER_LEVEL, ServerConfig.QUICK_DRAW_BONUS_PER_LEVEL, ServerConfig.QUICK_DRAW_PREREQUISITES);
    public static final ISkill IRON_SKIN = createSkill("iron_skin", Items.IRON_CHESTPLATE, SpecRegistry.COMBAT, ServerConfig.IRON_SKIN_MAX_LEVEL, ServerConfig.IRON_SKIN_COST_PER_LEVEL, ServerConfig.IRON_SKIN_BONUS_PER_LEVEL, ServerConfig.IRON_SKIN_PREREQUISITES);
    public static final ISkill SNEAK_SPEED = createSkill("sneak_speed", Items.RABBIT_FOOT, SpecRegistry.COMBAT, ServerConfig.SNEAK_SPEED_MAX_LEVEL, ServerConfig.SNEAK_SPEED_COST_PER_LEVEL, ServerConfig.SNEAK_SPEED_BONUS_PER_LEVEL, ServerConfig.SNEAK_SPEED_PREREQUISITES);
    public static final ISkill GREEN_THUMB = createSkill("green_thumb", Items.WHEAT_SEEDS, SpecRegistry.CRAFTING, ServerConfig.GREEN_THUMB_MAX_LEVEL, ServerConfig.GREEN_THUMB_COST_PER_LEVEL, ServerConfig.GREEN_THUMB_BONUS_PER_LEVEL, ServerConfig.GREEN_THUMB_PREREQUISITES);
    public static final ISkill HARVEST_PROFICIENCY = createSkill("harvest_proficiency", Items.WHEAT, SpecRegistry.CRAFTING, ServerConfig.HARVEST_PROFICIENCY_MAX_LEVEL, ServerConfig.HARVEST_PROFICIENCY_COST_PER_LEVEL, ServerConfig.HARVEST_PROFICIENCY_BONUS_PER_LEVEL, ServerConfig.HARVEST_PROFICIENCY_PREREQUISITES);
    public static final ISkill SKINNING = createSkill("skinning", Items.LEATHER, SpecRegistry.CRAFTING, ServerConfig.SKINNING_MAX_LEVEL, ServerConfig.SKINNING_COST_PER_LEVEL, ServerConfig.SKINNING_BONUS_PER_LEVEL, ServerConfig.SKINNING_PREREQUISITES);
    public static final ISkill MEAT_GATHERING = createSkill("meat_gathering", Items.PORKCHOP, SpecRegistry.CRAFTING, ServerConfig.MEAT_GATHERING_MAX_LEVEL, ServerConfig.MEAT_GATHERING_COST_PER_LEVEL, ServerConfig.MEAT_GATHERING_BONUS_PER_LEVEL, ServerConfig.MEAT_GATHERING_PREREQUISITES);
    public static final ISkill SWIM_SPEED = createSkill("swim_speed", Items.HEART_OF_THE_SEA, SpecRegistry.CRAFTING, ServerConfig.SWIM_SPEED_MAX_LEVEL, ServerConfig.SWIM_SPEED_COST_PER_LEVEL, ServerConfig.SWIM_SPEED_BONUS_PER_LEVEL, ServerConfig.SWIM_SPEED_PREREQUISITES);
    public static final ISkill COOKING_SPEED = createSkill("cooking_speed", Items.FURNACE, SpecRegistry.CRAFTING, ServerConfig.COOKING_SPEED_MAX_LEVEL, ServerConfig.COOKING_SPEED_COST_PER_LEVEL, ServerConfig.COOKING_SPEED_BONUS_PER_LEVEL, ServerConfig.COOKING_SPEED_PREREQUISITES);
    public static final ISkill STONECUTTING = createSkill("stonecutting", Items.IRON_PICKAXE, SpecRegistry.MINING, ServerConfig.STONECUTTING_MAX_LEVEL, ServerConfig.STONECUTTING_COST_PER_LEVEL, ServerConfig.STONECUTTING_BONUS_PER_LEVEL, ServerConfig.STONECUTTING_PREREQUISITES);
    public static final ISkill PROSPECTING = createSkill("prospecting", Items.GOLDEN_PICKAXE, SpecRegistry.MINING, ServerConfig.PROSPECTING_MAX_LEVEL, ServerConfig.PROSPECTING_COST_PER_LEVEL, ServerConfig.PROSPECTING_BONUS_PER_LEVEL, ServerConfig.PROSPECTING_PREREQUISITES);
    public static final ISkill WOODCUTTING = createSkill("woodcutting", Items.IRON_AXE, SpecRegistry.MINING, ServerConfig.WOODCUTTING_MAX_LEVEL, ServerConfig.WOODCUTTING_COST_PER_LEVEL, ServerConfig.WOODCUTTING_BONUS_PER_LEVEL, ServerConfig.WOODCUTTING_PREREQUISITES);
    public static final ISkill TREASURE_HUNTING = createSkill("treasure_hunting", Items.GOLDEN_SHOVEL, SpecRegistry.MINING, ServerConfig.TREASURE_HUNTING_MAX_LEVEL, ServerConfig.TREASURE_HUNTING_COST_PER_LEVEL, ServerConfig.TREASURE_HUNTING_BONUS_PER_LEVEL, ServerConfig.TREASURE_HUNTING_PREREQUISITES);
    public static final ISkill SOFT_LANDING = createSkill("soft_landing", Items.FEATHER, SpecRegistry.MINING, ServerConfig.SOFT_LANDING_MAX_LEVEL, ServerConfig.SOFT_LANDING_COST_PER_LEVEL, ServerConfig.SOFT_LANDING_BONUS_PER_LEVEL, ServerConfig.SOFT_LANDING_PREREQUISITES);
    public static final ISkill SPRINT_SPEED = createSkill("sprint_speed", Items.LEATHER_BOOTS, SpecRegistry.MINING, ServerConfig.SPRINT_SPEED_MAX_LEVEL, ServerConfig.SPRINT_SPEED_COST_PER_LEVEL, ServerConfig.SPRINT_SPEED_BONUS_PER_LEVEL, ServerConfig.SPRINT_SPEED_PREREQUISITES);
    private static final List<ISkill> SKILL_REGISTRY = new ArrayList<>();
    private static final Map<String, ISkill> SKILL_NAME_MAP = new HashMap<>();
    private static final Map<ISpecialization, List<ISkill>> SKILL_SPEC_MAP = new HashMap<>();

    public static void init() {
        registerSkill(STRENGTH);
        registerSkill(ARROW_SPEED);
        registerSkill(IRON_SKIN);
        registerSkill(CRITICAL_STRIKE);
        registerSkill(QUICK_DRAW);
        registerSkill(SNEAK_SPEED);
        registerSkill(GREEN_THUMB);
        registerSkill(SKINNING);
        registerSkill(SWIM_SPEED);
        registerSkill(HARVEST_PROFICIENCY);
        registerSkill(MEAT_GATHERING);
        registerSkill(COOKING_SPEED);
        registerSkill(STONECUTTING);
        registerSkill(WOODCUTTING);
        registerSkill(SOFT_LANDING);
        registerSkill(PROSPECTING);
        registerSkill(TREASURE_HUNTING);
        registerSkill(SPRINT_SPEED);
    }

    public static void registerSkill(ISkill pSkill) {
        SKILL_REGISTRY.add(pSkill);

        SKILL_NAME_MAP.put(pSkill.getName(), pSkill);

        SpecRegistry.getSpecRegistry().forEach(specialization -> {
            List<ISkill> skills = new ArrayList<>();

            SKILL_REGISTRY.forEach(skill -> {
                if (skill.getParentSpec() == specialization) {
                    skills.add(skill);
                }
            });

            SKILL_SPEC_MAP.put(specialization, skills);
        });
    }

    public static ImmutableList<ISkill> getSkillRegistry() {
        return ImmutableList.copyOf(SKILL_REGISTRY);
    }

    public static ImmutableMap<String, ISkill> getSkillNameMap() {
        return ImmutableMap.copyOf(SKILL_NAME_MAP);
    }

    public static ImmutableMap<ISpecialization, List<ISkill>> getSkillSpecMap() {
        return ImmutableMap.copyOf(SKILL_SPEC_MAP);
    }

    private static Skill createSkill(String pName, ItemLike pItemLike, ISpecialization pParentSpec, ConfigValue<Integer> pMaxLevel, ConfigValue<Integer> pCostPerLevel, ConfigValue<Double> pBonusPerLevel, ConfigValue<String> pPrerequisites) {
        return new Skill(BetterLeveling.MOD_ID, pName, pItemLike, pParentSpec, pMaxLevel, pCostPerLevel, pBonusPerLevel, pPrerequisites);
    }
}
