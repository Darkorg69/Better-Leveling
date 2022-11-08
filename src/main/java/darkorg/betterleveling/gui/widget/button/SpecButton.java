package darkorg.betterleveling.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class SpecButton extends AbstractSpecButton {
    private final boolean isUnlocked;
    private final OnTooltip onTooltip;

    public SpecButton(int pX, int pY, ISpecialization pValue, boolean pIsUnlocked, OnValueChange pOnValueChange, OnTooltip pOnTooltip) {
        super(pX, pY, 32, 32, pValue, pOnValueChange);
        this.isUnlocked = pIsUnlocked;
        this.onTooltip = pOnTooltip;
    }

    @Override
    public void renderButton(@Nonnull MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderUtil.setShaderTextureButton();
        if (!this.isUnlocked) {
            this.blit(pMatrixStack, this.x, this.y, 0, 166, this.width, this.height);
        } else {
            this.blit(pMatrixStack, this.x, this.y, 32, 166, this.width, this.height);
        }
        drawCenteredString(pMatrixStack, minecraft.font, this.value.getTranslation(), this.x + 16, this.y - 10, 16777215);
        if (this.isHovered() || this.isFocused()) {
            this.renderToolTip(pMatrixStack, pMouseX, pMouseY);
        }
        this.renderBg(pMatrixStack, minecraft, pMouseX, pMouseY);
        minecraft.getItemRenderer().renderGuiItem(this.representativeItemStack, x + 8, y + 8);
    }

    @Override
    public void renderToolTip(@Nonnull MatrixStack pMatrixStack, int pMouseX, int pMouseY) {
        this.onTooltip.onTooltip(pMatrixStack, pMouseX, pMouseY);
    }

    @OnlyIn(Dist.CLIENT)
    public interface OnTooltip {
        void onTooltip(MatrixStack pMatrixStack, int pMouseX, int pMouseY);
    }
}
