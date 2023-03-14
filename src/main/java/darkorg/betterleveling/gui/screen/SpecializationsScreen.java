package darkorg.betterleveling.gui.screen;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.gui.widget.button.SkillButton;
import darkorg.betterleveling.gui.widget.button.SpecializationButton;
import darkorg.betterleveling.impl.PlayerCapability;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.impl.specialization.Specialization;
import darkorg.betterleveling.key.KeyMappings;
import darkorg.betterleveling.network.NetworkHandler;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.network.packets.specialization.RequestSpecializationsScreenC2SPacket;
import darkorg.betterleveling.network.packets.specialization.UnlockSpecializationC2SPacket;
import darkorg.betterleveling.registry.Skills;
import darkorg.betterleveling.registry.Specializations;
import darkorg.betterleveling.util.PlayerUtil;
import darkorg.betterleveling.util.RenderUtil;
import darkorg.betterleveling.util.SkillUtil;
import darkorg.betterleveling.util.SpecializationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;

public class SpecializationsScreen extends Screen {
    private static final ImmutableList<Specialization> SPECIALIZATIONS = Specializations.getAll();
    private final int imageWidth = 176;
    private final int imageHeight = 166;
    private int leftPos;
    private int topPos;
    private ClientPlayerEntity localPlayer;
    private PlayerCapability capability;
    private Specialization specialization;
    private boolean isUnlocked;
    private boolean canUnlock;
    private ImmutableList<Specialization> unlockedSpecializations;
    private ImmutableList<Skill> skills;
    private int availableExperience;
    private ITextComponent availableXP;

    public SpecializationsScreen() {
        super(ModComponents.EMPTY);
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
                this.capability = pCapability;
                this.specialization = this.capability.getSpecialization(this.localPlayer);
                this.unlockedSpecializations = SpecializationUtil.getUnlocked(this.capability, localPlayer);

                if (this.specialization == null) {
                    if (SpecializationUtil.hasUnlocked(this.capability, this.localPlayer)) {
                        this.specialization = this.unlockedSpecializations.get(0);
                        NetworkHandler.sendToServer(new RequestSpecializationsScreenC2SPacket(this.specialization));
                    } else {
                        Minecraft.getInstance().setScreen(new ChooseSpecializationScreen());
                    }
                } else {
                    this.skills = Skills.getAllFrom(this.specialization);
                    this.isUnlocked = this.capability.getUnlocked(this.localPlayer, this.specialization);
                    this.canUnlock = PlayerUtil.canUnlockSpecialization(this.localPlayer, this.specialization);

                    SpecializationButton specializationButton = new SpecializationButton(this.leftPos + 72, this.topPos + 16, this.specialization, this.isUnlocked, SPECIALIZATIONS, this::onValueChange, this::onSpecializationTooltip);
                    addButton(specializationButton);

                    if (!this.isUnlocked) {
                        ExtendedButton unlockSpecButton = new ExtendedButton(this.leftPos + this.imageWidth / 2 - 37, this.topPos + 98, 74, 17, ModComponents.UNLOCK, this::onUnlock);
                        unlockSpecButton.active = this.canUnlock;
                        addButton(unlockSpecButton);
                    }

                    this.skills.forEach(pSkill -> {
                        SkillButton skillButton = new SkillButton(this.leftPos - 28, this.topPos + 14, pSkill, this::onSkillTooltip);
                        addButton(skillButton);
                    });

                    this.availableExperience = this.capability.getAvailableExperience(this.localPlayer);
                    this.availableXP = RenderUtil.getAvailableXP(this.availableExperience);
                }
            });
        }
    }

    @Override
    public void render(MatrixStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);

        RenderUtil.setShaderTexture();
        this.blit(pPoseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        if (this.isUnlocked) {
            drawCenteredString(pPoseStack, this.font, this.availableXP, this.leftPos + this.imageWidth / 2, this.topPos + 51, 16777215);
        } else {
            drawCenteredString(pPoseStack, this.font, ModComponents.SPEC_IS_LOCKED, this.leftPos + this.imageWidth / 2, this.topPos + 51, 16733525);
        }

        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    private void onValueChange(Specialization pSpecialization) {
        NetworkHandler.sendToServer(new RequestSpecializationsScreenC2SPacket(pSpecialization));
    }

    private void onSpecializationTooltip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
        this.renderComponentTooltip(pPoseStack, SpecializationUtil.getTooltip(this.specialization, this.isUnlocked, this.canUnlock), pMouseX, pMouseY);
    }

    private void onUnlock(Button pButton) {
        Minecraft.getInstance().setScreen(new ConfirmScreen(this::onCallback, this.specialization.getTranslation(), ModComponents.CONFIRM_UNLOCK));
    }

    private void onCallback(boolean pConfirm) {
        if (pConfirm) {
            NetworkHandler.sendToServer(new UnlockSpecializationC2SPacket(this.specialization));
        }
        NetworkHandler.sendToServer(new RequestSpecializationsScreenC2SPacket(this.specialization));
    }

    private void onSkillTooltip(SkillButton pButton, MatrixStack pPoseStack, int pMouseX, int pMouseY) {
        Skill skill = pButton.getSkill();
        int currentLevel = this.capability.getLevel(this.localPlayer, skill);
        boolean canIncrease = PlayerUtil.canIncreaseSkill(this.localPlayer, skill, skill.isMaxLevel(currentLevel), this.availableExperience, currentLevel);
        boolean hasUnlocked = SkillUtil.hasUnlocked(this.capability, this.localPlayer, skill);
        this.renderComponentTooltip(pPoseStack, SkillUtil.getTooltip(skill, hasUnlocked, currentLevel, canIncrease), pMouseX, pMouseY);
    }
}
