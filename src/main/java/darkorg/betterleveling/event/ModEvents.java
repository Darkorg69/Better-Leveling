package darkorg.betterleveling.event;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.data.client.ModItemModelProvider;
import darkorg.betterleveling.data.client.ModLanguageProvider;
import darkorg.betterleveling.data.server.ModBlockTagsProvider;
import darkorg.betterleveling.data.server.ModGlobalLootModifierProvider;
import darkorg.betterleveling.data.server.ModItemTagsProvider;
import darkorg.betterleveling.data.server.ModRecipeProvider;
import darkorg.betterleveling.network.NetworkHandler;
import darkorg.betterleveling.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::init);
    }

    @SubscribeEvent
    public static void onCreativeModeTabBuildContents(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.RAW_DEBRIS);
        }
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ModLanguageProvider en_us = new ModLanguageProvider(packOutput, "en_us");
        ModItemModelProvider itemModelProvider = new ModItemModelProvider(packOutput, existingFileHelper);
        generator.addProvider(event.includeClient(), en_us);
        generator.addProvider(event.includeClient(), itemModelProvider);
        ModRecipeProvider recipeProvider = new ModRecipeProvider(packOutput);
        ModGlobalLootModifierProvider lootModifierProvider = new ModGlobalLootModifierProvider(packOutput);
        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);
        ModItemTagsProvider itemTagsProvider = new ModItemTagsProvider(packOutput, lookupProvider, blockTagsProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), recipeProvider);
        generator.addProvider(event.includeServer(), lootModifierProvider);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), itemTagsProvider);
    }
}
