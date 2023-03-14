package darkorg.betterleveling.event;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.data.client.ModBlockStateProvider;
import darkorg.betterleveling.data.client.ModItemModelProvider;
import darkorg.betterleveling.data.client.ModLanguageProvider;
import darkorg.betterleveling.data.server.*;
import darkorg.betterleveling.network.NetworkHandler;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

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
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        ModLanguageProvider en_us = new ModLanguageProvider(generator, "en_us");
        ModItemModelProvider itemModelProvider = new ModItemModelProvider(generator, existingFileHelper);
        ModBlockStateProvider blockStateProvider = new ModBlockStateProvider(generator, existingFileHelper);

        ModRecipeProvider recipeProvider = new ModRecipeProvider(generator);
        ModLootTableProvider lootTableProvider = new ModLootTableProvider(generator);
        ModGlobalLootModifierProvider globalLootModifierProvider = new ModGlobalLootModifierProvider(generator);

        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(generator, existingFileHelper);
        ModItemTagsProvider itemTagsProvider = new ModItemTagsProvider(generator, blockTagsProvider, existingFileHelper);

        generator.addProvider(en_us);
        generator.addProvider(itemModelProvider);
        generator.addProvider(blockStateProvider);

        generator.addProvider(recipeProvider);
        generator.addProvider(lootTableProvider);
        generator.addProvider(globalLootModifierProvider);

        generator.addProvider(blockTagsProvider);
        generator.addProvider(itemTagsProvider);
    }
}
