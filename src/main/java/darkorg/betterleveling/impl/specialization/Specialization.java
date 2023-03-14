package darkorg.betterleveling.impl.specialization;

import darkorg.betterleveling.api.IItemStackRepresentable;
import darkorg.betterleveling.api.ITranslatable;
import darkorg.betterleveling.api.specialization.ISpecialization;
import darkorg.betterleveling.network.chat.ModComponents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Specialization extends ForgeRegistryEntry<Specialization> implements ISpecialization, ITranslatable, IItemStackRepresentable {
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

    public IFormattableTextComponent getUnlockCost() {
        return new TranslationTextComponent("").append(ModComponents.UNLOCK_COST).append(String.valueOf(this.getProperties().getLevelCost())).append(ModComponents.LEVELS);
    }
}
