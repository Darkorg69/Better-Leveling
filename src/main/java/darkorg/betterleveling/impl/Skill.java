package darkorg.betterleveling.impl;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.util.RegistryUtil;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
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
    private final TranslatableContents translation;
    private final TranslatableContents description;

    public Skill(String pMod, String pName, ItemLike pItemLike, ISpecialization pParentSpec, ConfigValue<Integer> pMaxLevel, ConfigValue<Integer> pCostPerLevel, ConfigValue<Double> pBonusPerLevel, ConfigValue<String> pPrerequisites) {
        this.name = pName;
        this.itemLike = pItemLike;
        this.parentSpec = pParentSpec;
        this.maxLevel = pMaxLevel;
        this.costPerLevel = pCostPerLevel;
        this.bonusPerLevel = pBonusPerLevel;
        this.prerequisites = pPrerequisites;
        this.translation = new TranslatableContents(pMod + "." + pName + ".translation");
        this.description = new TranslatableContents(pMod + "." + pName + ".description");
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
        return MutableComponent.create(this.translation);
    }

    @Override
    public MutableComponent getDescription() {
        return MutableComponent.create(this.description);
    }

    @Override
    public MutableComponent getDescriptionIndexOf(int pIndex) {
        return MutableComponent.create(new TranslatableContents(getDescriptionIndexOfKey(pIndex)));
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
