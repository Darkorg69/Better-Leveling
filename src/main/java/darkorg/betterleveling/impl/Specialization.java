package darkorg.betterleveling.impl;

import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.util.CapabilityUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

public class Specialization implements ISpecialization {
    private final String name;
    private final TranslationTextComponent translation;
    private final TranslationTextComponent description;
    private final ItemStack representativeStack;

    public Specialization(String pNamespace, String pName, Item pItem) {
        this.name = pName;
        this.translation = new TranslationTextComponent(pNamespace + ".spec." + pName);
        this.description = new TranslationTextComponent(pNamespace + ".spec." + pName + ".desc");
        this.representativeStack = new ItemStack(pItem);
    }

    @Override
    public int getLevelCost() {
        return CapabilityUtil.getSkillsFromSpec(this).size() * 5;
    }

    @Override
    public String getName() {
        return this.name;
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
    public ItemStack getRepresentativeItemStack() {
        return this.representativeStack;
    }
}
