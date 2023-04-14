package darkorg.betterleveling.util;

import darkorg.betterleveling.config.ModConfig;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.impl.specialization.Specialization;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

public class PlayerUtil {
    public static boolean canUnlockFirstSpecialization(PlayerEntity pPlayer) {
        return pPlayer.experienceLevel >= ModConfig.SPECIALIZATIONS.firstSpecCost.get() || pPlayer.isCreative();
    }

    public static boolean canUnlockSpecialization(PlayerEntity pPlayer, Specialization pSpecialization) {
        return pPlayer.experienceLevel >= pSpecialization.getProperties().getLevelCost() || pPlayer.isCreative();
    }

    public static int getAvailableExperience(ServerPlayerEntity pServerPlayer) {
        return ExperienceUtil.getXpNeededToReachLevelWithProgress(pServerPlayer.experienceLevel, pServerPlayer.experienceProgress);
    }


    public static boolean canIncreaseSkill(PlayerEntity pPlayer, Skill pSkill, boolean isMaxLevel, int pAvailableExperience, int pCurrentLevel) {
        return !isMaxLevel && (pAvailableExperience >= pSkill.getCurrentCost(pCurrentLevel) || pPlayer.isCreative());
    }

    public static boolean isCrouchingWithEmptyHand(PlayerEntity pPlayer, ItemStack pItemStack) {
        return pPlayer.isCrouching() && pItemStack.isEmpty();
    }

    public static boolean canDecreaseSkill(Skill pSkill, int pCurrentLevel) {
        return !pSkill.isMinLevel(pCurrentLevel);
    }
}
