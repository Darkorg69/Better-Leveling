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
public class SpecializationButton extends AbstractSpecializationButton {
    private final boolean isUnlocked;

    public SpecializationButton(int pX, int pY, Specialization pValue, boolean pIsUnlocked, ImmutableList<Specialization> pValues, OnValueChange pOnValueChange, OnTooltip pOnTooltip) {
        super(pX, pY, 32, 32, pValue, pValues, pOnValueChange, pOnTooltip);
        this.isUnlocked = pIsUnlocked;
    }

    @Override
    public void renderWidget(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        Minecraft minecraft = Minecraft.getInstance();

        RenderUtil.setShaderTextureButton();

        blit(pPoseStack, this.getX(), this.getY(), this.isUnlocked ? 32 : 0, 166, this.width, this.height);

        minecraft.getItemRenderer().renderGuiItem(pPoseStack, this.value.getRepresentativeItemStack(), this.getX() + 8, this.getY() + 8);

        drawCenteredString(pPoseStack, minecraft.font, this.value.getTranslation(), this.getX() + 16, this.getY() - 10, 16777215);

        if (this.isHovered || this.isFocused()) {
            this.renderToolTip(pPoseStack, pMouseX, pMouseY);
        }

        //this.renderBg(pPoseStack, minecraft, pMouseX, pMouseY);
    }

    public void renderToolTip(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        this.onTooltip.onTooltip(pPoseStack, pMouseX, pMouseY);
    }
}
