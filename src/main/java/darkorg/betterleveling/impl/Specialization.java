package darkorg.betterleveling.impl;

import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.config.ServerConfig;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class Specialization implements ISpecialization {
    private final String name;
    private final ItemLike itemLike;
    private final TranslatableContents translation;
    private final TranslatableContents description;

    public Specialization(String pMod, String pName, ItemLike pItemLike) {
        this.name = pName;
        this.itemLike = pItemLike;
        this.translation = new TranslatableContents(pMod + ".spec." + pName);
        this.description = new TranslatableContents(pMod + ".spec." + pName + ".desc");
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
        return MutableComponent.create(this.translation);
    }

    @Override
    public MutableComponent getDescription() {
        return MutableComponent.create(this.description);
    }

    @Override
    public ItemStack getRepresentativeItemStack() {
        return new ItemStack(this.itemLike);
    }
}
