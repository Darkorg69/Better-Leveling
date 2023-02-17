package darkorg.betterleveling.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import darkorg.betterleveling.api.IPlayerCapability;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.network.NetworkHandler;
import darkorg.betterleveling.network.chat.ModTranslatableContents;
import darkorg.betterleveling.network.packets.AddSkillC2SPacket;
import darkorg.betterleveling.util.RenderUtil;
import darkorg.betterleveling.util.SkillUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class SkillScreen extends Screen {
    private final ISkill skill;
    private final int imageWidth = 176;
    private final int imageHeight = 166;
    private final LocalPlayer localPlayer;
    private int leftPos;
    private int topPos;
    private int currentLevel;
    private boolean isMinLevel;
    private boolean isMaxLevel;
    private IPlayerCapability playerCapability;

    public SkillScreen(ISkill pSkill) {
        super(Component.empty());

        this.skill = pSkill;

        this.localPlayer = Minecraft.getInstance().player;
        if (localPlayer != null) {
            this.localPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(playerCapability -> {
                this.playerCapability = playerCapability;
                this.currentLevel = this.playerCapability.getLevel(this.localPlayer, this.skill);
                this.isMinLevel = SkillUtil.isMinLevel(this.skill, this.currentLevel);
                this.isMaxLevel = SkillUtil.isMaxLevel(this.skill, this.currentLevel);
            });
        }
    }

    @Override
    protected void init() {
        this.leftPos = (width - imageWidth) / 2;
        this.topPos = (height - imageHeight) / 2;

        ExtendedButton increaseButton = new ExtendedButton((this.width / 2) - 44, this.topPos + 92, 88, 24, MutableComponent.create(ModTranslatableContents.INCREASE), this::onIncrease);
        increaseButton.active = !this.isMaxLevel;
        addRenderableWidget(increaseButton);

        ExtendedButton decreaseButton = new ExtendedButton((this.width / 2) - 44, this.topPos + 126, 88, 24, MutableComponent.create(ModTranslatableContents.DECREASE), this::onDecrease);
        decreaseButton.active = !this.isMinLevel;
        addRenderableWidget(decreaseButton);
    }

    private void onIncrease(Button pButton) {
        confirm(MutableComponent.create(ModTranslatableContents.CONFIRM_INCREASE), 1);
    }

    private void onDecrease(Button pButton) {
        confirm(MutableComponent.create(ModTranslatableContents.CONFIRM_DECREASE), -1);
    }

    private void confirm(Component pComponent, int i) {
        Minecraft.getInstance().setScreen(new ConfirmScreen(pCallback -> {
            if (pCallback) {
                NetworkHandler.sendToServer(new AddSkillC2SPacket(Pair.of(this.skill, i)));
                Minecraft.getInstance().popGuiLayer();
            } else {
                Minecraft.getInstance().setScreen(this);
            }
        }, this.skill.getTranslation(), pComponent));
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);

        RenderUtil.setShaderTexture();
        this.blit(pPoseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        int pX = this.width / 2;
        drawCenteredString(pPoseStack, this.font, this.skill.getTranslation(), pX, this.topPos + 12, 16777215);
        drawCenteredString(pPoseStack, this.font, this.skill.getDescription(), pX, this.topPos + 24, 16777045);
        drawCenteredString(pPoseStack, this.font, RenderUtil.getBonusPerLevel(this.skill), pX, this.topPos + 36, 170);

        MutableComponent CURRENT_LEVEL = RenderUtil.getCurrentLevel(this.skill, this.currentLevel);

        if (this.isMaxLevel) {
            drawCenteredString(pPoseStack, this.font, MutableComponent.create(ModTranslatableContents.MAX_LEVEL), pX, this.topPos + 48, 16733525);
            drawCenteredString(pPoseStack, this.font, CURRENT_LEVEL, pX, this.topPos + 70, 11141120);
        } else {
            drawCenteredString(pPoseStack, this.font, RenderUtil.getSkillCost(this.skill, this.currentLevel), pX, this.topPos + 48, 5635925);
            drawCenteredString(pPoseStack, this.font, CURRENT_LEVEL, pX, this.topPos + 70, 16777215);
        }

        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }
}
