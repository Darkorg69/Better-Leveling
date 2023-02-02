package darkorg.betterleveling.impl;

import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.config.ServerConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.text.TranslationTextComponent;

public class Specialization implements ISpecialization {
    private final String name;
    private final IItemProvider itemLike;
    private final TranslationTextComponent translation;
    private final TranslationTextComponent description;

    public Specialization(String pMod, String pName, IItemProvider pItemLike) {
        this.name = pName;
        this.itemLike = pItemLike;
        this.translation = new TranslationTextComponent(pMod + ".spec." + pName);
        this.description = new TranslationTextComponent(pMod + ".spec." + pName + ".desc");
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getLevelCost() {
        return ServerConfig.SPEC_LEVEL_COST.get();
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
        return new ItemStack(this.itemLike);
    }
}
