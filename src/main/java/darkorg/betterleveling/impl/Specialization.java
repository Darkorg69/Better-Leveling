package darkorg.betterleveling.impl;

import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.config.ServerConfig;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class Specialization implements ISpecialization {
    private final String name;
    private final ItemLike itemLike;
    private final TranslatableComponent translation;
    private final TranslatableComponent description;

    public Specialization(String pMod, String pName, ItemLike pItemLike) {
        this.name = pName;
        this.itemLike = pItemLike;
        this.translation = new TranslatableComponent(pMod + ".spec." + pName);
        this.description = new TranslatableComponent(pMod + ".spec." + pName + ".desc");
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getLevelCost() {
        return ServerConfig.SECONDARY_SPEC_COST.get();
    }

    @Override
    public String getTranslationKey() {
        return this.translation.getKey();
    }

    @Override
    public String getDescriptionKey() {
        return this.description.getKey();
    }

    @Override
    public MutableComponent getTranslation() {
        return new TranslatableComponent(this.getTranslationKey());

    }

    @Override
    public MutableComponent getDescription() {
        return new TranslatableComponent(this.getDescriptionKey());
    }

    @Override
    public ItemStack getRepresentativeItemStack() {
        return new ItemStack(this.itemLike);
    }
}
