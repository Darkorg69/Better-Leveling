package darkorg.betterleveling.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.impl.Skill;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static darkorg.betterleveling.registry.SpecRegistry.*;
import static net.minecraft.item.Items.*;

public class SkillRegistry {
    private static final List<ISkill> SKILL_REGISTRY = new ArrayList<>();
    private static final Map<String, ISkill> SKILL_NAME_MAP = new HashMap<>();
    private static final Map<ISpecialization, List<ISkill>> SKILL_SPEC_MAP = new HashMap<>();

    //COMBAT
    public static ISkill STRENGTH = createSkill("strength", COMBAT, IRON_SWORD);
    public static ISkill CRITICAL_STRIKE = createSkill("crit_strike", COMBAT, GOLDEN_SWORD, Pair.of(STRENGTH, 5));
    public static ISkill QUICK_DRAW = createSkill("quick_draw", COMBAT, BOW);
    public static ISkill ARROW_SPEED = createSkill("arrow_speed", COMBAT, TIPPED_ARROW, Pair.of(QUICK_DRAW, 5));
    public static ISkill IRON_SKIN = createSkill("iron_skin", COMBAT, IRON_CHESTPLATE);
    public static ISkill SNEAK_SPEED = createSkill("sneak_speed", COMBAT, RABBIT_FOOT);

    //CRAFTING - Gather and provide resources
    public static ISkill GREEN_THUMB = createSkill("green_thumb", CRAFTING, WHEAT_SEEDS);
    public static ISkill HARVEST_PROFICIENCY = createSkill("harvest_proficiency", CRAFTING, WHEAT, Pair.of(GREEN_THUMB, 5));
    public static ISkill SKINNING = createSkill("skinning", CRAFTING, LEATHER);
    public static ISkill MEAT_GATHERING = createSkill("meat_gathering", CRAFTING, PORKCHOP, Pair.of(SKINNING, 5));
    public static ISkill SWIM_SPEED = createSkill("swim_speed", CRAFTING, HEART_OF_THE_SEA);
    public static ISkill COOKING_SPEED = createSkill("cooking_speed", CRAFTING, FURNACE);

    //MINING - Mine and smelt the world
    public static ISkill STONECUTTING = createSkill("stonecutting", MINING, IRON_PICKAXE);
    public static ISkill PROSPECTING = createSkill("prospecting", MINING, GOLDEN_PICKAXE, Pair.of(STONECUTTING, 5));
    public static ISkill WOODCUTTING = createSkill("woodcutting", MINING, IRON_AXE);
    public static ISkill TREASURE_HUNTING = createSkill("treasure_hunting", MINING, GOLDEN_SHOVEL, Pair.of(PROSPECTING, 5));
    public static ISkill SOFT_LANDING = createSkill("soft_landing", MINING, FEATHER);
    public static ISkill SPRINT_SPEED = createSkill("sprint_speed", MINING, LEATHER_BOOTS);


    public static void init() {
        registerSkill(STRENGTH);
        registerSkill(QUICK_DRAW);
        registerSkill(SNEAK_SPEED);
        registerSkill(CRITICAL_STRIKE);
        registerSkill(ARROW_SPEED);
        registerSkill(IRON_SKIN);

        registerSkill(GREEN_THUMB);
        registerSkill(SKINNING);
        registerSkill(SWIM_SPEED);
        registerSkill(HARVEST_PROFICIENCY);
        registerSkill(MEAT_GATHERING);
        registerSkill(COOKING_SPEED);

        registerSkill(STONECUTTING);
        registerSkill(WOODCUTTING);
        registerSkill(SPRINT_SPEED);
        registerSkill(PROSPECTING);
        registerSkill(TREASURE_HUNTING);
        registerSkill(SOFT_LANDING);
    }

    public static void registerSkill(ISkill pPlayerSkill) {
        SKILL_REGISTRY.add(pPlayerSkill);

        SKILL_NAME_MAP.put(pPlayerSkill.getName(), pPlayerSkill);

        SpecRegistry.getSpecRegistry().forEach(specialization -> {
            List<ISkill> skills = new ArrayList<>();

            SKILL_REGISTRY.forEach(playerSkill -> {
                if (playerSkill.getParentSpec() == specialization) {
                    skills.add(playerSkill);
                }
            });
            SKILL_SPEC_MAP.put(specialization, skills);
        });
    }

    public static ImmutableList<ISkill> getSkillRegistry() {
        return ImmutableList.copyOf(SKILL_REGISTRY);
    }

    public static ImmutableMap<String, ISkill> getNameSkillMap() {
        return ImmutableMap.copyOf(SKILL_NAME_MAP);
    }

    public static ImmutableMap<ISpecialization, List<ISkill>> getSpecSkillMap() {
        return ImmutableMap.copyOf(SKILL_SPEC_MAP);
    }

    @SafeVarargs
    private static Skill createSkill(String pName, ISpecialization pParentSpec, Item pRepresentativeItem, Pair<ISkill, Integer>... pPairs) {
        return new Skill(BetterLeveling.MOD_ID, pName, 0, 10, pParentSpec, pRepresentativeItem, pPairs);
    }
}
