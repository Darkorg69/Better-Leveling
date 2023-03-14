package darkorg.betterleveling.util;

import darkorg.betterleveling.impl.PlayerCapability;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.impl.skill.SkillProperties;
import darkorg.betterleveling.network.chat.ModComponents;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SkillUtil {
    public static boolean hasUnlocked(PlayerCapability pCapability, PlayerEntity pPlayer, Skill pSkill) {
        boolean unlocked = false;

        SkillProperties properties = pSkill.getProperties();

        if (pCapability.getUnlocked(pPlayer, properties.getParentSpecialization())) {
            unlocked = true;

            for (Map.Entry<Skill, Integer> entry : properties.getPrerequisites().entrySet()) {
                if (pCapability.getLevel(pPlayer, entry.getKey()) < entry.getValue()) {
                    unlocked = false;
                }
            }
        }

        return unlocked;
    }

    public static List<ITextComponent> getTooltip(Skill pSkill, boolean pHasUnlocked, int pCurrentLevel, boolean pCanIncrease) {
        List<ITextComponent> tooltip = new ArrayList<>();

        IFormattableTextComponent translation = pSkill.getTranslation();
        IFormattableTextComponent description = pSkill.getDescription();
        IFormattableTextComponent costPerLevel = pSkill.getCostPerLevel();
        IFormattableTextComponent bonusPerLevel = pSkill.getBonusPerLevel();

        IFormattableTextComponent currentCost = RenderUtil.getCurrentCost(pSkill, pCurrentLevel);
        IFormattableTextComponent currentLevel = RenderUtil.getCurrentLevel(pSkill, pCurrentLevel);
        IFormattableTextComponent currentBonus = RenderUtil.getCurrentBonus(pSkill, pCurrentLevel);

        tooltip.add(translation);

        boolean isMaxLevel = pSkill.isMaxLevel(pCurrentLevel);

        if (pHasUnlocked) {
            tooltip.add(isMaxLevel ? ModComponents.MAX_LEVEL.withStyle(TextFormatting.RED) : currentLevel.withStyle(TextFormatting.GRAY));

            if (!isMaxLevel) {
                tooltip.add(currentCost.withStyle(pCanIncrease ? TextFormatting.GREEN : TextFormatting.RED));
            }

            tooltip.add(currentBonus.withStyle(isMaxLevel ? TextFormatting.DARK_RED : TextFormatting.BLUE));
        } else {
            tooltip.add(ModComponents.LOCKED.withStyle(TextFormatting.RED));

            Map<Skill, Integer> prerequisitesMap = pSkill.getProperties().getPrerequisites();

            if (!prerequisitesMap.isEmpty()) {
                tooltip.add(ModComponents.PREREQUISITES.withStyle(TextFormatting.DARK_RED));
                prerequisitesMap.forEach((prerequisiteSkill, prerequisiteLevel) -> {
                    tooltip.add(RenderUtil.getPrerequisite(prerequisiteSkill, prerequisiteLevel).withStyle(TextFormatting.GRAY));
                });
            }
        }

        if (Screen.hasShiftDown()) {
            tooltip.add(ModComponents.EMPTY);
            tooltip.add(ModComponents.ADDITIONAL_INFO.withStyle(TextFormatting.AQUA));
            tooltip.add(description.withStyle(TextFormatting.YELLOW));
            tooltip.add(costPerLevel.withStyle(TextFormatting.DARK_GREEN));
            tooltip.add(bonusPerLevel.withStyle(TextFormatting.DARK_BLUE));
        } else {
            tooltip.add(ModComponents.HOLD_SHIFT.withStyle(TextFormatting.AQUA));
        }

        return tooltip;
    }
}
