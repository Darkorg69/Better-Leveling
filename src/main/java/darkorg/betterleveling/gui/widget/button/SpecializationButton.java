package darkorg.betterleveling.gui.widget.button;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import darkorg.betterleveling.impl.specialization.Specialization;
import darkorg.betterleveling.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpecializationButton extends AbstractSpecializationButton {
    private final boolean isUnlocked;

    public SpecializationButton(int pX, int pY, Specialization pValue, boolean pIsUnlocked, ImmutableList<Specialization> pValues, OnValueChange pOnValueChange, OnTooltip pOnTooltip) {
        super(pX, pY, 32, 32, pValue, pValues, pOnValueChange, pOnTooltip);
        this.isUnlocked = pIsUnlocked;
    }

    @Override
    public void renderButton(MatrixStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        Minecraft minecraft = Minecraft.getInstance();

        RenderUtil.setShaderTextureButton();

        this.blit(pPoseStack, this.x, this.y, this.isUnlocked ? 32 : 0, 166, this.width, this.height);

        minecraft.getItemRenderer().renderGuiItem(this.value.getRepresentativeItemStack(), this.x + 8, this.y + 8);

        drawCenteredString(pPoseStack, minecraft.font, this.value.getTranslation(), this.x + 16, this.y - 10, 16777215);

        if (this.isHovered() || this.isFocused()) {
            this.renderToolTip(pPoseStack, pMouseX, pMouseY);
        }

        this.renderBg(pPoseStack, minecraft, pMouseX, pMouseY);
    }

    @Override
    public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
        this.onTooltip.onTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    public void onPress() {
        super.onPress();
    }
}
