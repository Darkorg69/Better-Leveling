package darkorg.betterleveling.gui.widget.button;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import darkorg.betterleveling.impl.specialization.Specialization;
import darkorg.betterleveling.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChooseSpecializationButton extends AbstractSpecializationButton {
    public ChooseSpecializationButton(int pX, int pY, Specialization pValue, ImmutableList<Specialization> pValues, OnValueChange pOnValueChange) {
        super(pX - 32, pY - 32, 64, 64, pValue, pValues, pOnValueChange);
    }

    @Override
    public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderUtil.setShaderTextureButton();
        this.blit(pPoseStack, this.x, this.y, 176, 0, this.width, this.height);
        this.renderBg(pPoseStack, minecraft, pMouseX, pMouseY);
        minecraft.getItemRenderer().renderGuiItem(this.value.getRepresentativeItemStack(), this.x + 24, this.y + 16);
        drawCenteredString(pPoseStack, minecraft.font, this.value.getTranslation(), this.x + 32, this.y - 10, 16777215);
        drawCenteredString(pPoseStack, minecraft.font, this.value.getDescription(), this.x + 32, this.y + this.width + 6, 16777215);
        drawCenteredString(pPoseStack, minecraft.font, this.value.getDescription(1), this.x + 32, this.y + this.width + 20, 16777215);
    }
}
