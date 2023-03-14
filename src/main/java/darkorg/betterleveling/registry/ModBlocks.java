package darkorg.betterleveling.registry;

import darkorg.betterleveling.BetterLeveling;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BetterLeveling.MOD_ID);

    public static final RegistryObject<Block> RAW_IRON_BLOCK = registerWithBlockItem("raw_iron_block", () -> new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<Block> RAW_GOLD_BLOCK = registerWithBlockItem("raw_gold_block", () -> new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.GOLD).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.METAL)), new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<Block> RAW_DEBRIS_BLOCK = registerWithBlockItem("raw_debris_block", () -> new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(50.0F, 1200.0F).sound(SoundType.ANCIENT_DEBRIS)), new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS));

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String pName, Supplier<T> pBlock) {
        return BLOCKS.register(pName, pBlock);
    }

    private static <T extends Block> RegistryObject<T> registerWithBlockItem(String pName, Supplier<T> pBlock, Item.Properties pProperties) {
        RegistryObject<T> toReturn = registerBlock(pName, pBlock);
        ModItems.registerBlockItem(pName, toReturn, pProperties);
        return toReturn;
    }
}
