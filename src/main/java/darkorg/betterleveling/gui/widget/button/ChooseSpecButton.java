package darkorg.betterleveling.gui.widget.button;

import com.mojang.blaze3d.vertex.PoseStack;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ChooseSpecButton extends AbstractSpecButton {
    public ChooseSpecButton(int pX, int pY, ISpecialization pValue, OnValueChange pOnValueChange) {
        super(pX, pY, 64, 64, pValue, pOnValueChange);
    }

    @Override
    public void renderButton(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderUtil.setShaderTextureButton();
        this.blit(pPoseStack, this.x, this.y, 176, 0, this.width, this.height);
        this.renderBg(pPoseStack, minecraft, pMouseX, pMouseY);
        minecraft.getItemRenderer().renderGuiItem(this.representativeItemStack, this.x + 24, this.y + 16);
        drawCenteredString(pPoseStack, minecraft.font, this.translation, this.x + 32, this.y - 10, 16777215);
        drawCenteredString(pPoseStack, minecraft.font, this.description, this.x + 32, this.y + width + 10, 16777215);
    }
}
