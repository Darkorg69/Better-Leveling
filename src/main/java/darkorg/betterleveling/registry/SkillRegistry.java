package darkorg.betterleveling.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.impl.Skill;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static darkorg.betterleveling.config.ServerConfig.*;
import static darkorg.betterleveling.registry.SpecRegistry.*;
import static net.minecraft.world.item.Items.*;

public class SkillRegistry {
    public static final ISkill STRENGTH = createSkill("strength", IRON_SWORD, COMBAT, STRENGTH_MAX_LEVEL, STRENGTH_COST_PER_LEVEL, STRENGTH_BONUS_PER_LEVEL, STRENGTH_PREREQUISITES);
    public static final ISkill CRITICAL_STRIKE = createSkill("crit_strike", GOLDEN_SWORD, COMBAT, CRITICAL_STRIKE_MAX_LEVEL, CRITICAL_STRIKE_COST_PER_LEVEL, CRITICAL_STRIKE_BONUS_PER_LEVEL, CRITICAL_STRIKE_PREREQUISITES);
    public static final ISkill ARROW_SPEED = createSkill("arrow_speed", TIPPED_ARROW, COMBAT, ARROW_SPEED_MAX_LEVEL, ARROW_SPEED_COST_PER_LEVEL, ARROW_SPEED_BONUS_PER_LEVEL, ARROW_SPEED_PREREQUISITES);
    public static final ISkill QUICK_DRAW = createSkill("quick_draw", BOW, COMBAT, QUICK_DRAW_MAX_LEVEL, QUICK_DRAW_COST_PER_LEVEL, QUICK_DRAW_BONUS_PER_LEVEL, QUICK_DRAW_PREREQUISITES);
    public static final ISkill IRON_SKIN = createSkill("iron_skin", IRON_CHESTPLATE, COMBAT, IRON_SKIN_MAX_LEVEL, IRON_SKIN_COST_PER_LEVEL, IRON_SKIN_BONUS_PER_LEVEL, IRON_SKIN_PREREQUISITES);
    public static final ISkill SNEAK_SPEED = createSkill("sneak_speed", RABBIT_FOOT, COMBAT, SNEAK_SPEED_MAX_LEVEL, SNEAK_SPEED_COST_PER_LEVEL, SNEAK_SPEED_BONUS_PER_LEVEL, SNEAK_SPEED_PREREQUISITES);
    public static final ISkill GREEN_THUMB = createSkill("green_thumb", WHEAT_SEEDS, CRAFTING, GREEN_THUMB_MAX_LEVEL, GREEN_THUMB_COST_PER_LEVEL, GREEN_THUMB_BONUS_PER_LEVEL, GREEN_THUMB_PREREQUISITES);
    public static final ISkill HARVEST_PROFICIENCY = createSkill("harvest_proficiency", WHEAT, CRAFTING, HARVEST_PROFICIENCY_MAX_LEVEL, HARVEST_PROFICIENCY_COST_PER_LEVEL, HARVEST_PROFICIENCY_BONUS_PER_LEVEL, HARVEST_PROFICIENCY_PREREQUISITES);
    public static final ISkill SKINNING = createSkill("skinning", LEATHER, CRAFTING, SKINNING_MAX_LEVEL, SKINNING_COST_PER_LEVEL, SKINNING_BONUS_PER_LEVEL, SKINNING_PREREQUISITES);
    public static final ISkill MEAT_GATHERING = createSkill("meat_gathering", PORKCHOP, CRAFTING, MEAT_GATHERING_MAX_LEVEL, MEAT_GATHERING_COST_PER_LEVEL, MEAT_GATHERING_BONUS_PER_LEVEL, MEAT_GATHERING_PREREQUISITES);
    public static final ISkill SWIM_SPEED = createSkill("swim_speed", HEART_OF_THE_SEA, CRAFTING, SWIM_SPEED_MAX_LEVEL, SWIM_SPEED_COST_PER_LEVEL, SWIM_SPEED_BONUS_PER_LEVEL, SWIM_SPEED_PREREQUISITES);
    public static final ISkill COOKING_SPEED = createSkill("cooking_speed", FURNACE, CRAFTING, COOKING_SPEED_MAX_LEVEL, COOKING_SPEED_COST_PER_LEVEL, COOKING_SPEED_BONUS_PER_LEVEL, COOKING_SPEED_PREREQUISITES);
    public static final ISkill STONECUTTING = createSkill("stonecutting", IRON_PICKAXE, MINING, STONECUTTING_MAX_LEVEL, STONECUTTING_COST_PER_LEVEL, STONECUTTING_BONUS_PER_LEVEL, STONECUTTING_PREREQUISITES);
    public static final ISkill PROSPECTING = createSkill("prospecting", GOLDEN_PICKAXE, MINING, PROSPECTING_MAX_LEVEL, PROSPECTING_COST_PER_LEVEL, PROSPECTING_BONUS_PER_LEVEL, PROSPECTING_PREREQUISITES);
    public static final ISkill WOODCUTTING = createSkill("woodcutting", IRON_AXE, MINING, WOODCUTTING_MAX_LEVEL, WOODCUTTING_COST_PER_LEVEL, WOODCUTTING_BONUS_PER_LEVEL, WOODCUTTING_PREREQUISITES);
    public static final ISkill TREASURE_HUNTING = createSkill("treasure_hunting", GOLDEN_SHOVEL, MINING, TREASURE_HUNTING_MAX_LEVEL, TREASURE_HUNTING_COST_PER_LEVEL, TREASURE_HUNTING_BONUS_PER_LEVEL, TREASURE_HUNTING_PREREQUISITES);
    public static final ISkill SOFT_LANDING = createSkill("soft_landing", FEATHER, MINING, SOFT_LANDING_MAX_LEVEL, SOFT_LANDING_COST_PER_LEVEL, SOFT_LANDING_BONUS_PER_LEVEL, SOFT_LANDING_PREREQUISITES);
    public static final ISkill SPRINT_SPEED = createSkill("sprint_speed", LEATHER_BOOTS, MINING, SPRINT_SPEED_MAX_LEVEL, SPRINT_SPEED_COST_PER_LEVEL, SPRINT_SPEED_BONUS_PER_LEVEL, SPRINT_SPEED_PREREQUISITES);

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

        getSpecRegistry().forEach(specialization -> {
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
