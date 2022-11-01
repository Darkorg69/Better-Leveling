package darkorg.betterleveling.data;

import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.api.ISpecialization;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.data.LanguageProvider;

import static darkorg.betterleveling.key.KeyMappings.KEY_CATEGORY_BETTERLEVELING;
import static darkorg.betterleveling.key.KeyMappings.KEY_OPEN_GUI;
import static darkorg.betterleveling.network.chat.ModTextComponents.*;
import static darkorg.betterleveling.registry.SkillRegistry.*;
import static darkorg.betterleveling.registry.SpecRegistry.*;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator dataGenerator, String modId, String locale) {
        super(dataGenerator, modId, locale);
    }

    @Override
    protected void addTranslations() {
        add(CANNOT_DECREASE, "Cannot decrease level");
        add(CANNOT_INCREASE, "Cannot increase level");
        add(CHOOSE_CONFIRM, "Are you sure you want to select this specialization as your first?");
        add(CHOOSE_NO_XP, "Before choosing your first specialization you must first reach level:");
        add(CONFIRM_DECREASE, "Are you sure you want to increase the level of this skill?");
        add(CONFIRM_INCREASE, "Are you sure you want to decrease the level of this skill?");
        add(CONFIRM_UNLOCK, "Are you sure you want to unlock this specialization?");
        add(CURRENT_LEVEL, "Current Level:");
        add(DECREASE_BUTTON, "Decrease");
        add(GUI_CHOOSE, "Choose your specialization");
        add(INCREASE_BUTTON, "Increase");
        add(KEY_CATEGORY_BETTERLEVELING, "Better Leveling");
        add(KEY_OPEN_GUI, "Open GUI");
        add(LEVEL, "Level");
        add(LEVELS, "Levels");
        add(LEVEL_COST, "Cost:");
        add(LOCKED, "Locked");
        add(MAX_LEVEL, "Max Level");
        add(NOT_ENOUGH_XP, "You don't have enough XP!");
        add(NOT_OWNED, "Machine is bound to someone else!");
        add(NO_ACCESS, "You cannot use this machine.");
        add(REGISTER, "Machine bound successfully");
        add(REQUIREMENTS, "Requirements:");
        add(SELECT_BUTTON, "Select");
        add(SPEC, "Specialization");
        add(SPEC_LOCKED, "This spec is locked");
        add(UNLOCK_COST, "Unlock cost:");
        add(UNLOCK_SPEC, "Unlock");
        add(UNREGISTER, "Machine unbound successfully");

        addTranslation(COMBAT, "Combat");
        addDescription(COMBAT, "Protector of the realm");

        addTranslation(CRAFTING, "Crafting");
        addDescription(CRAFTING, "Craft and gather resources");

        addTranslation(MINING, "Mining");
        addDescription(MINING, "Branch mining is for cowards");

        addTranslation(ARROW_SPEED, "Arrow Speed");
        addDescription(ARROW_SPEED, "Increase arrow speed");
        addDescriptionIndexOf(ARROW_SPEED, 1, "+3% speed per level");

        addTranslation(COOKING_SPEED, "Cooking Speed");
        addDescription(COOKING_SPEED, "Decrease total cook time");
        addDescriptionIndexOf(COOKING_SPEED, 1, "-9% per level");

        addTranslation(CRITICAL_STRIKE, "Critical Strike");
        addDescription(CRITICAL_STRIKE, "Increase critical chance");
        addDescriptionIndexOf(CRITICAL_STRIKE, 1, "+5% chance per level");

        addTranslation(GREEN_THUMB, "Green Thumb");
        addDescription(GREEN_THUMB, "Crops around you grow faster");
        addDescriptionIndexOf(GREEN_THUMB, 1, "+2% chance every 5 seconds");

        addTranslation(HARVEST_PROFICIENCY, "Harvest Proficiency");
        addDescription(HARVEST_PROFICIENCY, "Increase drops from crops");
        addDescriptionIndexOf(HARVEST_PROFICIENCY, 1, "Increase max drop by 1");

        addTranslation(IRON_SKIN, "Iron Skin");
        addDescription(IRON_SKIN, "Decrease damage taken");
        addDescriptionIndexOf(IRON_SKIN, 1, "-3.5% damage per level");

        addTranslation(MEAT_GATHERING, "Meat Gathering");
        addDescription(MEAT_GATHERING, "Gather more meat from animals");
        addDescriptionIndexOf(MEAT_GATHERING, 1, "Increase max drops by 1");

        addTranslation(PROSPECTING, "Prospecting");
        addDescription(PROSPECTING, "Gather ores more proficiently");
        addDescriptionIndexOf(PROSPECTING, 1, "Increase max drops by 1");

        addTranslation(QUICK_DRAW, "Quick-Draw");
        addDescription(QUICK_DRAW, "Decrease bow charge time");
        addDescriptionIndexOf(QUICK_DRAW, 1, "-1 tick per level");

        addTranslation(SKINNING, "Skinning");
        addDescription(SKINNING, "Skin animals more efficiently");
        addDescriptionIndexOf(SKINNING, 1, "Increase max drops by 1");

        addTranslation(SNEAK_SPEED, "Sneak Speed");
        addDescription(SNEAK_SPEED, "Increase speed while sneaking");
        addDescriptionIndexOf(SNEAK_SPEED, 1, "+5% speed per level");

        addTranslation(SOFT_LANDING, "Soft Landing");
        addDescription(SOFT_LANDING, "Decrease fall damage taken");
        addDescriptionIndexOf(SOFT_LANDING, 1, "-5% damage per level");

        addTranslation(SPRINT_SPEED, "Sprint Speed");
        addDescription(SPRINT_SPEED, "Increase speed while sprinting");
        addDescriptionIndexOf(SPRINT_SPEED, 1, "+5% speed per level");

        addTranslation(STONECUTTING, "Stonecutting");
        addDescription(STONECUTTING, "Increase stone mining speed");
        addDescriptionIndexOf(STONECUTTING, 1, "+10% speed per level");

        addTranslation(STRENGTH, "Strength");
        addDescription(STRENGTH, "Increase melee damage");
        addDescriptionIndexOf(STRENGTH, 1, "+10% damage per level");

        addTranslation(SWIM_SPEED, "Swim Speed");
        addDescription(SWIM_SPEED, "Increase swimming speed");
        addDescriptionIndexOf(SWIM_SPEED, 1, "+10% speed per level");

        addTranslation(TREASURE_HUNTING, "Treasure Hunting");
        addDescription(TREASURE_HUNTING, "Chance to dig treasure from dirt");
        addDescriptionIndexOf(TREASURE_HUNTING, 1, "~0.1% chance per level");

        addTranslation(WOODCUTTING, "Woodcutting");
        addDescription(WOODCUTTING, "Increase wood chopping speed");
        addDescriptionIndexOf(WOODCUTTING, 1, "+10% speed per level");
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
