package darkorg.betterleveling.gui.widget.button;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import darkorg.betterleveling.impl.specialization.Specialization;
import darkorg.betterleveling.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ChooseSpecializationButton extends AbstractSpecializationButton {
    public ChooseSpecializationButton(int pX, int pY, Specialization pValue, ImmutableList<Specialization> pValues, OnValueChange pOnValueChange) {
        super(pX - 32, pY - 32, 64, 64, pValue, pValues, pOnValueChange);
    }

    @Override
    public void renderWidget(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderUtil.setShaderTextureButton();
        blit(pPoseStack, this.getX(), this.getY(), 176, 0, this.width, this.height);
        //this.renderBg(pPoseStack, minecraft, pMouseX, pMouseY);
        minecraft.getItemRenderer().renderGuiItem(pPoseStack, this.value.getRepresentativeItemStack(), this.getX() + 24, this.getY() + 16);
        drawCenteredString(pPoseStack, minecraft.font, this.value.getTranslation(), this.getX() + 32, this.getY() - 10, 16777215);
        drawCenteredString(pPoseStack, minecraft.font, this.value.getDescription(), this.getX() + 32, this.getY() + this.width + 6, 16777215);
        drawCenteredString(pPoseStack, minecraft.font, this.value.getDescription(1), this.getX() + 32, this.getY() + this.width + 20, 16777215);
    }
}
