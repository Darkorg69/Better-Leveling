package darkorg.betterleveling.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Map;

public interface ISkill {
    int getMinLevel();

    int getMaxLevel();

    int getIncreaseCost(int pLevel);

    boolean isMinLevel(int pLevel);

    boolean isMaxLevel(int pLevel);

    String getName();

    ISpecialization getParentSpec();

    TranslationTextComponent getTranslation();

    TranslationTextComponent getDescription();

    TranslationTextComponent getDescriptionIndexOf(int pIndex);

    ItemStack getRepresentativeItemStack();

    Map<ISkill, Integer> getPrerequisites();
}

