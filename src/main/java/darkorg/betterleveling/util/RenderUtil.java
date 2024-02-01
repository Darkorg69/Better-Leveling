package darkorg.betterleveling.util;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.config.ModConfig;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.network.chat.ModComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;

public class RenderUtil {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(BetterLeveling.MOD_ID, "textures/gui/background.png");

    public static void updateScreen(Screen pGuiScreen) {
        Minecraft minecraft = Minecraft.getInstance();
        ForgeHooksClient.clearGuiLayers(minecraft);
        Screen old = minecraft.screen;
        if (old != null && pGuiScreen != old) {
            old.removed();
        }
        minecraft.screen = pGuiScreen;
        pGuiScreen.init(minecraft, minecraft.getWindow().getGuiScaledWidth(), minecraft.getWindow().getGuiScaledHeight());
        minecraft.noRender = false;
        minecraft.updateTitle();
    }

    public static MutableComponent getCannotUnlock() {
        return Component.translatable("").append(ModComponents.CANNOT_UNLOCK).append(String.valueOf(ModConfig.GAMEPLAY.firstSpecCost.get()));
    }

    public static MutableComponent getAvailableXP(int pAvailableExperience) {
        return Component.translatable("").append(ModComponents.AVAILABLE).append(String.valueOf(pAvailableExperience)).append(ModComponents.XP);
    }

    public static MutableComponent getSkillCost(Skill pSkill, int pCurrentLevel) {
        return Component.translatable("").append(ModComponents.COST).append(String.valueOf(pSkill.getCurrentCost(pCurrentLevel))).append(ModComponents.XP);
    }

    public static MutableComponent getCurrentCost(Skill pSkill, int pCurrentLevel) {
        return Component.translatable("").append(ModComponents.CURRENT).append(ModComponents.COST).append(String.valueOf(pSkill.getCurrentCost(pCurrentLevel))).append(ModComponents.XP);
    }

    public static MutableComponent getCurrentLevel(Skill pSkill, int pCurrentLevel) {
        return Component.translatable("").append(ModComponents.CURRENT).append(ModComponents.LEVEL).append(String.valueOf(pCurrentLevel)).append("/").append(String.valueOf(pSkill.getProperties().getMaxLevel()));
    }

    public static MutableComponent getCurrentBonus(Skill pSkill, int pCurrentLevel) {
        return Component.translatable("").append(ModComponents.CURRENT).append(ModComponents.BONUS).append(pSkill.getDescription(1)).append(String.format("%.2f", pSkill.getCurrentBonus(pCurrentLevel) * 100)).append(pSkill.getDescription(2)).append(pSkill.getDescription(3));
    }

    public static MutableComponent getPrerequisite(Skill pPrerequisiteSkill, int pPrerequisiteLevel) {
        return Component.translatable("").append(ModComponents.BULLET).append(pPrerequisiteSkill.getTranslation()).append(": ").append(String.valueOf(pPrerequisiteLevel));
    }
}
