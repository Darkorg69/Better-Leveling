package darkorg.betterleveling.api;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public interface ISkill {
    String getName();

    int getMinLevel();

    int getMaxLevel();

    double getBonusPerLevel();

    int getCostPerLevel();

    ISpecialization getParentSpec();

    TranslatableComponent getTranslation();

    TranslatableComponent getDescription();

    ItemStack getRepresentativeItemStack();

    Map<ISkill, Integer> getPrerequisites();

    TranslatableComponent getDescriptionIndexOf(int pIndex);
}

