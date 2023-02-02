package darkorg.betterleveling.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Map;

public interface ISkill {
    String getName();

    int getMinLevel();

    int getMaxLevel();

    double getBonusPerLevel();

    int getCostPerLevel();

    ISpecialization getParentSpec();

    TranslationTextComponent getTranslation();

    TranslationTextComponent getDescription();

    ItemStack getRepresentativeItemStack();

    Map<ISkill, Integer> getPrerequisites();

    TranslationTextComponent getDescriptionIndexOf(int pIndex);
}

