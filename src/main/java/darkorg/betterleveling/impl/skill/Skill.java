package darkorg.betterleveling.impl.skill;

import darkorg.betterleveling.api.IItemStackRepresentable;
import darkorg.betterleveling.api.ITranslatable;
import darkorg.betterleveling.api.skill.ISkill;
import darkorg.betterleveling.network.chat.ModComponents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Skill extends ForgeRegistryEntry<Skill> implements ISkill, ITranslatable, IItemStackRepresentable {
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
        return new TranslatableComponent("").append(ModComponents.BONUS).append(this.getDescription(1)).append(String.format("%.2f", this.getProperties().getBonusPerLevel() * 100)).append(this.getDescription(2)).append(this.getDescription(3)).append(ModComponents.PER_LEVEL);
    }

    public MutableComponent getCostPerLevel() {
        return new TranslatableComponent("").append(ModComponents.COST).append(String.valueOf(this.getProperties().getCostPerLevel())).append(ModComponents.XP).append(ModComponents.PER_LEVEL);
    }
}