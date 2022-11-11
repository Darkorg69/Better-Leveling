package darkorg.betterleveling.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import darkorg.betterleveling.api.IPlayerCapability;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.network.NetworkHandler;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.network.packets.AddSkillC2SPacket;
import darkorg.betterleveling.util.RenderUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

@OnlyIn(Dist.CLIENT)
public class SkillScreen extends Screen {
    private final int imageWidth = 176;
    private final int imageHeight = 166;
    private final ISkill playerSkill;
    private int leftPos, topPos;
    private int level, maxLevel, levelCost;
    private boolean isMinLevel, isMaxLevel;
    private LocalPlayer localPlayer;
    private IPlayerCapability playerCapability;

    public SkillScreen(ISkill pPlayerSkill) {
        super(new TranslatableComponent(""));
        this.playerSkill = pPlayerSkill;
        buildGui();
    }

    @Override
    protected void init() {
        this.leftPos = (width - imageWidth) / 2;
        this.topPos = (height - imageHeight) / 2;
        ExtendedButton increaseButton = new ExtendedButton((this.width / 2) - 44, this.topPos + 92, 88, 24, ModComponents.INCREASE_BUTTON, pButton -> Minecraft.getInstance().setScreen(new ConfirmScreen(this::onIncrease, this.playerSkill.getTranslation(), ModComponents.CONFIRM_INCREASE)));
        increaseButton.active = !this.isMaxLevel;
        addRenderableWidget(increaseButton);
        ExtendedButton decreaseButton = new ExtendedButton((this.width / 2) - 44, this.topPos + 126, 88, 24, ModComponents.DECREASE_BUTTON, pButton -> Minecraft.getInstance().setScreen(new ConfirmScreen(this::onDecrease, this.playerSkill.getTranslation(), ModComponents.CONFIRM_DECREASE)));
        decreaseButton.active = !this.isMinLevel;
        addRenderableWidget(decreaseButton);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        RenderUtil.setShaderTexture();
        blit(pPoseStack, this.leftPos, this.topPos, 0, 0, imageWidth, imageHeight);
        drawCenteredString(pPoseStack, this.font, this.playerSkill.getTranslation(), (width / 2), this.topPos + 12, 16777215);
        drawCenteredString(pPoseStack, this.font, this.playerSkill.getDescription(), (width / 2), this.topPos + 24, 16777215);
        drawCenteredString(pPoseStack, this.font, this.playerSkill.getDescriptionIndexOf(1), (width / 2), this.topPos + 36, 16777215);

        if (this.isMaxLevel) {
            drawCenteredString(pPoseStack, this.font, new TranslatableComponent("").append(ModComponents.MAX_LEVEL).withStyle(ChatFormatting.DARK_RED), (this.width / 2), this.topPos + 48, 16733525);
        } else {
            if (this.levelCost <= 1) {
                drawCenteredString(pPoseStack, this.font, new TranslatableComponent("").append(ModComponents.LEVEL_COST).append(" ").append(String.valueOf(this.levelCost)).append(" ").append(ModComponents.LEVEL), (this.width / 2), this.topPos + 48, 16777215);
            } else {
                drawCenteredString(pPoseStack, this.font, new TranslatableComponent("").append(ModComponents.LEVEL_COST).append(" ").append(String.valueOf(this.levelCost)).append(" ").append(ModComponents.LEVELS), (this.width / 2), this.topPos + 48, 16777215);
            }
        }
        drawCenteredString(pPoseStack, this.font, new TranslatableComponent("").append(ModComponents.CURRENT_LEVEL).append(" ").append(String.valueOf(this.level)).append("/").append(String.valueOf(this.maxLevel)), (this.width / 2), this.topPos + 70, 16777215);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void buildGui() {
        this.localPlayer = Minecraft.getInstance().player;

        if (localPlayer != null) {
            this.localPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(pPlayerCapability -> {
                this.playerCapability = pPlayerCapability;
                this.level = this.playerCapability.getLevel(this.localPlayer, this.playerSkill);
                this.maxLevel = this.playerSkill.getMaxLevel();
                this.levelCost = this.playerSkill.getIncreaseCost(this.level);
                this.isMinLevel = this.playerSkill.isMinLevel(this.level);
                this.isMaxLevel = this.playerSkill.isMaxLevel(this.level);
            });
        }
    }

    private void onIncrease(boolean pCallback) {
        if (pCallback) {
            NetworkHandler.sendToServer(new AddSkillC2SPacket(new Pair<>(this.playerSkill, 1)));
            Minecraft.getInstance().popGuiLayer();
        } else {
            Minecraft.getInstance().setScreen(this);
        }
    }

    private void onDecrease(boolean pCallback) {
        if (pCallback) {
            NetworkHandler.sendToServer(new AddSkillC2SPacket(new Pair<>(this.playerSkill, -1)));
            Minecraft.getInstance().popGuiLayer();
        } else {
            Minecraft.getInstance().setScreen(this);
        }
    }
}
