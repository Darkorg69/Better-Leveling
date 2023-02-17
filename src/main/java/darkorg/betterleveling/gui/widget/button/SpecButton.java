package darkorg.betterleveling.gui.widget.button;

import com.mojang.blaze3d.vertex.PoseStack;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

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
    public void renderButton(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderUtil.setShaderTextureButton();
        if (!this.isUnlocked) {
            this.blit(pPoseStack, this.x, this.y, 0, 166, this.width, this.height);
        } else {
            this.blit(pPoseStack, this.x, this.y, 32, 166, this.width, this.height);
        }
        drawCenteredString(pPoseStack, minecraft.font, this.value.getTranslation(), this.x + 16, this.y - 10, 16777215);
        if (isHoveredOrFocused()) {
            this.renderToolTip(pPoseStack, pMouseX, pMouseY);
        }
        this.renderBg(pPoseStack, minecraft, pMouseX, pMouseY);
        minecraft.getItemRenderer().renderGuiItem(this.representativeItemStack, this.x + 8, this.y + 8);
    }

    public void renderToolTip(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        this.onTooltip.onTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @OnlyIn(Dist.CLIENT)
    public interface OnTooltip {
        void onTooltip(PoseStack pPoseStack, int pMouseX, int pMouseY);
    }
}
