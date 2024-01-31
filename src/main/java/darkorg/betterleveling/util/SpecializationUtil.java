package darkorg.betterleveling.util;

import com.google.common.collect.ImmutableList;
import darkorg.betterleveling.impl.PlayerCapability;
import darkorg.betterleveling.impl.specialization.Specialization;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.registry.Specializations;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class SpecializationUtil {
    public static boolean hasUnlocked(PlayerCapability pCapability, Player pPlayer) {
        return !getUnlocked(pCapability, pPlayer).isEmpty();
    }

    public static ImmutableList<Specialization> getUnlocked(PlayerCapability pCapability, Player pPlayer) {
        List<Specialization> specializations = new ArrayList<>();

        for (Specialization specialization : Specializations.getAll()) {
            if (pCapability.getUnlocked(pPlayer, specialization)) {
                specializations.add(specialization);
            }
        }

        return ImmutableList.copyOf(specializations);
    }

    public static List<Component> getTooltip(Specialization pSpecialization, boolean pIsUnlocked, boolean pCanUnlock) {
        List<Component> tooltip = new ArrayList<>();

        MutableComponent translation = pSpecialization.getTranslation();
        MutableComponent description = pSpecialization.getDescription();
        MutableComponent unlockCost = pSpecialization.getUnlockCost();

        tooltip.add(translation.append(ModComponents.SPEC));

        if (!pIsUnlocked) {
            tooltip.add(ModComponents.LOCKED.withStyle(ChatFormatting.DARK_RED));
            tooltip.add(unlockCost.withStyle(pCanUnlock ? ChatFormatting.GREEN : ChatFormatting.RED));
        }

        if (Screen.hasShiftDown()) {
            tooltip.add(Component.empty());
            tooltip.add(ModComponents.ADDITIONAL_INFO.withStyle(ChatFormatting.AQUA));
            tooltip.add(description.withStyle(ChatFormatting.YELLOW));
        } else {
            tooltip.add(ModComponents.HOLD_SHIFT.withStyle(ChatFormatting.AQUA));
        }

        return tooltip;
    }
}
