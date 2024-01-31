package darkorg.betterleveling.registry;

import com.google.common.collect.ImmutableList;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.config.ModConfig;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.impl.skill.SkillProperties;
import darkorg.betterleveling.impl.specialization.Specialization;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static net.minecraft.world.item.Items.*;

public class Skills {
    private static final DeferredRegister<Skill> SKILLS = DeferredRegister.create(new ResourceLocation(BetterLeveling.MOD_ID, "skills"), BetterLeveling.MOD_ID);

    public static final RegistryObject<Skill> STRENGTH = register("strength", () -> new Skill(SkillProperties.of(Specializations.COMBAT, IRON_SWORD, ModConfig.CLIENT.strengthRenderRow, ModConfig.CLIENT.strengthRenderRow, ModConfig.SKILLS.strengthMaxLevel, ModConfig.SKILLS.strengthCostPerLevel, ModConfig.SKILLS.strengthBonusPerLevel, ModConfig.SKILLS.strengthPrerequisites)));
    public static final RegistryObject<Skill> CRITICAL_STRIKE = register("crit_strike", () -> new Skill(SkillProperties.of(Specializations.COMBAT, GOLDEN_SWORD, ModConfig.CLIENT.criticalStrikeRenderRow, ModConfig.CLIENT.criticalStrikeRenderColumn, ModConfig.SKILLS.criticalStrikeMaxLevel, ModConfig.SKILLS.criticalStrikeCostPerLevel, ModConfig.SKILLS.criticalStrikeBonusPerLevel, ModConfig.SKILLS.criticalStrikePrerequisites)));
    public static final RegistryObject<Skill> QUICK_DRAW = register("quick_draw", () -> new Skill(SkillProperties.of(Specializations.COMBAT, BOW, ModConfig.CLIENT.quickDrawRenderRow, ModConfig.CLIENT.quickDrawRenderColumn, ModConfig.SKILLS.quickDrawMaxLevel, ModConfig.SKILLS.quickDrawCostPerLevel, ModConfig.SKILLS.quickDrawBonusPerLevel, ModConfig.SKILLS.quickDrawPrerequisites)));
    public static final RegistryObject<Skill> ARROW_SPEED = register("arrow_speed", () -> new Skill(SkillProperties.of(Specializations.COMBAT, TIPPED_ARROW, ModConfig.CLIENT.arrowSpeedRenderRow, ModConfig.CLIENT.arrowSpeedRenderColumn, ModConfig.SKILLS.arrowSpeedMaxLevel, ModConfig.SKILLS.arrowSpeedCostPerLevel, ModConfig.SKILLS.arrowSpeedBonusPerLevel, ModConfig.SKILLS.arrowSpeedPrerequisites)));
    public static final RegistryObject<Skill> IRON_SKIN = register("iron_skin", () -> new Skill(SkillProperties.of(Specializations.COMBAT, IRON_CHESTPLATE, ModConfig.CLIENT.ironSkinRenderRow, ModConfig.CLIENT.ironSkinRenderColumn, ModConfig.SKILLS.ironSkinMaxLevel, ModConfig.SKILLS.ironSkinCostPerLevel, ModConfig.SKILLS.ironSkinBonusPerLevel, ModConfig.SKILLS.ironSkinPrerequisites)));
    public static final RegistryObject<Skill> SNEAK_SPEED = register("sneak_speed", () -> new Skill(SkillProperties.of(Specializations.COMBAT, LEATHER_BOOTS, ModConfig.CLIENT.sneakSpeedRenderRow, ModConfig.CLIENT.sneakSpeedRenderColumn, ModConfig.SKILLS.sneakSpeedMaxLevel, ModConfig.SKILLS.sneakSpeedCostPerLevel, ModConfig.SKILLS.sneakSpeedBonusPerLevel, ModConfig.SKILLS.sneakSpeedPrerequisites)));

