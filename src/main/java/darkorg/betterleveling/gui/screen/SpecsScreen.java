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
import darkorg.betterleveling.util.RegistryUtil;
import darkorg.betterleveling.util.RenderUtil;
import darkorg.betterleveling.util.SkillUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class SpecsScreen extends Screen {
    private final int imageWidth = 176;
    private final int imageHeight = 166;
    private int leftPos;
    private int topPos;
    private int levelCost;
    private boolean specUnlocked;
    private boolean canUnlockSpec;
    private LocalPlayer localPlayer;
    private List<ISkill> skillsFromSpec;
    private ISpecialization specialization;
    private IPlayerCapability playerCapability;

    public SpecsScreen() {
        super(ModComponents.EMPTY);

        this.localPlayer = Minecraft.getInstance().player;
        if (this.localPlayer != null) {
            localPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                this.playerCapability = capability;
                this.specialization = this.playerCapability.getFirstSpecialization(this.localPlayer);
                this.levelCost = this.specialization.getLevelCost();
                this.specUnlocked = this.playerCapability.getUnlocked(this.localPlayer, this.specialization);
                this.canUnlockSpec = this.localPlayer.experienceLevel >= this.levelCost;
                this.skillsFromSpec = RegistryUtil.getSkillsFromSpec(this.specialization);
            });
        }
    }

    @Override
    protected void init() {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        SpecButton specButton = new SpecButton(this.leftPos + 72, this.topPos + 16, this.specialization, this.specUnlocked, this::onValueChange, this::onSpecTooltip);
        addRenderableWidget(specButton);

        int skillRow = 0;
        int skillColumn = 0;

        for (int i = 0; i < this.skillsFromSpec.size(); i++) {
            if (i != 0 && i % 3 == 0) {
                skillRow++;
                skillColumn = 0;
            }
            SkillButton skillButton = new SkillButton(this.leftPos + 22 + skillColumn * 50, this.topPos + 65 + skillRow * 51, this.skillsFromSpec.get(i), this::onSkillTooltip);
            skillButton.active = this.playerCapability.hasUnlocked(this.localPlayer, this.skillsFromSpec.get(i));
            addRenderableWidget(skillButton);
            skillColumn++;
        }

        if (!this.specUnlocked) {
            ExtendedButton unlockSpecButton = new ExtendedButton(this.leftPos + this.imageWidth / 2 - 37, this.topPos + 98, 74, 17, ModComponents.UNLOCK_SPEC, this::onPress);
            unlockSpecButton.active = this.canUnlockSpec;
            addRenderableWidget(unlockSpecButton);
        }
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);

        RenderUtil.setShaderTexture();
        this.blit(pPoseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        if (!this.specUnlocked) {
            drawCenteredString(pPoseStack, this.font, ModComponents.SPEC_LOCKED, this.leftPos + (imageWidth / 2), this.topPos + 51, 16733525);
        } else {
            drawCenteredString(pPoseStack, this.font, RenderUtil.getAvailableXP(this.localPlayer), this.leftPos + (imageWidth / 2), this.topPos + 51, 16777215);
        }
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    private void onValueChange(ISpecialization specialization) {
        this.specialization = specialization;
        this.levelCost = this.specialization.getLevelCost();

        this.skillsFromSpec = RegistryUtil.getSkillsFromSpec(this.specialization);
        this.localPlayer = Minecraft.getInstance().player;
        if (this.localPlayer != null) {
            this.canUnlockSpec = this.localPlayer.experienceLevel >= this.levelCost;
            localPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(playerCapability -> {
                this.playerCapability = playerCapability;
                this.specUnlocked = this.playerCapability.getUnlocked(this.localPlayer, this.specialization);
            });
        }
        refreshScreen();
    }

    private void refreshScreen() {
        this.clearWidgets();
        this.setFocused(null);
        this.init();
    }

    private void onSpecTooltip(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        if (this.specUnlocked) {
            List<Component> unlocked = new ArrayList<>();
            unlocked.add(new TranslatableComponent("").append(this.specialization.getTranslation()).append(" ").append(ModComponents.SPEC));
            this.renderComponentTooltip(pPoseStack, unlocked, pMouseX, pMouseY, this.font);
        } else {
            List<Component> locked = new ArrayList<>();
            locked.add(ModComponents.LOCKED.withStyle(ChatFormatting.RED));
            locked.add(this.specialization.getTranslation());
            locked.add(new TranslatableComponent("").append(ModComponents.UNLOCK_COST).append(String.valueOf(this.specialization.getLevelCost())).append(" ").append(ModComponents.LEVELS));
            this.renderComponentTooltip(pPoseStack, locked, pMouseX, pMouseY, this.font);
        }
    }

    private void onSkillTooltip(SkillButton pSkillButton, PoseStack pPoseStack, int pMouseX, int pMouseY) {
        ISkill skill = pSkillButton.getSkill();
        List<Component> tooltip = new ArrayList<>();

        TranslatableComponent TRANSLATION = skill.getTranslation();
        TranslatableComponent DESCRIPTION = skill.getDescription();
        MutableComponent COST_PER_LEVEL = RenderUtil.getCostPerLevel(skill);
        MutableComponent BONUS_PER_LEVEL = RenderUtil.getBonusPerLevel(skill);

        tooltip.add(TRANSLATION);

        if (this.playerCapability.hasUnlocked(this.localPlayer, skill)) {
            int currentLevel = this.playerCapability.getLevel(this.localPlayer, skill);

            MutableComponent CURRENT_LEVEL = RenderUtil.getCurrentLevel(skill, currentLevel);
            MutableComponent CURRENT_BONUS = RenderUtil.getCurrentBonus(skill, currentLevel);
            MutableComponent CURRENT_COST = RenderUtil.getCurrentCost(skill, currentLevel);

            if (SkillUtil.isMaxLevel(skill, currentLevel)) {
                tooltip.add(ModComponents.MAX_LEVEL.withStyle(ChatFormatting.RED));
                tooltip.add(CURRENT_BONUS.withStyle(ChatFormatting.DARK_RED));
            } else {
                tooltip.add(CURRENT_LEVEL.withStyle(ChatFormatting.GRAY));
                tooltip.add(CURRENT_COST.withStyle(ChatFormatting.GREEN));
                tooltip.add(CURRENT_BONUS.withStyle(ChatFormatting.BLUE));
            }
        } else {
            tooltip.add(ModComponents.LOCKED.withStyle(ChatFormatting.RED));
            Map<ISkill, Integer> prerequisitesMap = skill.getPrerequisites();
            if (!prerequisitesMap.isEmpty()) {
                tooltip.add(ModComponents.PREREQUISITES.withStyle(ChatFormatting.DARK_RED));
                prerequisitesMap.forEach((prerequisiteSkill, prerequisiteLevel) -> tooltip.add(RenderUtil.getPrerequisite(prerequisiteSkill, prerequisiteLevel).withStyle(ChatFormatting.GRAY)));
            }
        }

        if (hasShiftDown()) {
            tooltip.add(ModComponents.EMPTY);
            tooltip.add(ModComponents.ADDITIONAL_INFORMATION.withStyle(ChatFormatting.AQUA));
            tooltip.add(DESCRIPTION.withStyle(ChatFormatting.YELLOW));
            tooltip.add(COST_PER_LEVEL.withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(BONUS_PER_LEVEL.withStyle(ChatFormatting.DARK_BLUE));
        } else {
            tooltip.add(ModComponents.HOLD_SHIFT.withStyle(ChatFormatting.AQUA));
        }

        this.renderComponentTooltip(pPoseStack, tooltip, pMouseX, pMouseY, this.font);
    }

    private void onPress(Button pUnlockSpecButton) {
        Minecraft.getInstance().setScreen(new ConfirmScreen(this::accept, this.specialization.getTranslation(), ModComponents.CONFIRM_UNLOCK));
    }

    private void accept(boolean pCallback) {
        if (pCallback) {
            NetworkHandler.sendToServer(new AddSpecC2SPacket(Pair.of(this.specialization, true)));
            Minecraft.getInstance().popGuiLayer();
        } else {
            Minecraft.getInstance().setScreen(this);
        }
    }
}
