package darkorg.betterleveling.event;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.data.client.ModBlockStateProvider;
import darkorg.betterleveling.data.client.ModItemModelProvider;
import darkorg.betterleveling.data.client.ModLanguageProvider;
import darkorg.betterleveling.data.server.*;
import darkorg.betterleveling.network.NetworkHandler;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void onModConfigLoading(ModConfigEvent.Loading event) {
        BetterLeveling.LOGGER.debug("Loaded BetterLeveling config file {}", event.getConfig().getFileName());
    }

    @SubscribeEvent
    public static void onModConfigReloading(ModConfigEvent.Reloading event) {
        BetterLeveling.LOGGER.debug("BetterLeveling config just got changed on the file system! {}", event.getConfig().getFileName());
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::init);
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ModLanguageProvider en_us = new ModLanguageProvider(packOutput);
        ModItemModelProvider itemModelProvider = new ModItemModelProvider(packOutput, existingFileHelper);
        ModBlockStateProvider blockStateProvider = new ModBlockStateProvider(packOutput, existingFileHelper);

        generator.addProvider(event.includeClient(), en_us);
        generator.addProvider(event.includeClient(), itemModelProvider);
        generator.addProvider(event.includeClient(), blockStateProvider);

        ModRecipeProvider recipeProvider = new ModRecipeProvider(packOutput);
        ModLootTableProvider lootTableProvider = new ModLootTableProvider(packOutput);
        ModGlobalLootModifierProvider globalLootModifierProvider = new ModGlobalLootModifierProvider(packOutput);
        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);
        ModItemTagsProvider itemTagsProvider = new ModItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper);

        generator.addProvider(event.includeServer(), recipeProvider);
        generator.addProvider(event.includeServer(), lootTableProvider);
        generator.addProvider(event.includeServer(), globalLootModifierProvider);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), itemTagsProvider);
    }
}
