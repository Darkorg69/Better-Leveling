package darkorg.betterleveling.impl.specialization;

import darkorg.betterleveling.api.IItemStackRepresentable;
import darkorg.betterleveling.api.ITranslatable;
import darkorg.betterleveling.api.specialization.ISpecialization;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.registry.Specializations;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class Specialization implements ISpecialization, ITranslatable, IItemStackRepresentable {
    private final SpecializationProperties properties;

    public Specialization(SpecializationProperties pProperties) {
        this.properties = pProperties;
    }

    @Override
    public SpecializationProperties getProperties() {
        return this.properties;
    }

    @Override
    public String getName() {
        return this.getRegistryName() != null ? this.getRegistryName().getPath() : null;
    }

    @Override
    public String getTranslationId() {
        return this.getRegistryName() != null ? this.getRegistryName().getNamespace() + ".specialization." + this.getName() : null;
    }

    @Override
    public ItemStack getRepresentativeItemStack() {
        return new ItemStack(this.properties.getItemLike());
    }

    public MutableComponent getUnlockCost() {
        return Component.translatable("").append(ModComponents.UNLOCK_COST).append(String.valueOf(this.getProperties().getLevelCost())).append(ModComponents.LEVELS);
    }

    public ResourceLocation getRegistryName() {
        return Specializations.getRegistry().getKey(this);
    }
}
