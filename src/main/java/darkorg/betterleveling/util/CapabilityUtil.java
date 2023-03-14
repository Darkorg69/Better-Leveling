package darkorg.betterleveling.util;

import darkorg.betterleveling.impl.PlayerCapability;
import darkorg.betterleveling.registry.Skills;
import darkorg.betterleveling.registry.Specializations;
import net.minecraft.entity.player.ServerPlayerEntity;

public class CapabilityUtil {
    public static void reset(PlayerCapability pCapability, ServerPlayerEntity pServerPlayer) {
        Specializations.getAll().forEach(pSpecialization -> pCapability.setUnlocked(pServerPlayer, pSpecialization, false));
        Skills.getAll().forEach(pSkill -> pCapability.setLevel(pServerPlayer, pSkill, pSkill.getProperties().getMinLevel()));
        pCapability.setSpecialization(pServerPlayer, null);
    }

    public static void max(PlayerCapability pCapability, ServerPlayerEntity pServerPlayer) {
        Specializations.getAll().forEach(pSpecialization -> pCapability.setUnlocked(pServerPlayer, pSpecialization, true));
        Skills.getAll().forEach(pSkill -> pCapability.setLevel(pServerPlayer, pSkill, pSkill.getProperties().getMaxLevel()));
    }
}
