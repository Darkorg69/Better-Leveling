package darkorg.betterleveling.util;

import com.mojang.blaze3d.systems.RenderSystem;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.ISkill;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import static darkorg.betterleveling.network.chat.ModComponents.*;

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

    public static IFormattableTextComponent getAvailableXP(ClientPlayerEntity pLocalPlayer) {
        return new TranslationTextComponent("").append(AVAILABLE).append(String.valueOf(pLocalPlayer.totalExperience)).append(XP);
    }

    public static IFormattableTextComponent getSkillCost(ISkill pSkill, int pCurrentLevel) {
        return new TranslationTextComponent("").append(COST).append(String.valueOf(SkillUtil.getCurrentCost(pSkill, pCurrentLevel))).append(XP);
    }

    public static IFormattableTextComponent getCostPerLevel(ISkill pSkill) {
        return new TranslationTextComponent("").append(COST).append(String.valueOf(pSkill.getCostPerLevel())).append(XP).append(PER_LEVEL);
    }

    public static IFormattableTextComponent getBonusPerLevel(ISkill pSkill) {
        return new TranslationTextComponent("").append(BONUS).append(pSkill.getDescriptionIndexOf(1)).append(String.format("%.2f", pSkill.getBonusPerLevel() * 100)).append(pSkill.getDescriptionIndexOf(2)).append(pSkill.getDescriptionIndexOf(3)).append(PER_LEVEL);
    }

    public static IFormattableTextComponent getCurrentLevel(ISkill pSkill, int pCurrentLevel) {
        return new TranslationTextComponent("").append(CURRENT).append(LEVEL).append(String.valueOf(pCurrentLevel)).append("/").append(String.valueOf(pSkill.getMaxLevel()));
    }

    public static IFormattableTextComponent getCurrentCost(ISkill pSkill, int pCurrentLevel) {
        return new TranslationTextComponent("").append(CURRENT).append(COST).append(String.valueOf(SkillUtil.getCurrentCost(pSkill, pCurrentLevel))).append(XP);
    }

    public static IFormattableTextComponent getCurrentBonus(ISkill pSkill, int pCurrentLevel) {
        return new TranslationTextComponent("").append(CURRENT).append(BONUS).append(pSkill.getDescriptionIndexOf(1)).append(String.format("%.2f", SkillUtil.getCurrentBonus(pSkill, pCurrentLevel) * 100)).append(pSkill.getDescriptionIndexOf(2)).append(pSkill.getDescriptionIndexOf(3));
    }

    public static IFormattableTextComponent getPrerequisite(ISkill pPrerequisiteSkill, int pPrerequisiteLevel) {
        return new TranslationTextComponent("").append(BULLET).append(pPrerequisiteSkill.getTranslation()).append(": ").append(String.valueOf(pPrerequisiteLevel));
    }
}
