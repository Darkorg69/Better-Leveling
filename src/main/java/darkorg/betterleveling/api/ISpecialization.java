package darkorg.betterleveling.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

public interface ISpecialization {
    int getLevelCost();

    String getName();

    TranslationTextComponent getTranslation();

    TranslationTextComponent getDescription();

    ItemStack getRepresentativeItemStack();
}