    public static final RegistryObject<Skill> GREEN_THUMB = register("green_thumb", () -> new Skill(SkillProperties.of(Specializations.CRAFTING, WHEAT_SEEDS, ModConfig.CLIENT.greenThumbRenderRow, ModConfig.CLIENT.greenThumbRenderColumn, ModConfig.SKILLS.greenThumbMaxLevel, ModConfig.SKILLS.greenThumbCostPerLevel, ModConfig.SKILLS.greenThumbBonusPerLevel, ModConfig.SKILLS.greenThumbPrerequisites)));
    public static final RegistryObject<Skill> HARVEST_PROFICIENCY = register("harvest_proficiency", () -> new Skill(SkillProperties.of(Specializations.CRAFTING, WHEAT, ModConfig.CLIENT.harvestProficiencyRenderRow, ModConfig.CLIENT.harvestProficiencyRenderColumn, ModConfig.SKILLS.harvestProficiencyMaxLevel, ModConfig.SKILLS.harvestProficiencyCostPerLevel, ModConfig.SKILLS.harvestProficiencyBonusPerLevel, ModConfig.SKILLS.harvestProficiencyPrerequisites)));
    public static final RegistryObject<Skill> SKINNING = register("skinning", () -> new Skill(SkillProperties.of(Specializations.CRAFTING, RABBIT_HIDE, ModConfig.CLIENT.skinningRenderRow, ModConfig.CLIENT.skinningRenderColumn, ModConfig.SKILLS.skinningMaxLevel, ModConfig.SKILLS.skinningCostPerLevel, ModConfig.SKILLS.skinningBonusPerLevel, ModConfig.SKILLS.skinningPrerequisites)));
    public static final RegistryObject<Skill> MEAT_GATHERING = register("meat_gathering", () -> new Skill(SkillProperties.of(Specializations.CRAFTING, PORKCHOP, ModConfig.CLIENT.meatGatheringRenderRow, ModConfig.CLIENT.meatGatheringRenderColumn, ModConfig.SKILLS.meatGatheringMaxLevel, ModConfig.SKILLS.meatGatheringCostPerLevel, ModConfig.SKILLS.meatGatheringBonusPerLevel, ModConfig.SKILLS.meatGatheringPrerequisites)));
    public static final RegistryObject<Skill> SWIM_SPEED = register("swim_speed", () -> new Skill(SkillProperties.of(Specializations.CRAFTING, HEART_OF_THE_SEA, ModConfig.CLIENT.swimSpeedRenderRow, ModConfig.CLIENT.swimSpeedRenderColumn, ModConfig.SKILLS.swimSpeedMaxLevel, ModConfig.SKILLS.swimSpeedCostPerLevel, ModConfig.SKILLS.swimSpeedBonusPerLevel, ModConfig.SKILLS.swimSpeedPrerequisites)));
    public static final RegistryObject<Skill> COOKING_SPEED = register("cooking_speed", () -> new Skill(SkillProperties.of(Specializations.CRAFTING, BLAST_FURNACE, ModConfig.CLIENT.cookingSpeedRenderRow, ModConfig.CLIENT.cookingSpeedRenderColumn, ModConfig.SKILLS.cookingSpeedMaxLevel, ModConfig.SKILLS.cookingSpeedCostPerLevel, ModConfig.SKILLS.cookingSpeedBonusPerLevel, ModConfig.SKILLS.cookingSpeedPrerequisites)));

