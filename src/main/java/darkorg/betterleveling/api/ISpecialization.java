package darkorg.betterleveling.api;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;

public interface ISpecialization {
    int getLevelCost();

    String getName();

    TranslatableComponent getTranslation();

    TranslatableComponent getDescription();

    ItemStack getRepresentativeItemStack();
}
