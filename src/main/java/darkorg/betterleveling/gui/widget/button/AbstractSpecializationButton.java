package darkorg.betterleveling.gui.widget.button;

import com.google.common.collect.ImmutableList;
import darkorg.betterleveling.impl.specialization.Specialization;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractSpecializationButton extends AbstractButton {
    protected final ImmutableList<Specialization> values;
    private final OnValueChange onValueChange;
    protected Specialization value;
    protected final OnTooltip onTooltip;
    private int index;

    public AbstractSpecializationButton(int pX, int pY, int pWidth, int pHeight, Specialization pValue, ImmutableList<Specialization> pValues, OnValueChange pOnValueChange) {
        this(pX, pY, pWidth, pHeight, pValue, pValues, pOnValueChange, (pGuiGraphics, pFont, pMouseX, pMouseY) -> {
        });
    }

    public AbstractSpecializationButton(int pX, int pY, int pWidth, int pHeight, Specialization pValue, ImmutableList<Specialization> pValues, OnValueChange pOnValueChange, OnTooltip pOnTooltip) {
        super(pX, pY, pWidth, pHeight, Component.empty());
        this.value = pValue;
        this.values = pValues;
        this.onValueChange = pOnValueChange;
        this.onTooltip = pOnTooltip;
        this.index = pValues.indexOf(pValue);
    }


    @Override
    public void onPress() {
        if (Screen.hasShiftDown()) {
            if (this.index == 0) {
                this.setValue(this.values.get(this.index = this.values.size() - 1));
            } else {
                this.setValue(this.values.get(--this.index));
            }
        } else {
            if (this.index == this.values.size() - 1) {
                this.setValue(this.values.get(this.index = 0));
            } else {
                this.setValue(this.values.get(++this.index));
            }
        }
        this.onValueChange.onValueChange(this.value);
    }

    private void setValue(Specialization pValue) {
        this.value = pValue;
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput pNarrationElementOutput) {

    }

    @OnlyIn(Dist.CLIENT)
    public interface OnValueChange {
        void onValueChange(Specialization pSpecialization);
    }

    @OnlyIn(Dist.CLIENT)
    public interface OnTooltip {
        void onTooltip(GuiGraphics pGuiGraphics, Font pFont, int pMouseX, int pMouseY);
    }
}
