package darkorg.betterleveling.util;

import com.mojang.blaze3d.systems.RenderSystem;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.network.chat.ModTranslatableContents;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.MutableComponent;
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
        return MutableComponent.create(ModTranslatableContents.AVAILABLE).append(String.valueOf(pLocalPlayer.totalExperience)).append(MutableComponent.create(ModTranslatableContents.XP));
    }

    public static MutableComponent getSkillCost(ISkill pSkill, int pCurrentLevel) {
        return MutableComponent.create(ModTranslatableContents.COST).append(String.valueOf(SkillUtil.getCurrentCost(pSkill, pCurrentLevel))).append(MutableComponent.create(ModTranslatableContents.XP));
    }

    public static MutableComponent getCostPerLevel(ISkill pSkill) {
        return MutableComponent.create(ModTranslatableContents.COST).append(String.valueOf(pSkill.getCostPerLevel())).append(MutableComponent.create(ModTranslatableContents.XP)).append(MutableComponent.create(ModTranslatableContents.PER_LEVEL));
    }

    public static MutableComponent getBonusPerLevel(ISkill pSkill) {
        return MutableComponent.create(ModTranslatableContents.BONUS).append(pSkill.getDescriptionIndexOf(1)).append(String.format("%.2f", pSkill.getBonusPerLevel() * 100)).append(pSkill.getDescriptionIndexOf(2)).append(pSkill.getDescriptionIndexOf(3)).append(MutableComponent.create(ModTranslatableContents.PER_LEVEL));
    }

    public static MutableComponent getCurrentLevel(ISkill pSkill, int pCurrentLevel) {
        return MutableComponent.create(ModTranslatableContents.CURRENT).append(MutableComponent.create(ModTranslatableContents.LEVEL)).append(String.valueOf(pCurrentLevel)).append("/").append(String.valueOf(pSkill.getMaxLevel()));
    }

    public static MutableComponent getCurrentCost(ISkill pSkill, int pCurrentLevel) {
        return MutableComponent.create(ModTranslatableContents.CURRENT).append(MutableComponent.create(ModTranslatableContents.COST)).append(String.valueOf(SkillUtil.getCurrentCost(pSkill, pCurrentLevel))).append(MutableComponent.create(ModTranslatableContents.XP));
    }

    public static MutableComponent getCurrentBonus(ISkill pSkill, int pCurrentLevel) {
        return MutableComponent.create(ModTranslatableContents.CURRENT).append(MutableComponent.create(ModTranslatableContents.BONUS)).append(pSkill.getDescriptionIndexOf(1)).append(String.format("%.2f", SkillUtil.getCurrentBonus(pSkill, pCurrentLevel) * 100)).append(pSkill.getDescriptionIndexOf(2)).append(pSkill.getDescriptionIndexOf(3));
    }

    public static MutableComponent getPrerequisite(ISkill pPrerequisiteSkill, int pPrerequisiteLevel) {
        return MutableComponent.create(ModTranslatableContents.BULLET).append(pPrerequisiteSkill.getTranslation()).append(": ").append(String.valueOf(pPrerequisiteLevel));
    }
}
