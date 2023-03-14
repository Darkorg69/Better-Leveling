package darkorg.betterleveling.gui.widget.button;

import com.mojang.blaze3d.vertex.PoseStack;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.gui.screen.SkillScreen;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.util.RenderUtil;
import darkorg.betterleveling.util.SkillUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkillButton extends AbstractButton {
    private final Skill skill;
    private final OnTooltip onTooltip;
    private final ItemStack itemStack;
    private final LocalPlayer localPlayer;
    private int currentLevel;
    private boolean isMaxLevel;

    public SkillButton(int pX, int pY, Skill pSkill, OnTooltip pOnTooltip) {
        super(pX + pSkill.getProperties().getColumn() * 50, pY + pSkill.getProperties().getRow() * 51, 32, 32, ModComponents.EMPTY);

        this.skill = pSkill;
        this.onTooltip = pOnTooltip;
        this.itemStack = pSkill.getRepresentativeItemStack();

        this.localPlayer = Minecraft.getInstance().player;

        if (this.localPlayer != null) {
            this.localPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                this.currentLevel = capability.getLevel(this.localPlayer, this.skill);
                this.isMaxLevel = this.skill.isMaxLevel(this.currentLevel);
                this.active = SkillUtil.hasUnlocked(capability, this.localPlayer, this.skill);
            });
        }
    }

    public Skill getSkill() {
        return this.skill;
    }

    @Override
    public void onPress() {
        Minecraft.getInstance().setScreen(new SkillScreen(this.skill));
    }

    @Override
    public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        Minecraft minecraft = Minecraft.getInstance();

        RenderUtil.setShaderTextureButton();

        if (this.active) {
            if (this.isMaxLevel) {
                this.blit(pPoseStack, this.x, this.y, 96, 166, width, height);
            } else {
                this.blit(pPoseStack, this.x, this.y, 64, 166, width, height);
                drawString(pPoseStack, minecraft.font, String.valueOf(this.currentLevel), this.x + 4, this.y + 4, 16777215);
            }
            minecraft.getItemRenderer().renderGuiItem(this.itemStack, this.x + 8, this.y + 8);
        } else {
            this.blit(pPoseStack, this.x, this.y, 64, 166, width, height);
            this.blit(pPoseStack, this.x + 6, this.y + 6, 0, 198, 20, 20);
        }

        if (this.isHovered || this.isFocused()) {
            this.renderToolTip(pPoseStack, pMouseX, pMouseY);
        }

        this.renderBg(pPoseStack, minecraft, pMouseX, pMouseY);
    }

    @Override
    public void renderToolTip(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        this.onTooltip.onTooltip(this, pPoseStack, pMouseX, pMouseY);
    }

    @Override
    public void updateNarration(NarrationElementOutput pNarrationElementOutput) {

    }

    @OnlyIn(Dist.CLIENT)
    public interface OnTooltip {
        void onTooltip(SkillButton pSkillButton, PoseStack pPoseStack, int pMouseX, int pMouseY);
    }
}
