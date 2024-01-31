package darkorg.betterleveling.data.client;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.impl.specialization.Specialization;
import darkorg.betterleveling.key.KeyMappings;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.registry.ModBlocks;
import darkorg.betterleveling.registry.ModItems;
import darkorg.betterleveling.registry.Skills;
import darkorg.betterleveling.registry.Specializations;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput pPackOutput) {
        super(pPackOutput, BetterLeveling.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(KeyMappings.KEY_CATEGORY_BETTERLEVELING, "Better Leveling");
        add(KeyMappings.KEY_OPEN_GUI, "Open GUI");

        add(ModItems.RAW_DEBRIS.get(), "Raw Debris");
        add(ModBlocks.RAW_DEBRIS_BLOCK.get(), "Raw Debris Block");

        add(ModComponents.ADDITIONAL_INFO, "Additional information:");
        add(ModComponents.AVAILABLE, "Available: ");
        add(ModComponents.BONUS, "Bonus: ");
        add(ModComponents.BULLET, "• ");
        add(ModComponents.CANNOT_DECREASE, "Cannot decrease level");
        add(ModComponents.CANNOT_INCREASE, "Cannot increase level");
        add(ModComponents.CAPABILITY_NOT_FOUND, "Command exception: Capability not found");
        add(ModComponents.CHOOSE_CONFIRM, "Are you sure you want to choose this specialization as your first?");
        add(ModComponents.CANNOT_UNLOCK, "Cannot yet unlock a specialization. Required level: ");
        add(ModComponents.CHOOSE_SPEC_TITLE, "Choose your specialization");
        add(ModComponents.CONFIRM_DECREASE, "Are you sure you want to decrease the level of this skill?");
        add(ModComponents.CONFIRM_INCREASE, "Are you sure you want to increase the level of this skill?");
        add(ModComponents.CONFIRM_UNLOCK, "Are you sure you want to unlock this specialization?");
        add(ModComponents.COST, "Cost: ");
        add(ModComponents.CURRENT, "Current ");
        add(ModComponents.DECREASE, "Decrease");
        add(ModComponents.HOLD_SHIFT, "Hold SHIFT for additional information");
        add(ModComponents.INCREASE, "Increase");
        add(ModComponents.INVALID_LEVEL, "Invalid level");
        add(ModComponents.LEVEL, "Level: ");
        add(ModComponents.LEVELS, " Levels");
        add(ModComponents.LOCKED, "Locked");
        add(ModComponents.MAX_LEVEL, "Max Level");
        add(ModComponents.NOT_ENOUGH_XP, "You don't have enough XP!");
        add(ModComponents.NOT_OWNED, "Machine is bound to someone else!");
        add(ModComponents.NO_ACCESS, "You cannot use this machine.");
        add(ModComponents.PER_LEVEL, " per level");
        add(ModComponents.PREREQUISITES, "Prerequisites:");
        add(ModComponents.REGISTER, "Machine bound successfully");
        add(ModComponents.SELECT_BUTTON, "Select");
        add(ModComponents.SKILL_NOT_FOUND, "Command exception: Skill not found");
        add(ModComponents.SPEC, " Specialization");
        add(ModComponents.SPEC_IS_LOCKED, "This spec is locked");
        add(ModComponents.SPEC_NOT_FOUND, "Command exception: Specialization not found");
        add(ModComponents.UNLOCK_COST, "Unlock cost: ");
        add(ModComponents.UNLOCK, "Unlock");
        add(ModComponents.UNREGISTER, "Machine unbound successfully");
        add(ModComponents.XP, "XP");

        addTranslation(Specializations.COMBAT.get(), "Combat");
        addDescription(Specializations.COMBAT.get(), "Protector of the realm");
        addDescription(Specializations.COMBAT.get(), "Earn bonus XP on kill", 1);

        addTranslation(Skills.STRENGTH.get(), "Strength");
        addDescription(Skills.STRENGTH.get(), "Increase damage output");
        addDescription(Skills.STRENGTH.get(), "+", 1);
        addDescription(Skills.STRENGTH.get(), "% ", 2);
        addDescription(Skills.STRENGTH.get(), "damage", 3);

        addTranslation(Skills.CRITICAL_STRIKE.get(), "Critical Strike");
        addDescription(Skills.CRITICAL_STRIKE.get(), "Increase critical chance");
        addDescription(Skills.CRITICAL_STRIKE.get(), "+", 1);
        addDescription(Skills.CRITICAL_STRIKE.get(), "% ", 2);
        addDescription(Skills.CRITICAL_STRIKE.get(), "chance", 3);

        addTranslation(Skills.QUICK_DRAW.get(), "Quick-Draw");
        addDescription(Skills.QUICK_DRAW.get(), "Decrease total bow charge time");
        addDescription(Skills.QUICK_DRAW.get(), "-", 1);
        addDescription(Skills.QUICK_DRAW.get(), "% ", 2);
        addDescription(Skills.QUICK_DRAW.get(), "total time", 3);

        addTranslation(Skills.ARROW_SPEED.get(), "Arrow Speed");
        addDescription(Skills.ARROW_SPEED.get(), "Increase arrow speed");
        addDescription(Skills.ARROW_SPEED.get(), "+", 1);
        addDescription(Skills.ARROW_SPEED.get(), "% ", 2);
        addDescription(Skills.ARROW_SPEED.get(), "velocity", 3);

        addTranslation(Skills.IRON_SKIN.get(), "Iron Skin");
        addDescription(Skills.IRON_SKIN.get(), "Reduce damage taken");
        addDescription(Skills.IRON_SKIN.get(), "-", 1);
        addDescription(Skills.IRON_SKIN.get(), "% ", 2);
        addDescription(Skills.IRON_SKIN.get(), "damage", 3);

        addTranslation(Skills.SNEAK_SPEED.get(), "Sneak Speed");
        addDescription(Skills.SNEAK_SPEED.get(), "Increase speed while sneaking");
        addDescription(Skills.SNEAK_SPEED.get(), "+", 1);
        addDescription(Skills.SNEAK_SPEED.get(), "% ", 2);
        addDescription(Skills.SNEAK_SPEED.get(), "speed", 3);

        addTranslation(Specializations.CRAFTING.get(), "Crafting");
        addDescription(Specializations.CRAFTING.get(), "Craft and gather resources");
        addDescription(Specializations.CRAFTING.get(), "Earn bonus XP when crafting", 1);

        addTranslation(Skills.GREEN_THUMB.get(), "Green Thumb");
        addDescription(Skills.GREEN_THUMB.get(), "Chance to tick crops around you");
        addDescription(Skills.GREEN_THUMB.get(), "+", 1);
        addDescription(Skills.GREEN_THUMB.get(), "% ", 2);
        addDescription(Skills.GREEN_THUMB.get(), "chance", 3);

        addTranslation(Skills.HARVEST_PROFICIENCY.get(), "Harvest Proficiency");
        addDescription(Skills.HARVEST_PROFICIENCY.get(), "Chance to increase crops drops");
        addDescription(Skills.HARVEST_PROFICIENCY.get(), "+", 1);
        addDescription(Skills.HARVEST_PROFICIENCY.get(), "% ", 2);
        addDescription(Skills.HARVEST_PROFICIENCY.get(), "chance", 3);

        addTranslation(Skills.SKINNING.get(), "Skinning");
        addDescription(Skills.SKINNING.get(), "Chance to increase skin drops");
        addDescription(Skills.SKINNING.get(), "+", 1);
        addDescription(Skills.SKINNING.get(), "% ", 2);
        addDescription(Skills.SKINNING.get(), "chance", 3);

        addTranslation(Skills.MEAT_GATHERING.get(), "Meat Gathering");
        addDescription(Skills.MEAT_GATHERING.get(), "Chance to increase meat drops");
        addDescription(Skills.MEAT_GATHERING.get(), "+", 1);
        addDescription(Skills.MEAT_GATHERING.get(), "% ", 2);
        addDescription(Skills.MEAT_GATHERING.get(), "chance", 3);

        addTranslation(Skills.SWIM_SPEED.get(), "Swim Speed");
        addDescription(Skills.SWIM_SPEED.get(), "Increase swimming speed");
        addDescription(Skills.SWIM_SPEED.get(), "+", 1);
        addDescription(Skills.SWIM_SPEED.get(), "% ", 2);
        addDescription(Skills.SWIM_SPEED.get(), "speed", 3);

        addTranslation(Skills.COOKING_SPEED.get(), "Cooking Speed");
        addDescription(Skills.COOKING_SPEED.get(), "Decrease total cooking time");
        addDescription(Skills.COOKING_SPEED.get(), "-", 1);
        addDescription(Skills.COOKING_SPEED.get(), "% ", 2);
        addDescription(Skills.COOKING_SPEED.get(), "total time", 3);

        addTranslation(Specializations.MINING.get(), "Mining");
        addDescription(Specializations.MINING.get(), "Branch mining is for cowards");
        addDescription(Specializations.MINING.get(), "Earn bonus XP for mining ores", 1);

        addTranslation(Skills.WOODCUTTING.get(), "Woodcutting");
        addDescription(Skills.WOODCUTTING.get(), "Increase wood chopping speed");
        addDescription(Skills.WOODCUTTING.get(), "+", 1);
        addDescription(Skills.WOODCUTTING.get(), "% ", 2);
        addDescription(Skills.WOODCUTTING.get(), "speed", 3);

        addTranslation(Skills.STONECUTTING.get(), "Stonecutting");
        addDescription(Skills.STONECUTTING.get(), "Increase stone mining speed");
        addDescription(Skills.STONECUTTING.get(), "+", 1);
        addDescription(Skills.STONECUTTING.get(), "% ", 2);
        addDescription(Skills.STONECUTTING.get(), "speed", 3);

        addTranslation(Skills.PROSPECTING.get(), "Prospecting");
        addDescription(Skills.PROSPECTING.get(), "Chance to increase ore drops");
        addDescription(Skills.PROSPECTING.get(), "+", 1);
        addDescription(Skills.PROSPECTING.get(), "% ", 2);
        addDescription(Skills.PROSPECTING.get(), "chance", 3);

        addTranslation(Skills.TREASURE_HUNTING.get(), "Treasure Hunting");
        addDescription(Skills.TREASURE_HUNTING.get(), "Chance to dig treasure from dirt");
        addDescription(Skills.TREASURE_HUNTING.get(), "+", 1);
        addDescription(Skills.TREASURE_HUNTING.get(), "% ", 2);
        addDescription(Skills.TREASURE_HUNTING.get(), "chance", 3);

        addTranslation(Skills.SOFT_LANDING.get(), "Soft Landing");
        addDescription(Skills.SOFT_LANDING.get(), "Decrease fall damage taken");
        addDescription(Skills.SOFT_LANDING.get(), "-", 1);
        addDescription(Skills.SOFT_LANDING.get(), "% ", 2);
        addDescription(Skills.SOFT_LANDING.get(), "damage", 3);

        addTranslation(Skills.SPRINT_SPEED.get(), "Sprint Speed");
        addDescription(Skills.SPRINT_SPEED.get(), "Increase speed while sprinting");
        addDescription(Skills.SPRINT_SPEED.get(), "+", 1);
        addDescription(Skills.SPRINT_SPEED.get(), "% ", 2);
        addDescription(Skills.SPRINT_SPEED.get(), "speed", 3);
    }

    private void addTranslation(Specialization pSpecialization, String pTranslation) {
        add(pSpecialization.getTranslationId(), pTranslation);
    }

    private void addDescription(Specialization pSpecialization, String pTranslation) {
        add(pSpecialization.getDescriptionId(), pTranslation);
    }

    private void addDescription(Specialization pSpecialization, String pDescription, int pIndex) {
        add(pSpecialization.getDescriptionId(pIndex), pDescription);
    }

    private void addTranslation(Skill pSkill, String pTranslation) {
        add(pSkill.getTranslationId(), pTranslation);
    }

    private void addDescription(Skill pSkill, String pDescription) {
        add(pSkill.getDescriptionId(), pDescription);
    }

    private void addDescription(Skill pSkill, String pDescription, int pIndex) {
        add(pSkill.getDescriptionId(pIndex), pDescription);
    }

    private void add(MutableComponent pTranslatableComponent, String pTranslation) {
        add(pTranslatableComponent.getString(), pTranslation);
    }
}
