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
import darkorg.betterleveling.util.RegistryUtil;
import darkorg.betterleveling.util.RenderUtil;
import darkorg.betterleveling.util.SkillUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.IFormattableTextComponent;
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
    private int leftPos;
    private int topPos;
    private int levelCost;
    private boolean specUnlocked;
    private boolean canUnlockSpec;
    private ClientPlayerEntity localPlayer;
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
        addButton(specButton);

        int skillRow = 0;
        int skillColumn = 0;

        for (int i = 0; i < this.skillsFromSpec.size(); i++) {
            if (i != 0 && i % 3 == 0) {
                skillRow++;
                skillColumn = 0;
            }
            SkillButton skillButton = new SkillButton(this.leftPos + 22 + skillColumn * 50, this.topPos + 65 + skillRow * 51, this.skillsFromSpec.get(i), this::onSkillTooltip);
            skillButton.active = this.playerCapability.hasUnlocked(this.localPlayer, this.skillsFromSpec.get(i));
            addButton(skillButton);
            skillColumn++;
        }

        if (!this.specUnlocked) {
            ExtendedButton unlockSpecButton = new ExtendedButton(this.leftPos + this.imageWidth / 2 - 37, this.topPos + 98, 74, 17, ModComponents.UNLOCK_SPEC, this::onPress);
            unlockSpecButton.active = this.canUnlockSpec;
            addButton(unlockSpecButton);
        }
    }

    @Override
    public void render(MatrixStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
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
        this.buttons.clear();
        this.children.clear();
        this.setFocused(null);
        this.init();
    }

    private void onSpecTooltip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
        if (this.specUnlocked) {
            List<ITextComponent> unlocked = new ArrayList<>();
            unlocked.add(new TranslationTextComponent("").append(this.specialization.getTranslation()).append(" ").append(ModComponents.SPEC));
            this.renderComponentTooltip(pPoseStack, unlocked, pMouseX, pMouseY);
        } else {
            List<ITextComponent> locked = new ArrayList<>();
            locked.add(ModComponents.LOCKED.withStyle(TextFormatting.RED));
            locked.add(this.specialization.getTranslation());
            locked.add(new TranslationTextComponent("").append(ModComponents.UNLOCK_COST).append(String.valueOf(this.specialization.getLevelCost())).append(" ").append(ModComponents.LEVELS));
            this.renderComponentTooltip(pPoseStack, locked, pMouseX, pMouseY);
        }
    }

    private void onSkillTooltip(SkillButton pSkillButton, MatrixStack pPoseStack, int pMouseX, int pMouseY) {
        ISkill skill = pSkillButton.getSkill();
        List<ITextComponent> tooltip = new ArrayList<>();

        TranslationTextComponent TRANSLATION = skill.getTranslation();
        TranslationTextComponent DESCRIPTION = skill.getDescription();
        IFormattableTextComponent COST_PER_LEVEL = RenderUtil.getCostPerLevel(skill);
        IFormattableTextComponent BONUS_PER_LEVEL = RenderUtil.getBonusPerLevel(skill);

        tooltip.add(TRANSLATION);

        if (this.playerCapability.hasUnlocked(this.localPlayer, skill)) {
            int currentLevel = this.playerCapability.getLevel(this.localPlayer, skill);

            IFormattableTextComponent CURRENT_LEVEL = RenderUtil.getCurrentLevel(skill, currentLevel);
            IFormattableTextComponent CURRENT_BONUS = RenderUtil.getCurrentBonus(skill, currentLevel);
            IFormattableTextComponent CURRENT_COST = RenderUtil.getCurrentCost(skill, currentLevel);

            if (SkillUtil.isMaxLevel(skill, currentLevel)) {
                tooltip.add(ModComponents.MAX_LEVEL.withStyle(TextFormatting.RED));
                tooltip.add(CURRENT_BONUS.withStyle(TextFormatting.DARK_RED));
            } else {
                tooltip.add(CURRENT_LEVEL.withStyle(TextFormatting.GRAY));
                tooltip.add(CURRENT_COST.withStyle(TextFormatting.GREEN));
                tooltip.add(CURRENT_BONUS.withStyle(TextFormatting.BLUE));
            }
        } else {
            tooltip.add(ModComponents.LOCKED.withStyle(TextFormatting.RED));
            Map<ISkill, Integer> prerequisitesMap = skill.getPrerequisites();
            if (!prerequisitesMap.isEmpty()) {
                tooltip.add(ModComponents.PREREQUISITES.withStyle(TextFormatting.DARK_RED));
                prerequisitesMap.forEach((prerequisiteSkill, prerequisiteLevel) -> tooltip.add(RenderUtil.getPrerequisite(prerequisiteSkill, prerequisiteLevel).withStyle(TextFormatting.GRAY)));
            }
        }

        if (hasShiftDown()) {
            tooltip.add(ModComponents.EMPTY);
            tooltip.add(ModComponents.ADDITIONAL_INFORMATION.withStyle(TextFormatting.AQUA));
            tooltip.add(DESCRIPTION.withStyle(TextFormatting.YELLOW));
            tooltip.add(COST_PER_LEVEL.withStyle(TextFormatting.DARK_GREEN));
            tooltip.add(BONUS_PER_LEVEL.withStyle(TextFormatting.DARK_BLUE));
        } else {
            tooltip.add(ModComponents.HOLD_SHIFT.withStyle(TextFormatting.AQUA));
        }

        this.renderComponentTooltip(pPoseStack, tooltip, pMouseX, pMouseY);
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
