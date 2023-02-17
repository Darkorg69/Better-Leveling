package darkorg.betterleveling.key;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public class KeyMappings {
    public static final String KEY_CATEGORY_BETTERLEVELING = "key.category.betterleveling";
    public static final String KEY_OPEN_GUI = "key.betterleveling.open_gui";
    public static final KeyMapping OPEN_GUI = new KeyMapping(KEY_OPEN_GUI, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, 66, KEY_CATEGORY_BETTERLEVELING);
}

