package darkorg.betterleveling.impl;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.util.RegistryUtil;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.HashMap;
import java.util.Map;

public class Skill implements ISkill {
    private final String name;
    private final ItemLike itemLike;
    private final ISpecialization parentSpec;
    private final ConfigValue<Integer> maxLevel;
    private final ConfigValue<Integer> costPerLevel;
    private final ConfigValue<Double> bonusPerLevel;
    private final ConfigValue<String> prerequisites;
    private final TranslatableComponent translation;
    private final TranslatableComponent description;

    public Skill(String pMod, String pName, ItemLike pItemLike, ISpecialization pParentSpec, ConfigValue<Integer> pMaxLevel, ConfigValue<Integer> pCostPerLevel, ConfigValue<Double> pBonusPerLevel, ConfigValue<String> pPrerequisites) {
        this.name = pName;
        this.itemLike = pItemLike;
        this.parentSpec = pParentSpec;
        this.maxLevel = pMaxLevel;
        this.costPerLevel = pCostPerLevel;
        this.bonusPerLevel = pBonusPerLevel;
        this.prerequisites = pPrerequisites;
        this.translation = new TranslatableComponent(pMod + "." + pName + ".translation");
        this.description = new TranslatableComponent(pMod + "." + pName + ".description");
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
    public String getTranslationKey() {
        return this.translation.getKey();
    }

    @Override
    public String getDescriptionKey() {
        return this.description.getKey();
    }

    @Override
    public String getDescriptionIndexOfKey(int pIndex) {
        return getDescriptionKey() + pIndex;
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
    public MutableComponent getDescriptionIndexOf(int pIndex) {
        return new TranslatableComponent(getDescriptionIndexOfKey(pIndex));
    }

    @Override
    public ItemStack getRepresentativeItemStack() {
        return new ItemStack(this.itemLike);
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
