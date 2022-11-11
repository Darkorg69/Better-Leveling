package darkorg.betterleveling.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import darkorg.betterleveling.api.IPlayerCapability;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.gui.widget.button.SkillButton;
import darkorg.betterleveling.gui.widget.button.SpecButton;
import darkorg.betterleveling.network.NetworkHandler;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.network.packets.AddSpecC2SPacket;
import darkorg.betterleveling.util.CapabilityUtil;
import darkorg.betterleveling.util.RenderUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class SpecsScreen extends Screen {
    private final int imageWidth = 176;
    private final int imageHeight = 166;
    private int levelCost;
    private int leftPos, topPos;
    private LocalPlayer localPlayer;
    private IPlayerCapability playerCapability;
    private ISpecialization specialization;
    private boolean specUnlocked, canUnlockSpec;
    private List<ISkill> skillsFromSpec;

    public SpecsScreen() {
        super(new TranslatableComponent(""));
        initGui();
    }

    @Override
    protected void init() {
        this.leftPos = (width - imageWidth) / 2;
        this.topPos = (height - imageHeight) / 2;

        SpecButton specButton = new SpecButton(this.leftPos + 72, this.topPos + 16, this.specialization, this.specUnlocked, this::onValueChange, this::onSpecTooltip);
        addRenderableWidget(specButton);

        int skillRow = 0;
        int skillColumn = 0;

        for (int i = 0; i < this.skillsFromSpec.size(); i++) {
            if (i != 0 && i % 3 == 0) {
                skillRow++;
                skillColumn = 0;
            }
            SkillButton skillButton = new SkillButton(this.leftPos + 22 + (skillColumn * 50), this.topPos + 65 + (skillRow * 51), this.skillsFromSpec.get(i), this::onSkillTooltip);
            skillButton.active = this.playerCapability.isUnlocked(this.localPlayer, this.skillsFromSpec.get(i));
            addRenderableWidget(skillButton);
            skillColumn++;
        }

        if (!this.specUnlocked) {
            ExtendedButton unlockSpecButton = new ExtendedButton(this.leftPos + (imageWidth / 2) - 37, this.topPos + 98, 74, 17, ModComponents.UNLOCK_SPEC, pButton -> Minecraft.getInstance().setScreen(new ConfirmScreen(this::onCallback, this.specialization.getTranslation(), ModComponents.CONFIRM_UNLOCK)));
            unlockSpecButton.active = this.canUnlockSpec;
            addRenderableWidget(unlockSpecButton);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        RenderUtil.setShaderTexture();
        this.blit(pPoseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        if (!this.specUnlocked) {
            drawCenteredString(pPoseStack, this.font, ModComponents.SPEC_LOCKED, this.leftPos + (imageWidth / 2), this.topPos + 51, 16777215);
        }
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    private void initGui() {
        this.localPlayer = Minecraft.getInstance().player;

        if (this.localPlayer != null) {
            localPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                this.playerCapability = capability;
                this.specialization = this.playerCapability.getFirstUnlocked(this.localPlayer);
                this.levelCost = this.specialization.getLevelCost();
                this.specUnlocked = this.playerCapability.getUnlocked(this.localPlayer, this.specialization);
                this.canUnlockSpec = this.localPlayer.experienceLevel >= this.levelCost;
                this.skillsFromSpec = CapabilityUtil.getSkillsFromSpec(this.specialization);
            });
        }
    }

    private void reInitGui(ISpecialization pPlayerSpec) {
        this.specialization = pPlayerSpec;

        this.localPlayer = Minecraft.getInstance().player;

        if (this.localPlayer != null) {
            localPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(pPlayerCapability -> {
                this.playerCapability = pPlayerCapability;
                this.levelCost = this.specialization.getLevelCost();
                this.canUnlockSpec = this.localPlayer.experienceLevel >= this.levelCost;
                this.specUnlocked = this.playerCapability.getUnlocked(this.localPlayer, this.specialization);
                this.skillsFromSpec = CapabilityUtil.getSkillsFromSpec(this.specialization);
            });
        }
    }

    private void onCallback(boolean pCallback) {
        if (pCallback) {
            NetworkHandler.sendToServer(new AddSpecC2SPacket(new Pair<>(this.specialization, true)));
            Minecraft.getInstance().popGuiLayer();
        } else {
            Minecraft.getInstance().setScreen(this);
        }
    }

    private void onValueChange(ISpecialization pPlayerSpec) {
        reInitGui(pPlayerSpec);
        rebuildGui();
    }

    private void onSpecTooltip(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        if (this.specUnlocked) {
            List<Component> unlocked = new ArrayList<>();

            unlocked.add(new TranslatableComponent("").append(this.specialization.getTranslation()).append(" ").append(ModComponents.SPEC));

            this.renderComponentTooltip(pPoseStack, unlocked, pMouseX, pMouseY, this.font);
        } else {
            List<Component> locked = new ArrayList<>();

            locked.add(ModComponents.LOCKED.withStyle(ChatFormatting.DARK_RED));
            locked.add(new TranslatableComponent("").append(this.specialization.getTranslation()));
            locked.add(new TranslatableComponent("").append(ModComponents.UNLOCK_COST).append(" ").append(String.valueOf(this.specialization.getLevelCost())).append(" ").append(ModComponents.LEVELS));

            this.renderComponentTooltip(pPoseStack, locked, pMouseX, pMouseY, this.font);
        }
    }

    private void onSkillTooltip(SkillButton pSkillButton, PoseStack pPoseStack, int pMouseX, int pMouseY) {
        ISkill playerSkill = pSkillButton.getPlayerSkill();

        if (this.playerCapability.isUnlocked(this.localPlayer, playerSkill)) {
            List<Component> unlocked = new ArrayList<>();

            unlocked.add(playerSkill.getTranslation());

            int skillLevel = this.playerCapability.getLevel(this.localPlayer, playerSkill);

            if (playerSkill.isMaxLevel(skillLevel)) {
                unlocked.add(ModComponents.MAX_LEVEL.withStyle(ChatFormatting.DARK_RED));
            } else {
                unlocked.add(new TranslatableComponent("").append(ModComponents.CURRENT_LEVEL).append(" ").append(String.valueOf(skillLevel)).append("/").append(String.valueOf(playerSkill.getMaxLevel())));
            }
            this.renderComponentTooltip(pPoseStack, unlocked, pMouseX, pMouseY, this.font);
        } else {
            List<Component> locked = new ArrayList<>();

            locked.add(ModComponents.LOCKED.withStyle(ChatFormatting.DARK_RED));
            locked.add(playerSkill.getTranslation());

            Map<ISkill, Integer> prerequisitesMap = playerSkill.getPrerequisites();

            if (!prerequisitesMap.isEmpty()) {
                locked.add(ModComponents.REQUIREMENTS);

                prerequisitesMap.forEach((prerequisiteSkill, prerequisiteLevel) -> {
                    locked.add(new TranslatableComponent("*").append(prerequisiteSkill.getTranslation()).append(" ").append(String.valueOf(prerequisiteLevel)).withStyle(ChatFormatting.GOLD));
                });
            }
            this.renderComponentTooltip(pPoseStack, locked, pMouseX, pMouseY, this.font);
        }
    }

    private void rebuildGui() {
        this.clearWidgets();
        this.setFocused(null);
        this.init();
    }
}
