package darkorg.betterleveling.registry;

import darkorg.betterleveling.BetterLeveling;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BetterLeveling.MOD_ID);

    public static final RegistryObject<Item> RAW_DEBRIS = ITEMS.register("raw_debris", () -> new Item(new Item.Properties().fireResistant()));

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private static <T extends Item> void registerItem(String pName, Supplier<T> pItem) {
        ITEMS.register(pName, pItem);
    }

    public static <T extends Block> void registerBlockItem(String pName, RegistryObject<T> pBlock, Item.Properties pProperties) {
        registerItem(pName, () -> new BlockItem(pBlock.get(), pProperties));
    }
}
