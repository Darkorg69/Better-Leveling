package darkorg.betterleveling.util;

import darkorg.betterleveling.impl.PlayerCapability;
import net.minecraft.client.player.LocalPlayer;

public class LocalPlayerUtil {
    public static void updateExperienceOverlay(PlayerCapability pCapability, LocalPlayer pLocalPlayer) {
        int availableExperience = pCapability.getAvailableExperience(pLocalPlayer);
        float experienceProgress = ExperienceUtil.getProgressFromXp(availableExperience);
        int experienceLevel = ExperienceUtil.getLevelFromXp(availableExperience);
        pLocalPlayer.setExperienceValues(experienceProgress, pLocalPlayer.totalExperience, experienceLevel);
    }
}

