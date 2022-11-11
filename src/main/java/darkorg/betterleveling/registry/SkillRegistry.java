package darkorg.betterleveling.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.impl.Skill;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillRegistry {
    public static final ISkill STRENGTH = createSkill("strength", SpecRegistry.COMBAT, Items.IRON_SWORD);
    public static final ISkill CRITICAL_STRIKE = createSkill("crit_strike", SpecRegistry.COMBAT, Items.GOLDEN_SWORD, Pair.of(STRENGTH, 5));
    public static final ISkill QUICK_DRAW = createSkill("quick_draw", SpecRegistry.COMBAT, Items.BOW);
    public static final ISkill ARROW_SPEED = createSkill("arrow_speed", SpecRegistry.COMBAT, Items.TIPPED_ARROW, Pair.of(QUICK_DRAW, 5));
    public static final ISkill IRON_SKIN = createSkill("iron_skin", SpecRegistry.COMBAT, Items.IRON_CHESTPLATE);
    public static final ISkill SNEAK_SPEED = createSkill("sneak_speed", SpecRegistry.COMBAT, Items.RABBIT_FOOT);
    public static final ISkill GREEN_THUMB = createSkill("green_thumb", SpecRegistry.CRAFTING, Items.WHEAT_SEEDS);
    public static final ISkill HARVEST_PROFICIENCY = createSkill("harvest_proficiency", SpecRegistry.CRAFTING, Items.WHEAT, Pair.of(GREEN_THUMB, 5));
    public static final ISkill SKINNING = createSkill("skinning", SpecRegistry.CRAFTING, Items.LEATHER);
    public static final ISkill MEAT_GATHERING = createSkill("meat_gathering", SpecRegistry.CRAFTING, Items.PORKCHOP, Pair.of(SKINNING, 5));
    public static final ISkill SWIM_SPEED = createSkill("swim_speed", SpecRegistry.CRAFTING, Items.HEART_OF_THE_SEA);
    public static final ISkill COOKING_SPEED = createSkill("cooking_speed", SpecRegistry.CRAFTING, Items.FURNACE);
    public static final ISkill STONECUTTING = createSkill("stonecutting", SpecRegistry.MINING, Items.IRON_PICKAXE);
    public static final ISkill PROSPECTING = createSkill("prospecting", SpecRegistry.MINING, Items.GOLDEN_PICKAXE, Pair.of(STONECUTTING, 5));
    public static final ISkill TREASURE_HUNTING = createSkill("treasure_hunting", SpecRegistry.MINING, Items.GOLDEN_SHOVEL, Pair.of(PROSPECTING, 5));
    public static final ISkill WOODCUTTING = createSkill("woodcutting", SpecRegistry.MINING, Items.IRON_AXE);
    public static final ISkill SOFT_LANDING = createSkill("soft_landing", SpecRegistry.MINING, Items.FEATHER);
    public static final ISkill SPRINT_SPEED = createSkill("sprint_speed", SpecRegistry.MINING, Items.LEATHER_BOOTS);
    private static final List<ISkill> SKILL_REGISTRY = new ArrayList<>();
    private static final Map<String, ISkill> SKILL_NAME_MAP = new HashMap<>();
    private static final Map<ISpecialization, List<ISkill>> SKILL_SPEC_MAP = new HashMap<>();

    public static void init() {
        registerSkill(STRENGTH);
        registerSkill(QUICK_DRAW);
        registerSkill(IRON_SKIN);
        registerSkill(CRITICAL_STRIKE);
        registerSkill(ARROW_SPEED);
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

    @SafeVarargs
    private static Skill createSkill(String pName, ISpecialization pParentSpec, Item pRepresentativeItem, Pair<ISkill, Integer>... pPairs) {
        return new Skill(BetterLeveling.MOD_ID, pName, 0, 10, pParentSpec, pRepresentativeItem, pPairs);
    }
}
