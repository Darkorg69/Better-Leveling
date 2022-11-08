package darkorg.betterleveling.data;

import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.key.KeyMappings;
import darkorg.betterleveling.network.chat.ModTextComponents;
import darkorg.betterleveling.registry.SkillRegistry;
import darkorg.betterleveling.registry.SpecRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator dataGenerator, String modId, String locale) {
        super(dataGenerator, modId, locale);
    }

    @Override
    protected void addTranslations() {
        add(KeyMappings.KEY_CATEGORY_BETTERLEVELING, "Better Leveling");
        add(KeyMappings.KEY_OPEN_GUI, "Open GUI");
        add(ModTextComponents.CANNOT_DECREASE, "Cannot decrease level");
        add(ModTextComponents.CANNOT_INCREASE, "Cannot increase level");
        add(ModTextComponents.CHOOSE_CONFIRM, "Are you sure you want to select this specialization as your first?");
        add(ModTextComponents.CHOOSE_NO_XP, "Before choosing your first specialization you must first reach level:");
        add(ModTextComponents.CONFIRM_DECREASE, "Are you sure you want to increase the level of this skill?");
        add(ModTextComponents.CONFIRM_INCREASE, "Are you sure you want to decrease the level of this skill?");
        add(ModTextComponents.CONFIRM_UNLOCK, "Are you sure you want to unlock this specialization?");
        add(ModTextComponents.CURRENT_LEVEL, "Current Level:");
        add(ModTextComponents.DECREASE_BUTTON, "Decrease");
        add(ModTextComponents.GUI_CHOOSE, "Choose your specialization");
        add(ModTextComponents.INCREASE_BUTTON, "Increase");
        add(ModTextComponents.LEVEL, "Level");
        add(ModTextComponents.LEVELS, "Levels");
        add(ModTextComponents.LEVEL_COST, "Cost:");
        add(ModTextComponents.LOCKED, "Locked");
        add(ModTextComponents.MAX_LEVEL, "Max Level");
        add(ModTextComponents.NOT_ENOUGH_XP, "You don't have enough XP!");
        add(ModTextComponents.NOT_OWNED, "Machine is bound to someone else!");
        add(ModTextComponents.NO_ACCESS, "You cannot use this machine.");
        add(ModTextComponents.REGISTER, "Machine bound successfully");
        add(ModTextComponents.REQUIREMENTS, "Requirements:");
        add(ModTextComponents.SELECT_BUTTON, "Select");
        add(ModTextComponents.SPEC, "Specialization");
        add(ModTextComponents.SPEC_LOCKED, "This spec is locked");
        add(ModTextComponents.UNLOCK_COST, "Unlock cost:");
        add(ModTextComponents.UNLOCK_SPEC, "Unlock");
        add(ModTextComponents.UNREGISTER, "Machine unbound successfully");

        addTranslation(SpecRegistry.COMBAT, "Combat");
        addDescription(SpecRegistry.COMBAT, "Protector of the realm");

        addTranslation(SpecRegistry.CRAFTING, "Crafting");
        addDescription(SpecRegistry.CRAFTING, "Craft and gather resources");

        addTranslation(SpecRegistry.MINING, "Mining");
        addDescription(SpecRegistry.MINING, "Branch mining is for cowards");

        addTranslation(SkillRegistry.ARROW_SPEED, "Arrow Speed");
        addDescription(SkillRegistry.ARROW_SPEED, "Increase arrow speed");
        addDescriptionIndexOf(SkillRegistry.ARROW_SPEED, 1, "+3% speed per level");

        addTranslation(SkillRegistry.COOKING_SPEED, "Cooking Speed");
        addDescription(SkillRegistry.COOKING_SPEED, "Decrease total cook time");
        addDescriptionIndexOf(SkillRegistry.COOKING_SPEED, 1, "-9% per level");

        addTranslation(SkillRegistry.CRITICAL_STRIKE, "Critical Strike");
        addDescription(SkillRegistry.CRITICAL_STRIKE, "Increase critical chance");
        addDescriptionIndexOf(SkillRegistry.CRITICAL_STRIKE, 1, "+5% chance per level");

        addTranslation(SkillRegistry.GREEN_THUMB, "Green Thumb");
        addDescription(SkillRegistry.GREEN_THUMB, "Crops around you grow faster");
        addDescriptionIndexOf(SkillRegistry.GREEN_THUMB, 1, "+2% chance every 5 seconds");

        addTranslation(SkillRegistry.HARVEST_PROFICIENCY, "Harvest Proficiency");
        addDescription(SkillRegistry.HARVEST_PROFICIENCY, "Increase drops from crops");
        addDescriptionIndexOf(SkillRegistry.HARVEST_PROFICIENCY, 1, "Increase max drop by 1");

        addTranslation(SkillRegistry.IRON_SKIN, "Iron Skin");
        addDescription(SkillRegistry.IRON_SKIN, "Decrease damage taken");
        addDescriptionIndexOf(SkillRegistry.IRON_SKIN, 1, "-3.5% damage per level");

        addTranslation(SkillRegistry.MEAT_GATHERING, "Meat Gathering");
        addDescription(SkillRegistry.MEAT_GATHERING, "Gather more meat from animals");
        addDescriptionIndexOf(SkillRegistry.MEAT_GATHERING, 1, "Increase max drops by 1");

        addTranslation(SkillRegistry.PROSPECTING, "Prospecting");
        addDescription(SkillRegistry.PROSPECTING, "Gather ores more proficiently");
        addDescriptionIndexOf(SkillRegistry.PROSPECTING, 1, "Increase max drops by 1");

        addTranslation(SkillRegistry.QUICK_DRAW, "Quick-Draw");
        addDescription(SkillRegistry.QUICK_DRAW, "Decrease bow charge time");
        addDescriptionIndexOf(SkillRegistry.QUICK_DRAW, 1, "-1 tick per level");

        addTranslation(SkillRegistry.SKINNING, "Skinning");
        addDescription(SkillRegistry.SKINNING, "Skin animals more efficiently");
        addDescriptionIndexOf(SkillRegistry.SKINNING, 1, "Increase max drops by 1");

        addTranslation(SkillRegistry.SNEAK_SPEED, "Sneak Speed");
        addDescription(SkillRegistry.SNEAK_SPEED, "Increase speed while sneaking");
        addDescriptionIndexOf(SkillRegistry.SNEAK_SPEED, 1, "+5% speed per level");

        addTranslation(SkillRegistry.SOFT_LANDING, "Soft Landing");
        addDescription(SkillRegistry.SOFT_LANDING, "Decrease fall damage taken");
        addDescriptionIndexOf(SkillRegistry.SOFT_LANDING, 1, "-5% damage per level");

        addTranslation(SkillRegistry.SPRINT_SPEED, "Sprint Speed");
        addDescription(SkillRegistry.SPRINT_SPEED, "Increase speed while sprinting");
        addDescriptionIndexOf(SkillRegistry.SPRINT_SPEED, 1, "+5% speed per level");

        addTranslation(SkillRegistry.STONECUTTING, "Stonecutting");
        addDescription(SkillRegistry.STONECUTTING, "Increase stone mining speed");
        addDescriptionIndexOf(SkillRegistry.STONECUTTING, 1, "+10% speed per level");

        addTranslation(SkillRegistry.STRENGTH, "Strength");
        addDescription(SkillRegistry.STRENGTH, "Increase melee damage");
        addDescriptionIndexOf(SkillRegistry.STRENGTH, 1, "+10% damage per level");

        addTranslation(SkillRegistry.SWIM_SPEED, "Swim Speed");
        addDescription(SkillRegistry.SWIM_SPEED, "Increase swimming speed");
        addDescriptionIndexOf(SkillRegistry.SWIM_SPEED, 1, "+10% speed per level");

        addTranslation(SkillRegistry.TREASURE_HUNTING, "Treasure Hunting");
        addDescription(SkillRegistry.TREASURE_HUNTING, "Chance to dig treasure from dirt");
        addDescriptionIndexOf(SkillRegistry.TREASURE_HUNTING, 1, "~0.1% chance per level");

        addTranslation(SkillRegistry.WOODCUTTING, "Woodcutting");
        addDescription(SkillRegistry.WOODCUTTING, "Increase wood chopping speed");
        addDescriptionIndexOf(SkillRegistry.WOODCUTTING, 1, "+10% speed per level");
    }

    private void add(TranslationTextComponent pTranslationTextComponent, String pTranslation) {
        add(pTranslationTextComponent.getKey(), pTranslation);
    }

    private void addTranslation(ISpecialization pSpecialization, String pTranslation) {
        add(pSpecialization.getTranslation().getKey(), pTranslation);
    }

    private void addTranslation(ISkill pSkill, String pTranslation) {
        add(pSkill.getTranslation().getKey(), pTranslation);
    }

    private void addDescription(ISpecialization pSpecialization, String pTranslation) {
        add(pSpecialization.getDescription().getKey(), pTranslation);
    }

    private void addDescription(ISkill pSkill, String pTranslation) {
        add(pSkill.getDescription(), pTranslation);
    }

    private void addDescriptionIndexOf(ISkill pSkill, int pIndex, String pTranslation) {
        add(pSkill.getDescriptionIndexOf(pIndex), pTranslation);
    }
}
