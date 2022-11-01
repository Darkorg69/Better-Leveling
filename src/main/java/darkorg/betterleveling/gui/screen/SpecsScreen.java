package darkorg.betterleveling.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Pair;
import darkorg.betterleveling.api.IPlayerCapability;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.gui.widget.SkillButton;
import darkorg.betterleveling.gui.widget.SpecButton;
import darkorg.betterleveling.network.NetworkHandler;
import darkorg.betterleveling.network.packets.AddSpecC2SPacket;
import darkorg.betterleveling.util.CapabilityUtil;
import darkorg.betterleveling.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static darkorg.betterleveling.network.chat.ModTextComponents.*;

@OnlyIn(Dist.CLIENT)
public class SpecsScreen extends Screen {
    private int levelCost;
    private int leftPos, topPos;
    private ClientPlayerEntity localPlayer;
    private IPlayerCapability playerCapability;
    private ISpecialization specialization;
    private boolean specUnlocked, canUnlockSpec;
    private List<ISkill> skillsFromSpec;
    private final int imageWidth = 176;
    private final int imageHeight = 166;

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
            ExtendedButton unlockSpecButton = new ExtendedButton(this.leftPos + (imageWidth / 2) - 37, this.topPos + 98, 74, 17, UNLOCK_SPEC, pButton -> Minecraft.getInstance().displayGuiScreen(new ConfirmScreen(this::onCallback, this.specialization.getTranslation(), CONFIRM_UNLOCK)));
            unlockSpecButton.active = this.canUnlockSpec;
            addButton(unlockSpecButton);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(@Nonnull MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pMatrixStack);
        RenderUtil.setShaderTexture();
        this.blit(pMatrixStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        if (!this.specUnlocked) {
            drawCenteredString(pMatrixStack, this.font, SPEC_LOCKED, this.leftPos + (imageWidth / 2), this.topPos + 51, 16777215);
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
            Minecraft.getInstance().displayGuiScreen(this);
        }
    }

    private void onValueChange(ISpecialization pPlayerSpec) {
        reInitGui(pPlayerSpec);
        rebuildGui();
    }

    private void onSpecTooltip(MatrixStack pMatrixStack, int pMouseX, int pMouseY) {
        if (this.specUnlocked) {
            List<ITextComponent> unlocked = new ArrayList<>();

            unlocked.add(new TranslationTextComponent("").appendSibling(this.specialization.getTranslation()).appendString(" ").appendSibling(SPEC));

            this.renderWrappedToolTip(pMatrixStack, unlocked, pMouseX, pMouseY, this.font);
        } else {
            List<ITextComponent> locked = new ArrayList<>();

            locked.add(LOCKED);
            locked.add(new TranslationTextComponent("").appendSibling(this.specialization.getTranslation()));
            locked.add(new TranslationTextComponent("").appendSibling(UNLOCK_COST).appendString(" ").appendString(String.valueOf(this.specialization.getLevelCost())).appendString(" ").appendSibling(LEVELS));

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
                unlocked.add(MAX_LEVEL);
            } else {
                unlocked.add(new TranslationTextComponent("").appendSibling(CURRENT_LEVEL).appendString(" ").appendString(String.valueOf(skillLevel)).appendString("/").appendString(String.valueOf(playerSkill.getMaxLevel())));
            }

            this.renderWrappedToolTip(pMatrixStack, unlocked, pMouseX, pMouseY, this.font);
        } else {
            List<ITextComponent> locked = new ArrayList<>();

            locked.add(LOCKED);
            locked.add(playerSkill.getTranslation());

            Map<ISkill, Integer> prerequisitesMap = playerSkill.getPrerequisites();

            if (!prerequisitesMap.isEmpty()) {
                locked.add(REQUIREMENTS);

                prerequisitesMap.forEach((prerequisiteSkill, prerequisiteLevel) -> {
                    locked.add(new TranslationTextComponent("*").appendSibling(prerequisiteSkill.getTranslation()).appendString(" ").appendString(String.valueOf(prerequisiteLevel)));
                });
            }

            this.renderWrappedToolTip(pMatrixStack, locked, pMouseX, pMouseY, this.font);
        }
    }

    private void rebuildGui() {
        this.buttons.clear();
        this.children.clear();
        this.setListener(null);
        this.init();
    }
}
