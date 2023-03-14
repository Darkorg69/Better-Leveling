package darkorg.betterleveling.api;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;

public interface ITranslatable {
    String getName();

    String getTranslationId();

    default String getDescriptionId() {
        return this.getDescriptionId(0);
    }

    default String getDescriptionId(int pIndex) {
        return this.getTranslationId() + ".description." + pIndex;
    }

    default MutableComponent getTranslation() {
        return new TranslatableComponent(getTranslationId());
    }

    default MutableComponent getDescription() {
        return new TranslatableComponent(getDescriptionId(0));
    }

    default MutableComponent getDescription(int pIndex) {
        return new TranslatableComponent(getDescriptionId(pIndex));
    }
}
