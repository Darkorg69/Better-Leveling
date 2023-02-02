package darkorg.betterleveling.key;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyMappings {
    public static final String KEY_CATEGORY_BETTERLEVELING = "key.category.betterleveling";
    public static final String KEY_OPEN_GUI = "key.betterleveling.open_gui";
    public static final KeyBinding OPEN_GUI = new KeyBinding(KEY_OPEN_GUI, KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, 66, KEY_CATEGORY_BETTERLEVELING);

    public static void init() {
        ClientRegistry.registerKeyBinding(OPEN_GUI);
    }
}

