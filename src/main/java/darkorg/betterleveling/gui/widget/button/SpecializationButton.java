package darkorg.betterleveling.gui.widget.button;

import com.google.common.collect.ImmutableList;
import darkorg.betterleveling.impl.specialization.Specialization;
import darkorg.betterleveling.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
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
    protected void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.blit(RenderUtil.BACKGROUND, this.getX(), this.getY(), this.isUnlocked ? 32 : 0, 166, this.width, this.height);

        pGuiGraphics.renderItem(this.value.getRepresentativeItemStack(), this.getX() + 8, this.getY() + 8);

        Font font = Minecraft.getInstance().font;
        pGuiGraphics.drawCenteredString(font, this.value.getTranslation(), this.getX() + 16, this.getY() - 10, 16777215);

        if (this.isHovered || this.isFocused()) {
            this.renderToolTip(pGuiGraphics, font, pMouseX, pMouseY);
        }
    }

    public void renderToolTip(GuiGraphics pGuiGraphics, Font pFont, int pMouseX, int pMouseY) {
        this.onTooltip.onTooltip(pGuiGraphics, pFont, pMouseX, pMouseY);
    }
}
