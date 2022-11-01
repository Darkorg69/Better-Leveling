package darkorg.betterleveling.impl;

import com.mojang.datafixers.util.Pair;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.api.ISpecialization;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.HashMap;
import java.util.Map;

public class Skill implements ISkill {
    private final String name;
    private final int minLevel;
    private final int maxLevel;
    private final TranslationTextComponent translation;
    private final TranslationTextComponent description;
    private final ISpecialization parentSpec;
    private final ItemStack representativeItemStack;
    private final Map<ISkill, Integer> prerequisites;

    @SafeVarargs
    public Skill(String pMod, String pName, int pMinLevel, int pMaxLevel, ISpecialization pParentSpec, Item pRepresentativeItem, Pair<ISkill, Integer>... pPrerequisites) {
        this.name = pName;
        this.minLevel = pMinLevel;
        this.maxLevel = pMaxLevel;
        this.translation = new TranslationTextComponent(pMod + "." + pName);
        this.description = new TranslationTextComponent(pMod + "." + pName + ".desc");
        this.parentSpec = pParentSpec;
        this.representativeItemStack = new ItemStack(pRepresentativeItem);
        this.prerequisites = new HashMap<>();

        for (Pair<ISkill, Integer> pPrerequisite : pPrerequisites) {
            this.prerequisites.put(pPrerequisite.getFirst(), pPrerequisite.getSecond());
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getMinLevel() {
        return this.minLevel;
    }

    @Override
    public boolean isMinLevel(int pLevel) {
        return this.minLevel == pLevel;
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public boolean isMaxLevel(int pLevel) {
        return this.maxLevel == pLevel;
    }

    @Override
    public int getLevelCost(int pLevel) {
        return Math.round((14 * pLevel) / 9.0F) + 1;
    }

    @Override
    public ISpecialization getParentSpec() {
        return this.parentSpec;
    }

    @Override
    public TranslationTextComponent getTranslation() {
        return this.translation;
    }

    @Override
    public TranslationTextComponent getDescription() {
        return this.description;
    }

    @Override
    public TranslationTextComponent getDescriptionIndexOf(int pIndex) {
        int index = Math.max(1, pIndex);
        return new TranslationTextComponent(getDescription().getKey() + index);
    }

    @Override
    public ItemStack getRepresentativeItemStack() {
        return this.representativeItemStack;
    }

    @Override
    public Map<ISkill, Integer> getPrerequisites() {
        return this.prerequisites;
    }

    @Override
    public boolean hasPrerequisites() {
        return !this.getPrerequisites().isEmpty();
    }
}
