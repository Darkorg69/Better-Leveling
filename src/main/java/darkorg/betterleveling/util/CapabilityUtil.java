package darkorg.betterleveling.util;

import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.registry.SkillRegistry;
import darkorg.betterleveling.registry.SpecRegistry;

import java.util.List;

public class CapabilityUtil {
    public static ISpecialization getSpecFromName(String name) {
        return SpecRegistry.getSpecNameMap().get(name);
    }

    public static ISkill getSkillFromName(String pName) {
        return SkillRegistry.getSkillNameMap().get(pName);
    }

    public static List<ISkill> getSkillsFromSpec(ISpecialization pSpecialization) {
        return SkillRegistry.getSkillSpecMap().get(pSpecialization);
    }
}
