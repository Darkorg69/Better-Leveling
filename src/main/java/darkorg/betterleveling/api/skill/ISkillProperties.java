package darkorg.betterleveling.api.skill;

import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.impl.specialization.Specialization;
import net.minecraft.util.IItemProvider;

import java.util.Map;

public interface ISkillProperties {
    Specialization getParentSpecialization();

    IItemProvider getItemLike();

    int getRow();

    int getColumn();

    int getMinLevel();

    int getMaxLevel();

    int getCostPerLevel();

    double getBonusPerLevel();

    Map<Skill, Integer> getPrerequisites();
}
