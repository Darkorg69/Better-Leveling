package darkorg.betterleveling.impl;

import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.util.CapabilityUtil;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Specialization implements ISpecialization {
    private final String name;
    private final TranslatableComponent translation;
    private final TranslatableComponent description;
    private final ItemStack representativeStack;

    public Specialization(String pNamespace, String pName, Item pItem) {
        this.name = pName;
        this.translation = new TranslatableComponent(pNamespace + ".spec." + pName);
        this.description = new TranslatableComponent(pNamespace + ".spec." + pName + ".desc");
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
    public TranslatableComponent getTranslation() {
        return this.translation;
    }

    @Override
    public TranslatableComponent getDescription() {
        return this.description;
    }

    @Override
    public ItemStack getRepresentativeItemStack() {
        return this.representativeStack;
    }
}
