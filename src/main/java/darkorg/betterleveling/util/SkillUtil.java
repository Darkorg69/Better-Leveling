package darkorg.betterleveling.util;

import darkorg.betterleveling.impl.PlayerCapability;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.impl.skill.SkillProperties;
import darkorg.betterleveling.network.chat.ModComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SkillUtil {
    public static boolean hasUnlocked(PlayerCapability pCapability, Player pPlayer, Skill pSkill) {
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

    public static List<Component> getTooltip(Skill pSkill, boolean pHasUnlocked, int pCurrentLevel, boolean pCanIncrease) {
        List<Component> tooltip = new ArrayList<>();

        MutableComponent translation = pSkill.getTranslation();
        MutableComponent description = pSkill.getDescription();
        MutableComponent costPerLevel = pSkill.getCostPerLevel();
        MutableComponent bonusPerLevel = pSkill.getBonusPerLevel();

        MutableComponent currentCost = RenderUtil.getCurrentCost(pSkill, pCurrentLevel);
        MutableComponent currentLevel = RenderUtil.getCurrentLevel(pSkill, pCurrentLevel);
        MutableComponent currentBonus = RenderUtil.getCurrentBonus(pSkill, pCurrentLevel);

        tooltip.add(translation);

        boolean isMaxLevel = pSkill.isMaxLevel(pCurrentLevel);

        if (pHasUnlocked) {
            tooltip.add(isMaxLevel ? ModComponents.MAX_LEVEL.withStyle(ChatFormatting.RED) : currentLevel.withStyle(ChatFormatting.GRAY));

            if (!isMaxLevel) {
                tooltip.add(currentCost.withStyle(pCanIncrease ? ChatFormatting.GREEN : ChatFormatting.RED));
            }

            tooltip.add(currentBonus.withStyle(isMaxLevel ? ChatFormatting.DARK_RED : ChatFormatting.BLUE));
        } else {
            tooltip.add(ModComponents.LOCKED.withStyle(ChatFormatting.RED));

            Map<Skill, Integer> prerequisitesMap = pSkill.getProperties().getPrerequisites();

            if (!prerequisitesMap.isEmpty()) {
                tooltip.add(ModComponents.PREREQUISITES.withStyle(ChatFormatting.DARK_RED));
                prerequisitesMap.forEach((prerequisiteSkill, prerequisiteLevel) -> tooltip.add(RenderUtil.getPrerequisite(prerequisiteSkill, prerequisiteLevel).withStyle(ChatFormatting.GRAY)));
            }
        }

        if (Screen.hasShiftDown()) {
            tooltip.add(Component.empty());
            tooltip.add(ModComponents.ADDITIONAL_INFO.withStyle(ChatFormatting.AQUA));
            tooltip.add(description.withStyle(ChatFormatting.YELLOW));
            tooltip.add(costPerLevel.withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(bonusPerLevel.withStyle(ChatFormatting.DARK_BLUE));
        } else {
            tooltip.add(ModComponents.HOLD_SHIFT.withStyle(ChatFormatting.AQUA));
        }

        return tooltip;
    }
}
