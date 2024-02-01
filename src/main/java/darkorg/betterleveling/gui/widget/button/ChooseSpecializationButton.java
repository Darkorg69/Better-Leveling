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
public class ChooseSpecializationButton extends AbstractSpecializationButton {
    public ChooseSpecializationButton(int pX, int pY, Specialization pValue, ImmutableList<Specialization> pValues, OnValueChange pOnValueChange) {
        super(pX - 32, pY - 32, 64, 64, pValue, pValues, pOnValueChange);
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.blit(RenderUtil.BACKGROUND, this.getX(), this.getY(), 176, 0, this.width, this.height);

        pGuiGraphics.renderItem(this.value.getRepresentativeItemStack(), this.getX() + 24, this.getY() + 16);

        Font font = Minecraft.getInstance().font;
        pGuiGraphics.drawCenteredString(font, this.value.getTranslation(), this.getX() + 32, this.getY() - 10, 16777215);
        pGuiGraphics.drawCenteredString(font, this.value.getDescription(), this.getX() + 32, this.getY() + this.width + 6, 16777215);
        pGuiGraphics.drawCenteredString(font, this.value.getDescription(1), this.getX() + 32, this.getY() + this.width + 20, 16777215);
    }
}
