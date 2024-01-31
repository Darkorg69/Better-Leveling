package darkorg.betterleveling.impl.skill;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.skill.ISkillProperties;
import darkorg.betterleveling.impl.specialization.Specialization;
import darkorg.betterleveling.registry.Skills;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class SkillProperties implements ISkillProperties {
    private final RegistryObject<Specialization> parentSpecialization;
    private final ItemLike itemLike;
    private final IntValue row;
    private final IntValue column;
    private final IntValue maxLevel;
    private final IntValue costPerLevel;
    private final DoubleValue bonusPerLevel;
    private final ConfigValue<String> prerequisites;

    public SkillProperties(RegistryObject<Specialization> pParentSpecialization, ItemLike pItemLike, IntValue pRow, IntValue pColumn, IntValue pMaxLevel, IntValue pCostPerLevel, DoubleValue pBonusPerLevel, ConfigValue<String> pPrerequisites) {
        this.parentSpecialization = pParentSpecialization;
        this.itemLike = pItemLike;
        this.row = pRow;
        this.column = pColumn;
        this.maxLevel = pMaxLevel;
        this.costPerLevel = pCostPerLevel;
        this.bonusPerLevel = pBonusPerLevel;
        this.prerequisites = pPrerequisites;
    }

    public static SkillProperties of(RegistryObject<Specialization> pParentSpecialization, ItemLike pItemLike, IntValue pRow, IntValue pColumn, IntValue pMaxLevel, IntValue pCostPerLevel, DoubleValue pBonusPerLevel, ConfigValue<String> pPrerequisites) {
        return new SkillProperties(pParentSpecialization, pItemLike, pRow, pColumn, pMaxLevel, pCostPerLevel, pBonusPerLevel, pPrerequisites);
    }

    @Override
    public Specialization getParentSpecialization() {
        return parentSpecialization.get();
    }

    @Override
    public ItemLike getItemLike() {
        return this.itemLike;
    }

    @Override
    public int getRow() {
        return row.get();
    }

    @Override
    public int getColumn() {
        return column.get();
    }

    @Override
    public int getMinLevel() {
        return 0;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel.get();
    }

    @Override
    public int getCostPerLevel() {
        return costPerLevel.get();
    }

    @Override
    public double getBonusPerLevel() {
        return bonusPerLevel.get();
    }

    @Override
    public Map<Skill, Integer> getPrerequisites() {
        Map<Skill, Integer> prerequisitesMap = new HashMap<>();

        String config = this.prerequisites.get();

        if (!config.isEmpty()) {
            if (config.matches("^(\\w+:\\d+,)*(\\w+:\\d+)$")) {
                for (String s : config.split(",")) {
                    String[] nameLevel = s.split(":");

                    prerequisitesMap.put(Skills.getFrom(nameLevel[0]), Integer.parseInt(nameLevel[1]));
                }

            } else {
                BetterLeveling.LOGGER.warn("Malformed prerequisite value! " + "Cannot parse string `" + config + "`");
            }
        }

        return prerequisitesMap;
    }
}
