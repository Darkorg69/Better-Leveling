package darkorg.betterleveling.api;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public interface ITranslatable {
    String getName();

    String getTranslationId();

    default String getDescriptionId() {
        return this.getDescriptionId(0);
    }

    default String getDescriptionId(int pIndex) {
        return this.getTranslationId() + ".description." + pIndex;
    }

    default IFormattableTextComponent getTranslation() {
        return new TranslationTextComponent(getTranslationId());
    }

    default IFormattableTextComponent getDescription() {
        return new TranslationTextComponent(getDescriptionId(0));
    }

    default IFormattableTextComponent getDescription(int pIndex) {
        return new TranslationTextComponent(getDescriptionId(pIndex));
    }
}
