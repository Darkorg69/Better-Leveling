package darkorg.betterleveling.api.skill;

import darkorg.betterleveling.config.ModConfig;

public interface ISkill {
    ISkillProperties getProperties();

    default boolean isMinLevel(int pCurrentLevel) {
        return pCurrentLevel <= this.getProperties().getMinLevel();
    }

    default boolean isMaxLevel(int pCurrentLevel) {
        return pCurrentLevel >= this.getProperties().getMaxLevel();
    }

    default int getCurrentCost(int pCurrentLevel) {
        return this.getProperties().getCostPerLevel() * (pCurrentLevel + 1);
    }

    default int getCurrentRefund(int pCurrentLevel) {
        return Math.toIntExact(Math.round(this.getCurrentCost(pCurrentLevel - 1) * ModConfig.GAMEPLAY.xpRefundFactor.get()));
    }

    default double getCurrentBonus(int pCurrentLevel) {
        return this.getProperties().getBonusPerLevel() * pCurrentLevel;
    }
}
