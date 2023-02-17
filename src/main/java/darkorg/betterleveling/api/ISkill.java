package darkorg.betterleveling.api;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public interface ISkill {
    String getName();

    int getMinLevel();

    int getMaxLevel();

    double getBonusPerLevel();

    int getCostPerLevel();

    ISpecialization getParentSpec();

    String getTranslationKey();

    String getDescriptionKey();

    String getDescriptionIndexOfKey(int pIndex);

    MutableComponent getTranslation();

    MutableComponent getDescription();

    MutableComponent getDescriptionIndexOf(int pIndex);

    ItemStack getRepresentativeItemStack();

    Map<ISkill, Integer> getPrerequisites();
}

