package darkorg.betterleveling.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class SpecsScreen extends Screen {
    private final int imageWidth = 176;
    private final int imageHeight = 166;
    private int levelCost;
    private int leftPos, topPos;
    private ClientPlayerEntity localPlayer;
    private IPlayerCapability playerCapability;
    private ISpecialization specialization;
    private boolean specUnlocked, canUnlockSpec;
    private List<ISkill> skillsFromSpec;

    public SpecsScreen() {
        super(new TranslationTextComponent(""));
        initGui();
    }

    @Override
    protected void init() {
        this.leftPos = (width - imageWidth) / 2;
        this.topPos = (height - imageHeight) / 2;

        SpecButton specButton = new SpecButton(this.leftPos + 72, this.topPos + 16, this.specialization, this.specUnlocked, this::onValueChange, this::onSpecTooltip);
        addButton(specButton);

        int skillRow = 0;
        int skillColumn = 0;

        for (int i = 0; i < this.skillsFromSpec.size(); i++) {
            if (i != 0 && i % 3 == 0) {
                skillRow++;
                skillColumn = 0;
            }
            SkillButton skillButton = new SkillButton(this.leftPos + 22 + (skillColumn * 50), this.topPos + 65 + (skillRow * 51), this.skillsFromSpec.get(i), this::onSkillTooltip);
            skillButton.active = this.playerCapability.isUnlocked(this.localPlayer, this.skillsFromSpec.get(i));
            addButton(skillButton);
            skillColumn++;
        }

        if (!this.specUnlocked) {
            ExtendedButton unlockSpecButton = new ExtendedButton(this.leftPos + (imageWidth / 2) - 37, this.topPos + 98, 74, 17, ModComponents.UNLOCK_SPEC, pButton -> Minecraft.getInstance().setScreen(new ConfirmScreen(this::onCallback, this.specialization.getTranslation(), ModComponents.CONFIRM_UNLOCK)));
            unlockSpecButton.active = this.canUnlockSpec;
            addButton(unlockSpecButton);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pMatrixStack);
        RenderUtil.setShaderTexture();
        this.blit(pMatrixStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        if (!this.specUnlocked) {
            drawCenteredString(pMatrixStack, this.font, ModComponents.SPEC_LOCKED, this.leftPos + (imageWidth / 2), this.topPos + 51, 16777215);
        }
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTick);
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

    private void onSpecTooltip(MatrixStack pMatrixStack, int pMouseX, int pMouseY) {
        if (this.specUnlocked) {
            List<ITextComponent> unlocked = new ArrayList<>();

            unlocked.add(new TranslationTextComponent("").append(this.specialization.getTranslation()).append(" ").append(ModComponents.SPEC));

            this.renderWrappedToolTip(pMatrixStack, unlocked, pMouseX, pMouseY, this.font);
        } else {
            List<ITextComponent> locked = new ArrayList<>();

            locked.add(ModComponents.LOCKED.withStyle(TextFormatting.DARK_RED));
            locked.add(new TranslationTextComponent("").append(this.specialization.getTranslation()));
            locked.add(new TranslationTextComponent("").append(ModComponents.UNLOCK_COST).append(" ").append(String.valueOf(this.specialization.getLevelCost())).append(" ").append(ModComponents.LEVELS));

            this.renderWrappedToolTip(pMatrixStack, locked, pMouseX, pMouseY, this.font);
        }
    }

    private void onSkillTooltip(SkillButton pSkillButton, MatrixStack pMatrixStack, int pMouseX, int pMouseY) {
        ISkill playerSkill = pSkillButton.getPlayerSkill();

        if (this.playerCapability.isUnlocked(this.localPlayer, playerSkill)) {
            List<ITextComponent> unlocked = new ArrayList<>();

            unlocked.add(playerSkill.getTranslation());

            int skillLevel = this.playerCapability.getLevel(this.localPlayer, playerSkill);

            if (playerSkill.isMaxLevel(skillLevel)) {
                unlocked.add(ModComponents.MAX_LEVEL.withStyle(TextFormatting.DARK_RED));
            } else {
                unlocked.add(new TranslationTextComponent("").append(ModComponents.CURRENT_LEVEL).append(" ").append(String.valueOf(skillLevel)).append("/").append(String.valueOf(playerSkill.getMaxLevel())));
            }
            this.renderWrappedToolTip(pMatrixStack, unlocked, pMouseX, pMouseY, this.font);
        } else {
            List<ITextComponent> locked = new ArrayList<>();

            locked.add(ModComponents.LOCKED.withStyle(TextFormatting.DARK_RED));
            locked.add(playerSkill.getTranslation());

            Map<ISkill, Integer> prerequisitesMap = playerSkill.getPrerequisites();

            if (!prerequisitesMap.isEmpty()) {
                locked.add(ModComponents.REQUIREMENTS);

                prerequisitesMap.forEach((prerequisiteSkill, prerequisiteLevel) -> {
                    locked.add(new TranslationTextComponent("*").append(prerequisiteSkill.getTranslation()).append(" ").append(String.valueOf(prerequisiteLevel)).withStyle(TextFormatting.GOLD));
                });
            }
            this.renderWrappedToolTip(pMatrixStack, locked, pMouseX, pMouseY, this.font);
        }
    }

    private void rebuildGui() {
        this.buttons.clear();
        this.children.clear();
        this.setFocused(null);
        this.init();
    }
}
