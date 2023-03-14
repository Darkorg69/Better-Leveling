package darkorg.betterleveling.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.key.KeyMappings;
import darkorg.betterleveling.network.NetworkHandler;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.network.packets.skill.DecreaseSkillC2SPacket;
import darkorg.betterleveling.network.packets.skill.IncreaseSkillC2SPacket;
import darkorg.betterleveling.network.packets.skill.RequestSkillScreenUpdateC2SPacket;
import darkorg.betterleveling.network.packets.specialization.RequestSpecializationsScreenC2SPacket;
import darkorg.betterleveling.util.PlayerUtil;
import darkorg.betterleveling.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;

@OnlyIn(Dist.CLIENT)
public class SkillScreen extends Screen {
    private final Skill skill;
    private final ITextComponent translation;
    private final ITextComponent description;
    private final ITextComponent bonusPerLevel;
    private final int imageWidth = 176;
    private final int imageHeight = 166;
    private int leftPos;
    private int topPos;
    private ClientPlayerEntity localPlayer;
    private int availableExperience;
    private int level;
    private boolean canIncrease;
    private boolean canDecrease;
    private ITextComponent availableXP;
    private ITextComponent currentLevel;
    private IFormattableTextComponent skillCost;
    private boolean isMaxLevel;

    public SkillScreen(Skill pSkill) {
        super(ModComponents.EMPTY);
        this.skill = pSkill;
        this.translation = this.skill.getTranslation();
        this.description = this.skill.getDescription();
        this.bonusPerLevel = this.skill.getBonusPerLevel();
    }

    @Override
    public void onClose() {
        NetworkHandler.sendToServer(new RequestSpecializationsScreenC2SPacket(this.skill.getProperties().getParentSpecialization()));
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == KeyMappings.OPEN_GUI.getKey().getValue()) {
            this.onClose();
            return true;
        }

        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    protected void init() {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        this.localPlayer = Minecraft.getInstance().player;

        if (this.localPlayer != null) {
            this.localPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(pCapability -> {
                this.level = pCapability.getLevel(this.localPlayer, this.skill);
                this.isMaxLevel = this.skill.isMaxLevel(this.level);
                this.availableExperience = pCapability.getAvailableExperience(this.localPlayer);

                ExtendedButton increaseButton = new ExtendedButton(this.width / 2 - 44, this.topPos + 96, 88, 24, ModComponents.INCREASE, this::onIncrease);
                this.canIncrease = PlayerUtil.canIncreaseSkill(this.localPlayer, this.skill, this.isMaxLevel, this.availableExperience, this.level);
                increaseButton.active = this.canIncrease;
                addButton(increaseButton);

                ExtendedButton decreaseButton = new ExtendedButton(this.width / 2 - 44, this.topPos + 126, 88, 24, ModComponents.DECREASE, this::onDecrease);
                this.canDecrease = PlayerUtil.canDecreaseSkill(this.skill, this.level);
                decreaseButton.active = this.canDecrease;
                addButton(decreaseButton);

                this.availableXP = RenderUtil.getAvailableXP(this.availableExperience);
                this.currentLevel = RenderUtil.getCurrentLevel(this.skill, this.level);
                this.skillCost = RenderUtil.getSkillCost(this.skill, this.level);
            });
        }
    }

    @Override
    public void render(MatrixStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);

        RenderUtil.setShaderTexture();
        this.blit(pPoseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        drawCenteredString(pPoseStack, this.font, this.translation, this.width / 2, this.topPos + 12, 16777215);
        drawCenteredString(pPoseStack, this.font, this.description, this.width / 2, this.topPos + 24, 16777045);
        drawCenteredString(pPoseStack, this.font, this.bonusPerLevel, this.width / 2, this.topPos + 36, 5592575);
        drawCenteredString(pPoseStack, this.font, this.availableXP, this.width / 2, this.topPos + 48, 16777215);

        if (this.isMaxLevel) {
            drawCenteredString(pPoseStack, this.font, ModComponents.MAX_LEVEL, this.width / 2, this.topPos + 70, 16733525);
        } else {
            drawCenteredString(pPoseStack, this.font, this.skillCost, this.width / 2, this.topPos + 70, this.canIncrease ? 5635925 : 16733525);
            drawCenteredString(pPoseStack, this.font, this.currentLevel, this.width / 2, this.topPos + 82, 16777215);
        }

        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    private void onIncrease(Button button) {
        Minecraft.getInstance().setScreen(new ConfirmScreen(this::onIncreaseCallback, this.translation, ModComponents.CONFIRM_INCREASE));
    }

    private void onIncreaseCallback(boolean pCallback) {
        if (pCallback) {
            NetworkHandler.sendToServer(new IncreaseSkillC2SPacket(this.skill));
        }
        NetworkHandler.sendToServer(new RequestSkillScreenUpdateC2SPacket(this.skill));
    }

    private void onDecrease(Button button) {
        Minecraft.getInstance().setScreen(new ConfirmScreen(this::onDecreaseCallback, this.translation, ModComponents.CONFIRM_DECREASE));
    }

    private void onDecreaseCallback(boolean pCallback) {
        if (pCallback) {
            NetworkHandler.sendToServer(new DecreaseSkillC2SPacket(this.skill));
        }
        NetworkHandler.sendToServer(new RequestSkillScreenUpdateC2SPacket(this.skill));
    }
}
