package darkorg.betterleveling.api;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

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
        return Component.translatable(getTranslationId());
    }

    default MutableComponent getDescription() {
        return Component.translatable(getDescriptionId(0));
    }

    default MutableComponent getDescription(int pIndex) {
        return Component.translatable(getDescriptionId(pIndex));
    }
}
