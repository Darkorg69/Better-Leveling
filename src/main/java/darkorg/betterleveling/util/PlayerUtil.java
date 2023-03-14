package darkorg.betterleveling.util;

import darkorg.betterleveling.config.ModConfig;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.impl.specialization.Specialization;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PlayerUtil {
    public static boolean canUnlockFirstSpecialization(Player pPlayer) {
        return pPlayer.experienceLevel >= ModConfig.GAMEPLAY.firstSpecCost.get() || pPlayer.isCreative();
    }

    public static boolean canUnlockSpecialization(Player pPlayer, Specialization pSpecialization) {
        return pPlayer.experienceLevel >= pSpecialization.getProperties().getLevelCost() || pPlayer.isCreative();
    }

    public static int getAvailableExperience(ServerPlayer pServerPlayer) {
        return ExperienceUtil.getXpNeededToReachLevelWithProgress(pServerPlayer.experienceLevel, pServerPlayer.experienceProgress);
    }


    public static boolean canIncreaseSkill(Player pPlayer, Skill pSkill, boolean isMaxLevel, int pAvailableExperience, int pCurrentLevel) {
        return !isMaxLevel && (pAvailableExperience >= pSkill.getCurrentCost(pCurrentLevel) || pPlayer.isCreative());
    }

    public static boolean isCrouchingWithEmptyHand(Player pPlayer, ItemStack pItemStack) {
        return pPlayer.isCrouching() && pItemStack.isEmpty();
    }

    public static boolean canDecreaseSkill(Skill pSkill, int pCurrentLevel) {
        return !pSkill.isMinLevel(pCurrentLevel);
    }
}
