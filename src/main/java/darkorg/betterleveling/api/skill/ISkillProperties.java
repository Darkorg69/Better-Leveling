package darkorg.betterleveling.api.skill;

import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.impl.specialization.Specialization;
import net.minecraft.world.level.ItemLike;

import java.util.Map;

public interface ISkillProperties {
    Specialization getParentSpecialization();

    ItemLike getItemLike();

    int getRow();

    int getColumn();

    int getMinLevel();

    int getMaxLevel();

    int getCostPerLevel();

    double getBonusPerLevel();

    Map<Skill, Integer> getPrerequisites();
}
