package darkorg.betterleveling.gui.widget.button;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import darkorg.betterleveling.impl.specialization.Specialization;
import darkorg.betterleveling.network.chat.ModComponents;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractSpecializationButton extends AbstractButton {
    protected final ImmutableList<Specialization> values;
    private final OnValueChange onValueChange;
    protected Specialization value;
    protected OnTooltip onTooltip;
    private int index;

    public AbstractSpecializationButton(int pX, int pY, int pWidth, int pHeight, Specialization pValue, ImmutableList<Specialization> pValues, OnValueChange pOnValueChange) {
        this(pX, pY, pWidth, pHeight, pValue, pValues, pOnValueChange, (pMatrixStack, pMouseX, pMouseY) -> {
        });
    }

    public AbstractSpecializationButton(int pX, int pY, int pWidth, int pHeight, Specialization pValue, ImmutableList<Specialization> pValues, OnValueChange pOnValueChange, OnTooltip pOnTooltip) {
        super(pX, pY, pWidth, pHeight, ModComponents.EMPTY);

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

    @OnlyIn(Dist.CLIENT)
    public interface OnValueChange {
        void onValueChange(Specialization pSpecialization);
    }

    @OnlyIn(Dist.CLIENT)
    public interface OnTooltip {
        void onTooltip(MatrixStack pPoseStack, int pMouseX, int pMouseY);
    }
}
