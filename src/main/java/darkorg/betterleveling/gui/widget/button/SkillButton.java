package darkorg.betterleveling.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import darkorg.betterleveling.api.IPlayerCapability;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.gui.screen.SkillScreen;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.util.RenderUtil;
import darkorg.betterleveling.util.SkillUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkillButton extends AbstractButton {
    private final ISkill skill;
    private final ItemStack representativeStack;
    private final OnTooltip onTooltip;
    private int level;
    private boolean isUnlocked;
    private boolean isMaxLevel;
    private ClientPlayerEntity localPlayer;
    private IPlayerCapability playerCapability;

    public SkillButton(int pX, int pY, ISkill pSkill, OnTooltip pOnTooltip) {
        super(pX, pY, 32, 32, ModComponents.EMPTY);
        this.skill = pSkill;
        this.onTooltip = pOnTooltip;
        this.representativeStack = pSkill.getRepresentativeItemStack();
        init();
    }

    private void init() {
        this.localPlayer = Minecraft.getInstance().player;

        if (this.localPlayer != null) {
            this.localPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(pCapability -> {
                this.playerCapability = pCapability;
                this.level = this.playerCapability.getLevel(this.localPlayer, this.skill);
                this.isUnlocked = this.playerCapability.hasUnlocked(this.localPlayer, this.skill);
                this.isMaxLevel = SkillUtil.isMaxLevel(this.skill, this.level);
            });
        }
        this.active = this.isUnlocked;
    }

    @Override
    public void onPress() {
        Minecraft.getInstance().setScreen(new SkillScreen(this.skill));
    }

    @Override
    public void renderButton(MatrixStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        Minecraft minecraft = Minecraft.getInstance();

        RenderUtil.setShaderTextureButton();

        if (!this.isUnlocked) {
            this.blit(pPoseStack, x, y, 64, 166, width, height);
            this.blit(pPoseStack, x + 6, y + 6, 0, 198, 20, 20);
        } else {
            if (!this.isMaxLevel) {
                this.blit(pPoseStack, x, y, 64, 166, width, height);
                drawString(pPoseStack, minecraft.font, String.valueOf(this.level), x + 4, y + 4, 16777215);
            } else {
                this.blit(pPoseStack, x, y, 96, 166, width, height);
            }
        }
        if (isHovered() || isFocused()) {
            this.renderToolTip(pPoseStack, pMouseX, pMouseY);
        }
        if (this.isUnlocked) {
            minecraft.getItemRenderer().renderGuiItem(this.representativeStack, x + 8, y + 8);
        }
        this.renderBg(pPoseStack, minecraft, pMouseX, pMouseY);
    }

    @Override
    public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
        this.onTooltip.onTooltip(this, pPoseStack, pMouseX, pMouseY);
    }

    public ISkill getSkill() {
        return this.skill;
    }

    @OnlyIn(Dist.CLIENT)
    public interface OnTooltip {
        void onTooltip(SkillButton pSkillButton, MatrixStack pPoseStack, int pMouseX, int pMouseY);

    }
}
