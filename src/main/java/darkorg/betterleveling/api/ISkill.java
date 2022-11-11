package darkorg.betterleveling.api;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public interface ISkill {
    int getMinLevel();

    int getMaxLevel();

    int getIncreaseCost(int pLevel);

    boolean isMinLevel(int pLevel);

    boolean isMaxLevel(int pLevel);

    String getName();

    ISpecialization getParentSpec();

    TranslatableComponent getTranslation();

    TranslatableComponent getDescription();

    TranslatableComponent getDescriptionIndexOf(int pIndex);

    ItemStack getRepresentativeItemStack();

    Map<ISkill, Integer> getPrerequisites();
}

