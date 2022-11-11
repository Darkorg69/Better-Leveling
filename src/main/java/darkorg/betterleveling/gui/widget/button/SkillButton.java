package darkorg.betterleveling.gui.widget.button;

import com.mojang.blaze3d.vertex.PoseStack;
import darkorg.betterleveling.api.IPlayerCapability;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.gui.screen.SkillScreen;
import darkorg.betterleveling.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkillButton extends AbstractButton {
    private final ISkill playerSkill;
    private final ItemStack representativeStack;
    private final OnTooltip onTooltip;
    private int level;
    private boolean isUnlocked;
    private boolean isMaxLevel;
    private LocalPlayer localPlayer;
    private IPlayerCapability playerCapability;

    public SkillButton(int pX, int pY, ISkill pPlayerSkill, OnTooltip pOnTooltip) {
        super(pX, pY, 32, 32, new TranslatableComponent(""));
        this.playerSkill = pPlayerSkill;
        this.onTooltip = pOnTooltip;
        this.representativeStack = pPlayerSkill.getRepresentativeItemStack();
        init();
    }

    private void init() {
        this.localPlayer = Minecraft.getInstance().player;

        if (this.localPlayer != null) {
            this.localPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(pCapability -> {
                this.playerCapability = pCapability;
                this.level = this.playerCapability.getLevel(this.localPlayer, this.playerSkill);
                this.isUnlocked = this.playerCapability.isUnlocked(this.localPlayer, this.playerSkill);
                this.isMaxLevel = this.playerSkill.isMaxLevel(this.level);
            });
        }
        this.active = this.isUnlocked;
    }

    @Override
    public void onPress() {
        Minecraft.getInstance().setScreen(new SkillScreen(this.playerSkill));
    }

    @Override
    public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
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
        if (isHoveredOrFocused()) {
            this.renderToolTip(pPoseStack, pMouseX, pMouseY);
        }
        if (this.isUnlocked) {
            minecraft.getItemRenderer().renderGuiItem(this.representativeStack, x + 8, y + 8);
        }
        this.renderBg(pPoseStack, minecraft, pMouseX, pMouseY);
    }

    @Override
    public void renderToolTip(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        this.onTooltip.onTooltip(this, pPoseStack, pMouseX, pMouseY);
    }

    public ISkill getPlayerSkill() {
        return this.playerSkill;
    }

    @Override
    public void updateNarration(NarrationElementOutput pNarrationElementOutput) {

    }

    @OnlyIn(Dist.CLIENT)
    public interface OnTooltip {
        void onTooltip(SkillButton pSkillButton, PoseStack pPoseStack, int pMouseX, int pMouseY);
    }
}
