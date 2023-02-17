package darkorg.betterleveling.api;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

public interface ISpecialization {
    int getLevelCost();

    String getName();

    String getTranslationKey();

    String getDescriptionKey();

    MutableComponent getTranslation();

    MutableComponent getDescription();

    ItemStack getRepresentativeItemStack();
}
