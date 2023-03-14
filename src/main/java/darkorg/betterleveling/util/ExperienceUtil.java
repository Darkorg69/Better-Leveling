package darkorg.betterleveling.util;

public class ExperienceUtil {
    public static int getXpNeededToReachLevelWithProgress(int pExperienceLevel, float pExperienceProgress) {
        return getXpNeededToReachLevel(pExperienceLevel) + getXpNeededToReachProgress(pExperienceLevel, pExperienceProgress);
    }

    public static int getXpNeededToReachLevel(int pExperienceLevel) {
        int experiencePoints = 0;

        for (int i = 0; i < pExperienceLevel; i++) {
            experiencePoints += getXpNeededForNextLevel(i);
        }

        return experiencePoints;
    }

    public static int getXpNeededToReachProgress(int pExperienceLevel, float pExperienceProgress) {
        return Math.round(getXpNeededForNextLevel(pExperienceLevel) * pExperienceProgress);
    }

    public static int getXpNeededForNextLevel(int pExperienceLevel) {
        if (pExperienceLevel >= 30) {
            return 112 + (pExperienceLevel - 30) * 9;
        } else {
            return pExperienceLevel >= 15 ? 37 + (pExperienceLevel - 15) * 5 : 7 + pExperienceLevel * 2;
        }
    }

    public static float getProgressFromXp(int pAvailableExperience) {
        int experienceLevel = getLevelFromXp(pAvailableExperience);
        return (float) (pAvailableExperience - getXpNeededToReachLevel(experienceLevel)) / (float) getXpNeededForNextLevel(experienceLevel);
    }

    public static int getLevelFromXp(int pAvailableExperience) {
        int experienceLevel = 0;

        while (pAvailableExperience > 0) {
            int i = getXpNeededForNextLevel(experienceLevel);

            if (i <= pAvailableExperience) {
                pAvailableExperience -= i;
                experienceLevel++;
            } else {
                return experienceLevel;
            }
        }

        return experienceLevel;
    }
}