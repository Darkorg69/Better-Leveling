package darkorg.betterleveling.registry;

import darkorg.betterleveling.BetterLeveling;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BetterLeveling.MOD_ID);
    public static final RegistryObject<Item> RAW_DEBRIS = ITEMS.register("raw_debris", () -> new Item(new Item.Properties().fireResistant().tab(CreativeModeTab.TAB_MATERIALS)));

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
