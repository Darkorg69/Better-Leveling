package darkorg.betterleveling.util;

import darkorg.betterleveling.api.ISkill;

public class SkillUtil {
    public static boolean isMinLevel(ISkill pSkill, int pCurrentLevel) {
        return pSkill.getMinLevel() >= pCurrentLevel;
    }

    public static boolean isMaxLevel(ISkill pSkill, int pCurrentLevel) {
        return pSkill.getMaxLevel() <= pCurrentLevel;
    }

    public static int getCurrentCost(ISkill pSkill, int pCurrentLevel) {
        return pSkill.getCostPerLevel() * (pCurrentLevel + 1);
    }

    public static double getCurrentBonus(ISkill pSkill, int pCurrentLevel) {
        return pSkill.getBonusPerLevel() * pCurrentLevel;
    }

    public static double getCurrentChance(ISkill pSkill, int pCurrentLevel) {
        return Math.min(getCurrentBonus(pSkill, pCurrentLevel), 1.0D);
    }

    public static double getIncreaseModifier(ISkill pSkill, int pCurrentLevel) {
        return 1.0D + getCurrentBonus(pSkill, pCurrentLevel);
    }

    public static double getDecreaseModifier(ISkill pSkill, int pCurrentLevel) {
        return 1.0D - getCurrentBonus(pSkill, pCurrentLevel);
    }
}
