package darkorg.betterleveling.impl;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.util.RegistryUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.HashMap;
import java.util.Map;

public class Skill implements ISkill {
    private final String name;
    private final IItemProvider itemLike;
    private final ISpecialization parentSpec;
    private final ConfigValue<Integer> maxLevel;
    private final ConfigValue<Integer> costPerLevel;
    private final ConfigValue<Double> bonusPerLevel;
    private final ConfigValue<String> prerequisites;
    private final TranslationTextComponent translation;
    private final TranslationTextComponent description;

    public Skill(String pMod, String pName, IItemProvider pItemLike, ISpecialization pParentSpec, ConfigValue<Integer> pMaxLevel, ConfigValue<Integer> pCostPerLevel, ConfigValue<Double> pBonusPerLevel, ConfigValue<String> pPrerequisites) {
        this.name = pName;
        this.itemLike = pItemLike;
        this.parentSpec = pParentSpec;
        this.maxLevel = pMaxLevel;
        this.costPerLevel = pCostPerLevel;
        this.bonusPerLevel = pBonusPerLevel;
        this.prerequisites = pPrerequisites;
        this.translation = new TranslationTextComponent(pMod + "." + pName + ".translation");
        this.description = new TranslationTextComponent(pMod + "." + pName + ".description");
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getMinLevel() {
        return 0;
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel.get();
    }

    @Override
    public int getCostPerLevel() {
        return this.costPerLevel.get();
    }

    public double getBonusPerLevel() {
        return this.bonusPerLevel.get();
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
    public ItemStack getRepresentativeItemStack() {
        return new ItemStack(this.itemLike);
    }

    @Override
    public TranslationTextComponent getDescriptionIndexOf(int pIndex) {
        return new TranslationTextComponent(this.description.getKey() + pIndex);
    }

    @Override
    public Map<ISkill, Integer> getPrerequisites() {
        Map<ISkill, Integer> map = new HashMap<>();
        String prerequisites = this.prerequisites.get();
        if (!prerequisites.isEmpty()) {
            if (prerequisites.matches("^(\\w+:\\d+,)*(\\w+:\\d+)$")) {
                for (String s : prerequisites.split(",")) {
                    String[] nameLevel = s.split(":");
                    map.put(RegistryUtil.getSkillFromName(nameLevel[0]), Integer.parseInt(nameLevel[1]));
                }
            } else {
                BetterLeveling.LOGGER.warn("Malformed skill prerequisite value, cannot parse string: " + prerequisites);
            }
        }
        return map;
    }
}
