package darkorg.betterleveling.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class ChooseSpecButton extends AbstractSpecButton {

    public ChooseSpecButton(int pX, int pY, ISpecialization pValue, OnValueChange pOnValueChange) {
        super(pX, pY, 64, 64, pValue, pOnValueChange);
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderUtil.setShaderTextureButton();
        this.blit(pMatrixStack, this.x, this.y, 176, 0, this.width, this.height);
        this.renderBg(pMatrixStack, minecraft, pMouseX, pMouseY);
        minecraft.getItemRenderer().renderItemAndEffectIntoGUI(this.representativeItemStack, x + 24, y + 16);
        drawCenteredString(pMatrixStack, minecraft.fontRenderer, this.translation, x + 32, y - 10, 16777215);
        drawCenteredString(pMatrixStack, minecraft.fontRenderer, this.description, x + 32, y + width + 10, 16777215);
    }
}
