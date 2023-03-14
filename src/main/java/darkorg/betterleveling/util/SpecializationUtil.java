package darkorg.betterleveling.util;

import com.google.common.collect.ImmutableList;
import darkorg.betterleveling.impl.PlayerCapability;
import darkorg.betterleveling.impl.specialization.Specialization;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.registry.Specializations;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class SpecializationUtil {
    public static boolean hasUnlocked(PlayerCapability pCapability, PlayerEntity pPlayer) {
        return !getUnlocked(pCapability, pPlayer).isEmpty();
    }

    public static ImmutableList<Specialization> getUnlocked(PlayerCapability pCapability, PlayerEntity pPlayer) {
        List<Specialization> specializations = new ArrayList<>();

        for (Specialization specialization : Specializations.getAll()) {
            if (pCapability.getUnlocked(pPlayer, specialization)) {
                specializations.add(specialization);
            }
        }

        return ImmutableList.copyOf(specializations);
    }

    public static List<ITextComponent> getTooltip(Specialization pSpecialization, boolean pIsUnlocked, boolean pCanUnlock) {
        List<ITextComponent> tooltip = new ArrayList<>();

        IFormattableTextComponent translation = pSpecialization.getTranslation();
        IFormattableTextComponent description = pSpecialization.getDescription();
        IFormattableTextComponent unlockCost = pSpecialization.getUnlockCost();

        tooltip.add(translation.append(ModComponents.SPEC));

        if (!pIsUnlocked) {
            tooltip.add(ModComponents.LOCKED.withStyle(TextFormatting.DARK_RED));
            tooltip.add(unlockCost.withStyle(pCanUnlock ? TextFormatting.GREEN : TextFormatting.RED));
        }

        if (Screen.hasShiftDown()) {
            tooltip.add(ModComponents.EMPTY);
            tooltip.add(ModComponents.ADDITIONAL_INFO.withStyle(TextFormatting.AQUA));
            tooltip.add(description.withStyle(TextFormatting.YELLOW));
        } else {
            tooltip.add(ModComponents.HOLD_SHIFT.withStyle(TextFormatting.AQUA));
        }

        return tooltip;
    }
}
