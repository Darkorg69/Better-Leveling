package darkorg.betterleveling.util;

import com.mojang.blaze3d.systems.RenderSystem;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.network.chat.ModComponents;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class RenderUtil {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(BetterLeveling.MOD_ID, "textures/gui/background.png");

    public static void setShaderTexture() {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);
    }

    public static void setShaderTextureButton() {
        setShaderTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
    }

    public static MutableComponent getAvailableXP(LocalPlayer pLocalPlayer) {
        return new TranslatableComponent("").append(ModComponents.AVAILABLE).append(String.valueOf(pLocalPlayer.totalExperience)).append(ModComponents.XP);
    }

    public static MutableComponent getSkillCost(ISkill pSkill, int pCurrentLevel) {
        return new TranslatableComponent("").append(ModComponents.COST).append(String.valueOf(SkillUtil.getCurrentCost(pSkill, pCurrentLevel))).append(ModComponents.XP);
    }

    public static MutableComponent getCostPerLevel(ISkill pSkill) {
        return new TranslatableComponent("").append(ModComponents.COST).append(String.valueOf(pSkill.getCostPerLevel())).append(ModComponents.XP).append(ModComponents.PER_LEVEL);
    }

    public static MutableComponent getBonusPerLevel(ISkill pSkill) {
        return new TranslatableComponent("").append(ModComponents.BONUS).append(pSkill.getDescriptionIndexOf(1)).append(String.format("%.2f", pSkill.getBonusPerLevel() * 100)).append(pSkill.getDescriptionIndexOf(2)).append(pSkill.getDescriptionIndexOf(3)).append(ModComponents.PER_LEVEL);
    }

    public static MutableComponent getCurrentLevel(ISkill pSkill, int pCurrentLevel) {
        return new TranslatableComponent("").append(ModComponents.CURRENT).append(ModComponents.LEVEL).append(String.valueOf(pCurrentLevel)).append("/").append(String.valueOf(pSkill.getMaxLevel()));
    }

    public static MutableComponent getCurrentCost(ISkill pSkill, int pCurrentLevel) {
        return new TranslatableComponent("").append(ModComponents.CURRENT).append(ModComponents.COST).append(String.valueOf(SkillUtil.getCurrentCost(pSkill, pCurrentLevel))).append(ModComponents.XP);
    }

    public static MutableComponent getCurrentBonus(ISkill pSkill, int pCurrentLevel) {
        return new TranslatableComponent("").append(ModComponents.CURRENT).append(ModComponents.BONUS).append(pSkill.getDescriptionIndexOf(1)).append(String.format("%.2f", SkillUtil.getCurrentBonus(pSkill, pCurrentLevel) * 100)).append(pSkill.getDescriptionIndexOf(2)).append(pSkill.getDescriptionIndexOf(3));
    }

    public static MutableComponent getPrerequisite(ISkill pPrerequisiteSkill, int pPrerequisiteLevel) {
        return new TranslatableComponent("").append(ModComponents.BULLET).append(pPrerequisiteSkill.getTranslation()).append(": ").append(String.valueOf(pPrerequisiteLevel));
    }
}
