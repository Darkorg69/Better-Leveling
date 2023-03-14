package darkorg.betterleveling.util;

import com.mojang.blaze3d.systems.RenderSystem;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.config.ModConfig;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.network.chat.ModComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.ForgeHooksClient;

public class RenderUtil {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(BetterLeveling.MOD_ID, "textures/gui/background.png");

    public static void setShaderTexture() {
        Minecraft.getInstance().getTextureManager().bind(BACKGROUND);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void setShaderTextureButton() {
        setShaderTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
    }

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
        NarratorChatListener.INSTANCE.sayNow(pGuiScreen.getNarrationMessage());
        minecraft.updateTitle();
    }

    public static IFormattableTextComponent getCannotUnlock() {
        return new TranslationTextComponent("").append(ModComponents.CANNOT_UNLOCK).append(String.valueOf(ModConfig.GAMEPLAY.firstSpecCost.get()));
    }

    public static IFormattableTextComponent getAvailableXP(int pAvailableExperience) {
        return new TranslationTextComponent("").append(ModComponents.AVAILABLE).append(String.valueOf(pAvailableExperience)).append(ModComponents.XP);
    }

    public static IFormattableTextComponent getSkillCost(Skill pSkill, int pCurrentLevel) {
        return new TranslationTextComponent("").append(ModComponents.COST).append(String.valueOf(pSkill.getCurrentCost(pCurrentLevel))).append(ModComponents.XP);
    }

    public static IFormattableTextComponent getCurrentCost(Skill pSkill, int pCurrentLevel) {
        return new TranslationTextComponent("").append(ModComponents.CURRENT).append(ModComponents.COST).append(String.valueOf(pSkill.getCurrentCost(pCurrentLevel))).append(ModComponents.XP);
    }

    public static IFormattableTextComponent getCurrentLevel(Skill pSkill, int pCurrentLevel) {
        return new TranslationTextComponent("").append(ModComponents.CURRENT).append(ModComponents.LEVEL).append(String.valueOf(pCurrentLevel)).append("/").append(String.valueOf(pSkill.getProperties().getMaxLevel()));
    }

    public static IFormattableTextComponent getCurrentBonus(Skill pSkill, int pCurrentLevel) {
        return new TranslationTextComponent("").append(ModComponents.CURRENT).append(ModComponents.BONUS).append(pSkill.getDescription(1)).append(String.format("%.2f", pSkill.getCurrentBonus(pCurrentLevel) * 100)).append(pSkill.getDescription(2)).append(pSkill.getDescription(3));
    }

    public static IFormattableTextComponent getPrerequisite(Skill pPrerequisiteSkill, int pPrerequisiteLevel) {
        return new TranslationTextComponent("").append(ModComponents.BULLET).append(pPrerequisiteSkill.getTranslation()).append(": ").append(String.valueOf(pPrerequisiteLevel));
    }
}
