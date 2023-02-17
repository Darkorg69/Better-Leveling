package darkorg.betterleveling.data.client;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.key.KeyMappings;
import darkorg.betterleveling.network.chat.ModTranslatableContents;
import darkorg.betterleveling.registry.ModItems;
import darkorg.betterleveling.registry.SkillRegistry;
import darkorg.betterleveling.registry.SpecRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput pOutput, String pLocale) {
        super(pOutput, BetterLeveling.MOD_ID, pLocale);
    }

    @Override
    protected void addTranslations() {
        add(KeyMappings.KEY_CATEGORY_BETTERLEVELING, "Better Leveling");
        add(KeyMappings.KEY_OPEN_GUI, "Open GUI");

        add(ModItems.RAW_DEBRIS.get(), "Raw Debris");

        add(ModTranslatableContents.ADDITIONAL_INFORMATION, "Additional information:");
        add(ModTranslatableContents.AVAILABLE, "Available: ");
        add(ModTranslatableContents.BONUS, "Bonus: ");
        add(ModTranslatableContents.BULLET, "• ");
        add(ModTranslatableContents.CANNOT_DECREASE, "Cannot decrease level");
        add(ModTranslatableContents.CANNOT_INCREASE, "Cannot increase level");
        add(ModTranslatableContents.CAPABILITY_NOT_FOUND, "Command exception: Capability not found");
        add(ModTranslatableContents.CHOOSE_CONFIRM, "Are you sure you want to choose this specialization as your first?");
        add(ModTranslatableContents.CHOOSE_NO_XP, "Before choosing your first specialization you must first reach level: ");
        add(ModTranslatableContents.CHOOSE_SPEC_TITLE, "Choose your specialization");
        add(ModTranslatableContents.CONFIRM_DECREASE, "Are you sure you want to decrease the level of this skill?");
        add(ModTranslatableContents.CONFIRM_INCREASE, "Are you sure you want to increase the level of this skill?");
        add(ModTranslatableContents.CONFIRM_UNLOCK, "Are you sure you want to unlock this specialization?");
        add(ModTranslatableContents.COST, "Cost: ");
        add(ModTranslatableContents.CURRENT, "Current ");
        add(ModTranslatableContents.DECREASE, "Decrease");
        add(ModTranslatableContents.HOLD_SHIFT, "Hold SHIFT for additional information");
        add(ModTranslatableContents.INCREASE, "Increase");
        add(ModTranslatableContents.LEVEL, "Level: ");
        add(ModTranslatableContents.LEVELS, " Levels");
        add(ModTranslatableContents.LOCKED, "Locked");
        add(ModTranslatableContents.MAX_LEVEL, "Max Level");
        add(ModTranslatableContents.NOT_ENOUGH_XP, "You don't have enough XP!");
        add(ModTranslatableContents.NOT_OWNED, "Machine is bound to someone else!");
        add(ModTranslatableContents.NO_ACCESS, "You cannot use this machine.");
        add(ModTranslatableContents.PER_LEVEL, " per level");
        add(ModTranslatableContents.PREREQUISITES, "Prerequisites:");
        add(ModTranslatableContents.REGISTER, "Machine bound successfully");
        add(ModTranslatableContents.SELECT_BUTTON, "Select");
        add(ModTranslatableContents.SKILL_NOT_FOUND, "Command exception: Skill not found");
        add(ModTranslatableContents.SPEC, " Specialization");
        add(ModTranslatableContents.SPEC_LOCKED, "This spec is locked");
        add(ModTranslatableContents.SPEC_NOT_FOUND, "Command exception: Specialization not found");
        add(ModTranslatableContents.UNLOCK_COST, "Unlock cost: ");
        add(ModTranslatableContents.UNLOCK_SPEC, "Unlock");
        add(ModTranslatableContents.UNREGISTER, "Machine unbound successfully");
        add(ModTranslatableContents.XP, "XP");

        addTranslation(SpecRegistry.COMBAT, "Combat");
        addDescription(SpecRegistry.COMBAT, "Protector of the realm");

        addTranslation(SkillRegistry.STRENGTH, "Strength");
        addDescription(SkillRegistry.STRENGTH, "Increase damage output");
        addDescriptionIndexOf(SkillRegistry.STRENGTH, "+", 1);
        addDescriptionIndexOf(SkillRegistry.STRENGTH, "% ", 2);
        addDescriptionIndexOf(SkillRegistry.STRENGTH, "damage", 3);

        addTranslation(SkillRegistry.CRITICAL_STRIKE, "Critical Strike");
        addDescription(SkillRegistry.CRITICAL_STRIKE, "Increase critical chance");
        addDescriptionIndexOf(SkillRegistry.CRITICAL_STRIKE, "+", 1);
        addDescriptionIndexOf(SkillRegistry.CRITICAL_STRIKE, "% ", 2);
        addDescriptionIndexOf(SkillRegistry.CRITICAL_STRIKE, "chance", 3);

        addTranslation(SkillRegistry.QUICK_DRAW, "Quick-Draw");
        addDescription(SkillRegistry.QUICK_DRAW, "Decrease total bow charge time");
        addDescriptionIndexOf(SkillRegistry.QUICK_DRAW, "-", 1);
        addDescriptionIndexOf(SkillRegistry.QUICK_DRAW, "% ", 2);
        addDescriptionIndexOf(SkillRegistry.QUICK_DRAW, "total time", 3);

        addTranslation(SkillRegistry.ARROW_SPEED, "Arrow Speed");
        addDescription(SkillRegistry.ARROW_SPEED, "Increase arrow speed");
        addDescriptionIndexOf(SkillRegistry.ARROW_SPEED, "+", 1);
        addDescriptionIndexOf(SkillRegistry.ARROW_SPEED, "% ", 2);
        addDescriptionIndexOf(SkillRegistry.ARROW_SPEED, "velocity", 3);

        addTranslation(SkillRegistry.IRON_SKIN, "Iron Skin");
        addDescription(SkillRegistry.IRON_SKIN, "Reduce damage taken");
        addDescriptionIndexOf(SkillRegistry.IRON_SKIN, "-", 1);
        addDescriptionIndexOf(SkillRegistry.IRON_SKIN, "% ", 2);
        addDescriptionIndexOf(SkillRegistry.IRON_SKIN, "damage", 3);

        addTranslation(SkillRegistry.SNEAK_SPEED, "Sneak Speed");
        addDescription(SkillRegistry.SNEAK_SPEED, "Increase speed while sneaking");
        addDescriptionIndexOf(SkillRegistry.SNEAK_SPEED, "+", 1);
        addDescriptionIndexOf(SkillRegistry.SNEAK_SPEED, "% ", 2);
        addDescriptionIndexOf(SkillRegistry.SNEAK_SPEED, "speed", 3);

        addTranslation(SpecRegistry.CRAFTING, "Crafting");
        addDescription(SpecRegistry.CRAFTING, "Craft and gather resources");

        addTranslation(SkillRegistry.GREEN_THUMB, "Green Thumb");
        addDescription(SkillRegistry.GREEN_THUMB, "Chance to tick crops around you");
        addDescriptionIndexOf(SkillRegistry.GREEN_THUMB, "+", 1);
        addDescriptionIndexOf(SkillRegistry.GREEN_THUMB, "% ", 2);
        addDescriptionIndexOf(SkillRegistry.GREEN_THUMB, "chance", 3);

        addTranslation(SkillRegistry.HARVEST_PROFICIENCY, "Harvest Proficiency");
        addDescription(SkillRegistry.HARVEST_PROFICIENCY, "Chance to increase crops drops");
        addDescriptionIndexOf(SkillRegistry.HARVEST_PROFICIENCY, "+", 1);
        addDescriptionIndexOf(SkillRegistry.HARVEST_PROFICIENCY, "% ", 2);
        addDescriptionIndexOf(SkillRegistry.HARVEST_PROFICIENCY, "chance", 3);

        addTranslation(SkillRegistry.SKINNING, "Skinning");
        addDescription(SkillRegistry.SKINNING, "Chance to increase skin drops");
        addDescriptionIndexOf(SkillRegistry.SKINNING, "+", 1);
        addDescriptionIndexOf(SkillRegistry.SKINNING, "% ", 2);
        addDescriptionIndexOf(SkillRegistry.SKINNING, "chance", 3);

        addTranslation(SkillRegistry.MEAT_GATHERING, "Meat Gathering");
        addDescription(SkillRegistry.MEAT_GATHERING, "Chance to increase meat drops");
        addDescriptionIndexOf(SkillRegistry.MEAT_GATHERING, "+", 1);
        addDescriptionIndexOf(SkillRegistry.MEAT_GATHERING, "% ", 2);
        addDescriptionIndexOf(SkillRegistry.MEAT_GATHERING, "chance", 3);

        addTranslation(SkillRegistry.SWIM_SPEED, "Swim Speed");
        addDescription(SkillRegistry.SWIM_SPEED, "Increase swimming speed");
        addDescriptionIndexOf(SkillRegistry.SWIM_SPEED, "+", 1);
        addDescriptionIndexOf(SkillRegistry.SWIM_SPEED, "% ", 2);
        addDescriptionIndexOf(SkillRegistry.SWIM_SPEED, "speed", 3);

        addTranslation(SkillRegistry.COOKING_SPEED, "Cooking Speed");
        addDescription(SkillRegistry.COOKING_SPEED, "Decrease total cooking time");
        addDescriptionIndexOf(SkillRegistry.COOKING_SPEED, "-", 1);
        addDescriptionIndexOf(SkillRegistry.COOKING_SPEED, "% ", 2);
        addDescriptionIndexOf(SkillRegistry.COOKING_SPEED, "total time", 3);

        addTranslation(SpecRegistry.MINING, "Mining");
        addDescription(SpecRegistry.MINING, "Branch mining is for cowards");

        addTranslation(SkillRegistry.WOODCUTTING, "Woodcutting");
        addDescription(SkillRegistry.WOODCUTTING, "Increase wood chopping speed");
        addDescriptionIndexOf(SkillRegistry.WOODCUTTING, "+", 1);
        addDescriptionIndexOf(SkillRegistry.WOODCUTTING, "% ", 2);
        addDescriptionIndexOf(SkillRegistry.WOODCUTTING, "speed", 3);

        addTranslation(SkillRegistry.STONECUTTING, "Stonecutting");
        addDescription(SkillRegistry.STONECUTTING, "Increase stone mining speed");
        addDescriptionIndexOf(SkillRegistry.STONECUTTING, "+", 1);
        addDescriptionIndexOf(SkillRegistry.STONECUTTING, "% ", 2);
        addDescriptionIndexOf(SkillRegistry.STONECUTTING, "speed", 3);

        addTranslation(SkillRegistry.PROSPECTING, "Prospecting");
        addDescription(SkillRegistry.PROSPECTING, "Chance to increase ore drops");
        addDescriptionIndexOf(SkillRegistry.PROSPECTING, "+", 1);
        addDescriptionIndexOf(SkillRegistry.PROSPECTING, "% ", 2);
        addDescriptionIndexOf(SkillRegistry.PROSPECTING, "chance", 3);

        addTranslation(SkillRegistry.TREASURE_HUNTING, "Treasure Hunting");
        addDescription(SkillRegistry.TREASURE_HUNTING, "Chance to dig treasure from dirt");
        addDescriptionIndexOf(SkillRegistry.TREASURE_HUNTING, "+", 1);
        addDescriptionIndexOf(SkillRegistry.TREASURE_HUNTING, "% ", 2);
        addDescriptionIndexOf(SkillRegistry.TREASURE_HUNTING, "chance", 3);

        addTranslation(SkillRegistry.SOFT_LANDING, "Soft Landing");
        addDescription(SkillRegistry.SOFT_LANDING, "Decrease fall damage taken");
        addDescriptionIndexOf(SkillRegistry.SOFT_LANDING, "-", 1);
        addDescriptionIndexOf(SkillRegistry.SOFT_LANDING, "% ", 2);
        addDescriptionIndexOf(SkillRegistry.SOFT_LANDING, "damage", 3);

        addTranslation(SkillRegistry.SPRINT_SPEED, "Sprint Speed");
        addDescription(SkillRegistry.SPRINT_SPEED, "Increase speed while sprinting");
        addDescriptionIndexOf(SkillRegistry.SPRINT_SPEED, "+", 1);
        addDescriptionIndexOf(SkillRegistry.SPRINT_SPEED, "% ", 2);
        addDescriptionIndexOf(SkillRegistry.SPRINT_SPEED, "speed", 3);
    }

    private void addTranslation(ISkill pSkill, String pTranslation) {
        add(pSkill.getTranslationKey(), pTranslation);
    }

    private void addDescription(ISkill pSkill, String pTranslation) {
        add(pSkill.getDescriptionKey(), pTranslation);
    }

    private void addDescriptionIndexOf(ISkill pSkill, String pTranslation, int pIndex) {
        add(pSkill.getDescriptionIndexOfKey(pIndex), pTranslation);
    }

    private void addTranslation(ISpecialization pSpecialization, String pTranslation) {
        add(pSpecialization.getTranslationKey(), pTranslation);
    }

    private void addDescription(ISpecialization pSpecialization, String pTranslation) {
        add(pSpecialization.getDescriptionKey(), pTranslation);
    }

    private void add(TranslatableContents pTranslatableContents, String pTranslation) {
        add(pTranslatableContents.getKey(), pTranslation);
    }
}
