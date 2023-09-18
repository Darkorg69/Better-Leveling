package darkorg.betterleveling.gui.widget.button;

import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.registry.SpecRegistry;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractSpecButton extends AbstractButton {
    private final OnValueChange onValueChange;
    private final List<ISpecialization> values = SpecRegistry.getSpecRegistry();
    protected ISpecialization value;
    protected Component translation;
    protected Component description;
    protected ItemStack representativeItemStack;
    private int index;

    public AbstractSpecButton(int pX, int pY, int pWidth, int pHeight, ISpecialization pValue, OnValueChange pOnValueChange) {
        super(pX, pY, pWidth, pHeight, Component.empty());
        this.index = values.indexOf(pValue);
        this.value = pValue;
        this.translation = pValue.getTranslation();
        this.description = pValue.getDescription();
        this.representativeItemStack = pValue.getRepresentativeItemStack();
        this.onValueChange = pOnValueChange;
    }

    @Override
    public void onPress() {
        this.cycleValues(Screen.hasShiftDown());
    }

    private void cycleValues(boolean hasShiftDown) {
        if (hasShiftDown) {
            if (onFirstValue()) {
                lastValue();
            } else {
                previousValue();
            }
        } else {
            if (onLastValue()) {
                firstValue();
            } else {
                nextValue();
            }
        }
        this.onValueChange.onValueChange(this.value);
    }

    private boolean onFirstValue() {
        return this.index == 0;
    }

    private void lastValue() {
        this.index = this.values.size() - 1;
        setValue(this.values.get(this.index));
    }

    private void previousValue() {
        setValue(this.values.get(--this.index));
    }

    private boolean onLastValue() {
        return this.index == this.values.size() - 1;
    }

    private void firstValue() {
        this.index = 0;
        setValue(this.values.get(this.index));
    }

    private void nextValue() {
        setValue(this.values.get(++this.index));
    }

    private void setValue(ISpecialization pValue) {
        this.value = pValue;
        this.translation = pValue.getTranslation();
        this.description = pValue.getDescription();
        this.representativeItemStack = pValue.getRepresentativeItemStack();
    }


    @OnlyIn(Dist.CLIENT)
    public interface OnValueChange {
        void onValueChange(ISpecialization pSpecialization);
    }
}