    public static final RegistryObject<Skill> STONECUTTING = register("stonecutting", () -> new Skill(SkillProperties.of(Specializations.MINING, IRON_PICKAXE, ModConfig.CLIENT.stonecuttingRenderRow, ModConfig.CLIENT.stonecuttingRenderColumn, ModConfig.SKILLS.stonecuttingMaxLevel, ModConfig.SKILLS.stonecuttingCostPerLevel, ModConfig.SKILLS.stonecuttingBonusPerLevel, ModConfig.SKILLS.stonecuttingPrerequisites)));
    public static final RegistryObject<Skill> PROSPECTING = register("prospecting", () -> new Skill(SkillProperties.of(Specializations.MINING, GOLDEN_PICKAXE, ModConfig.CLIENT.prospectingRenderRow, ModConfig.CLIENT.prospectingRenderColumn, ModConfig.SKILLS.prospectingMaxLevel, ModConfig.SKILLS.prospectingCostPerLevel, ModConfig.SKILLS.prospectingBonusPerLevel, ModConfig.SKILLS.prospectingPrerequisites)));
    public static final RegistryObject<Skill> WOODCUTTING = register("woodcutting", () -> new Skill(SkillProperties.of(Specializations.MINING, IRON_AXE, ModConfig.CLIENT.woodcuttingRenderRow, ModConfig.CLIENT.woodcuttingRenderColumn, ModConfig.SKILLS.woodcuttingMaxLevel, ModConfig.SKILLS.woodcuttingCostPerLevel, ModConfig.SKILLS.woodcuttingBonusPerLevel, ModConfig.SKILLS.woodcuttingPrerequisites)));
    public static final RegistryObject<Skill> TREASURE_HUNTING = register("treasure_hunting", () -> new Skill(SkillProperties.of(Specializations.MINING, GOLDEN_SHOVEL, ModConfig.CLIENT.treasureHuntingRenderRow, ModConfig.CLIENT.treasureHuntingRenderColumn, ModConfig.SKILLS.treasureHuntingMaxLevel, ModConfig.SKILLS.treasureHuntingCostPerLevel, ModConfig.SKILLS.treasureHuntingBonusPerLevel, ModConfig.SKILLS.treasureHuntingPrerequisites)));
    public static final RegistryObject<Skill> SOFT_LANDING = register("soft_landing", () -> new Skill(SkillProperties.of(Specializations.MINING, FEATHER, ModConfig.CLIENT.softLandingRenderRow, ModConfig.CLIENT.softLandingRenderColumn, ModConfig.SKILLS.softLandingMaxLevel, ModConfig.SKILLS.softLandingCostPerLevel, ModConfig.SKILLS.softLandingBonusPerLevel, ModConfig.SKILLS.softLandingPrerequisites)));
    public static final RegistryObject<Skill> SPRINT_SPEED = register("sprint_speed", () -> new Skill(SkillProperties.of(Specializations.MINING, GOLDEN_BOOTS, ModConfig.CLIENT.sprintSpeedRenderRow, ModConfig.CLIENT.sprintSpeedRenderColumn, ModConfig.SKILLS.sprintSpeedMaxLevel, ModConfig.SKILLS.sprintSpeedCostPerLevel, ModConfig.SKILLS.sprintSpeedBonusPerLevel, ModConfig.SKILLS.sprintSpeedPrerequisites)));

    private static final Supplier<IForgeRegistry<Skill>> REGISTRY = Skills.SKILLS.makeRegistry(RegistryBuilder::new);

    public static void init() {
        SKILLS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static IForgeRegistry<Skill> getRegistry() {
        return REGISTRY.get();
    }

    private static <T extends Skill> RegistryObject<T> register(String pName, Supplier<T> pSkill) {
        return SKILLS.register(pName, pSkill);
    }

    public static ImmutableList<Skill> getAll() {
        return ImmutableList.copyOf(Skills.getRegistry().getValues());
    }

    public static ImmutableList<Skill> getAllFrom(String pName) {
        return getAllFrom(Specializations.getFrom(pName));
    }

    public static ImmutableList<Skill> getAllFrom(Specialization pSpecialization) {
        List<Skill> skills = new ArrayList<>();

        for (Skill skill : getAll()) {
            if (skill.getProperties().getParentSpecialization() == pSpecialization) {
                skills.add(skill);
            }
        }

        return ImmutableList.copyOf(skills);
    }

    public static Skill getFrom(String pName) {
        return getFrom(BetterLeveling.MOD_ID, pName);
    }

    public static Skill getFrom(String pModId, String pName) {
        return getFrom(new ResourceLocation(pModId, pName));
    }

    public static Skill getFrom(ResourceLocation pResourceLocation) {
        return Skills.getRegistry().getValue(pResourceLocation);
    }

    public static ImmutableList<String> getAllNames() {
        List<String> names = new ArrayList<>();

        for (Skill skill : getAll()) {
            String name = skill.getName();
            names.add(name);
        }

        return ImmutableList.copyOf(names);
    }

    public static ImmutableList<String> getAllNamesFrom(String pName) {
        return getAllNamesFrom(Specializations.getFrom(pName));
    }

    public static ImmutableList<String> getAllNamesFrom(Specialization pSpecialization) {
        List<String> names = new ArrayList<>();

        for (Skill pSkill : getAll()) {
            if (pSkill.getProperties().getParentSpecialization() == pSpecialization) {
                String name = pSkill.getName();
                names.add(name);
            }
        }

        return ImmutableList.copyOf(names);
    }
}
