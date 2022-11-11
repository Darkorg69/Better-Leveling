package darkorg.betterleveling.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Pair;
import darkorg.betterleveling.api.IPlayerCapability;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.network.NetworkHandler;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.network.packets.AddSkillC2SPacket;
import darkorg.betterleveling.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;

@OnlyIn(Dist.CLIENT)
public class SkillScreen extends Screen {
    private final int imageWidth = 176;
    private final int imageHeight = 166;
    private final ISkill playerSkill;
    private int leftPos, topPos;
    private int level, maxLevel, levelCost;
    private boolean isMinLevel, isMaxLevel;
    private ClientPlayerEntity localPlayer;
    private IPlayerCapability playerCapability;

    public SkillScreen(ISkill pPlayerSkill) {
        super(new TranslationTextComponent(""));
        this.playerSkill = pPlayerSkill;
        buildGui();
    }

    @Override
    protected void init() {
        this.leftPos = (width - imageWidth) / 2;
        this.topPos = (height - imageHeight) / 2;
        ExtendedButton increaseButton = new ExtendedButton((this.width / 2) - 44, this.topPos + 92, 88, 24, ModComponents.INCREASE_BUTTON, pButton -> Minecraft.getInstance().setScreen(new ConfirmScreen(this::onIncrease, this.playerSkill.getTranslation(), ModComponents.CONFIRM_INCREASE)));
        increaseButton.active = !this.isMaxLevel;
        addButton(increaseButton);
        ExtendedButton decreaseButton = new ExtendedButton((this.width / 2) - 44, this.topPos + 126, 88, 24, ModComponents.DECREASE_BUTTON, pButton -> Minecraft.getInstance().setScreen(new ConfirmScreen(this::onDecrease, this.playerSkill.getTranslation(), ModComponents.CONFIRM_DECREASE)));
        decreaseButton.active = !this.isMinLevel;
        addButton(decreaseButton);
    }

    @Override
    public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pMatrixStack);
        RenderUtil.setShaderTexture();
        blit(pMatrixStack, this.leftPos, this.topPos, 0, 0, imageWidth, imageHeight);
        drawCenteredString(pMatrixStack, this.font, this.playerSkill.getTranslation(), (width / 2), this.topPos + 12, 16777215);
        drawCenteredString(pMatrixStack, this.font, this.playerSkill.getDescription(), (width / 2), this.topPos + 24, 16777215);
        drawCenteredString(pMatrixStack, this.font, this.playerSkill.getDescriptionIndexOf(1), (width / 2), this.topPos + 36, 16777215);

        if (this.isMaxLevel) {
            drawCenteredString(pMatrixStack, this.font, new TranslationTextComponent("").append(ModComponents.MAX_LEVEL).withStyle(TextFormatting.DARK_RED), (this.width / 2), this.topPos + 48, 16733525);
        } else {
            if (this.levelCost <= 1) {
                drawCenteredString(pMatrixStack, this.font, new TranslationTextComponent("").append(ModComponents.LEVEL_COST).append(" ").append(String.valueOf(this.levelCost)).append(" ").append(ModComponents.LEVEL), (this.width / 2), this.topPos + 48, 16777215);
            } else {
                drawCenteredString(pMatrixStack, this.font, new TranslationTextComponent("").append(ModComponents.LEVEL_COST).append(" ").append(String.valueOf(this.levelCost)).append(" ").append(ModComponents.LEVELS), (this.width / 2), this.topPos + 48, 16777215);
            }
        }
        drawCenteredString(pMatrixStack, this.font, new TranslationTextComponent("").append(ModComponents.CURRENT_LEVEL).append(" ").append(String.valueOf(this.level)).append("/").append(String.valueOf(this.maxLevel)), (this.width / 2), this.topPos + 70, 16777215);
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTick);
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
