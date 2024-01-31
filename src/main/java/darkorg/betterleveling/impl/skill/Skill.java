package darkorg.betterleveling.impl.skill;

import darkorg.betterleveling.api.IItemStackRepresentable;
import darkorg.betterleveling.api.ITranslatable;
import darkorg.betterleveling.api.skill.ISkill;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.registry.Skills;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class Skill implements ISkill, ITranslatable, IItemStackRepresentable {
    private final SkillProperties properties;

    public Skill(SkillProperties pProperties) {
        this.properties = pProperties;
    }

    public SkillProperties getProperties() {
        return this.properties;
    }

    public String getName() {

        return this.getRegistryName() != null ? this.getRegistryName().getPath() : null;
    }

    public String getTranslationId() {
        return this.getRegistryName() != null ? this.getRegistryName().getNamespace() + ".skill." + this.getName() : null;
    }

    public ItemStack getRepresentativeItemStack() {
        return new ItemStack(this.properties.getItemLike());
    }

    public MutableComponent getBonusPerLevel() {
        return Component.translatable("").append(ModComponents.BONUS).append(this.getDescription(1)).append(String.format("%.2f", this.getProperties().getBonusPerLevel() * 100)).append(this.getDescription(2)).append(this.getDescription(3)).append(ModComponents.PER_LEVEL);
    }

    public MutableComponent getCostPerLevel() {
        return Component.translatable("").append(ModComponents.COST).append(String.valueOf(this.getProperties().getCostPerLevel())).append(ModComponents.XP).append(ModComponents.PER_LEVEL);
    }

    public ResourceLocation getRegistryName() {
        return Skills.getRegistry().getKey(this);
    }
}
