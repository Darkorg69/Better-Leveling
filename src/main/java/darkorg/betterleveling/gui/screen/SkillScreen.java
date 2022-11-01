package darkorg.betterleveling.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Pair;
import darkorg.betterleveling.api.IPlayerCapability;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.network.NetworkHandler;
import darkorg.betterleveling.network.packets.AddSkillC2SPacket;
import darkorg.betterleveling.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;

import javax.annotation.Nonnull;

import static darkorg.betterleveling.network.chat.ModTextComponents.*;

@OnlyIn(Dist.CLIENT)
public class SkillScreen extends Screen {
    private int leftPos, topPos;
    private int level, maxLevel, levelCost;
    private boolean isMinLevel, isMaxLevel;
    private ClientPlayerEntity clientPlayer;
    private IPlayerCapability playerCapability;
    private final int imageWidth = 176;
    private final int imageHeight = 166;
    private final ISkill playerSkill;

    public SkillScreen(ISkill pPlayerSkill) {
        super(new TranslationTextComponent(""));
        this.playerSkill = pPlayerSkill;
        buildGui();
    }

    @Override
    protected void init() {
        this.leftPos = (width - imageWidth) / 2;
        this.topPos = (height - imageHeight) / 2;
        ExtendedButton increaseButton = new ExtendedButton((this.width / 2) - 44, this.topPos + 92, 88, 24, INCREASE_BUTTON, pButton -> Minecraft.getInstance().displayGuiScreen(new ConfirmScreen(this::onIncrease, this.playerSkill.getDescription(), CONFIRM_INCREASE)));
        increaseButton.active = !this.isMaxLevel;
        addButton(increaseButton);
        ExtendedButton decreaseButton = new ExtendedButton((this.width / 2) - 44, this.topPos + 126, 88, 24, DECREASE_BUTTON, pButton -> Minecraft.getInstance().displayGuiScreen(new ConfirmScreen(this::onDecrease, this.playerSkill.getDescription(), CONFIRM_DECREASE)));
        decreaseButton.active = !this.isMinLevel;
        addButton(decreaseButton);
    }

    @Override
    public void render(@Nonnull MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pMatrixStack);
        RenderUtil.setShaderTexture();
        blit(pMatrixStack, this.leftPos, this.topPos, 0, 0, imageWidth, imageHeight);
        drawCenteredString(pMatrixStack, this.font, this.playerSkill.getTranslation(), (width / 2), this.topPos + 12, 16777215);
        drawCenteredString(pMatrixStack, this.font, this.playerSkill.getDescription(), (width / 2), this.topPos + 24, 16777215);
        drawCenteredString(pMatrixStack, this.font, this.playerSkill.getDescriptionIndexOf(1), (width / 2), this.topPos + 36, 16777215);

        if (this.isMaxLevel) {
            drawCenteredString(pMatrixStack, this.font, new TranslationTextComponent("").appendSibling(MAX_LEVEL), (this.width / 2), this.topPos + 48, 16733525);
        } else {
            if (this.levelCost <= 1) {
                drawCenteredString(pMatrixStack, this.font, new TranslationTextComponent("").appendSibling(LEVEL_COST).appendString(" ").appendString(String.valueOf(this.levelCost)).appendString(" ").appendSibling(LEVEL), (this.width / 2), this.topPos + 48, 16777215);
            } else {
                drawCenteredString(pMatrixStack, this.font, new TranslationTextComponent("").appendSibling(LEVEL_COST).appendString(" ").appendString(String.valueOf(this.levelCost)).appendString(" ").appendSibling(LEVELS), (this.width / 2), this.topPos + 48, 16777215);
            }
        }
        drawCenteredString(pMatrixStack, this.font, new TranslationTextComponent("").appendSibling(CURRENT_LEVEL).appendString(" ").appendString(String.valueOf(this.level)).appendString("/").appendString(String.valueOf(this.maxLevel)), (this.width / 2), this.topPos + 70, 16777215);
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void buildGui() {
        this.clientPlayer = Minecraft.getInstance().player;

        if (clientPlayer != null) {
            this.clientPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(pPlayerCapability -> {
                this.playerCapability = pPlayerCapability;
                this.level = this.playerCapability.getLevel(this.clientPlayer, this.playerSkill);
                this.maxLevel = this.playerSkill.getMaxLevel();
                this.levelCost = this.playerSkill.getLevelCost(this.level);
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
            Minecraft.getInstance().displayGuiScreen(this);
        }
    }

    private void onDecrease(boolean pCallback) {
        if (pCallback) {
            NetworkHandler.sendToServer(new AddSkillC2SPacket(new Pair<>(this.playerSkill, -1)));
            Minecraft.getInstance().popGuiLayer();
        } else {
            Minecraft.getInstance().displayGuiScreen(this);
        }
    }
}
