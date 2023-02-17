package darkorg.betterleveling.event;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.data.client.ModItemModelProvider;
import darkorg.betterleveling.data.client.ModLanguageProvider;
import darkorg.betterleveling.data.server.ModBlockTagsProvider;
import darkorg.betterleveling.data.server.ModGlobalLootModifierProvider;
import darkorg.betterleveling.data.server.ModItemTagsProvider;
import darkorg.betterleveling.data.server.ModRecipeProvider;
import darkorg.betterleveling.network.NetworkHandler;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
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
        generator.addProvider(en_us);
        generator.addProvider(itemModelProvider);
        ModRecipeProvider recipeProvider = new ModRecipeProvider(generator);
        ModGlobalLootModifierProvider lootModifierProvider = new ModGlobalLootModifierProvider(generator);
        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(generator, existingFileHelper);
        ModItemTagsProvider itemTagsProvider = new ModItemTagsProvider(generator, blockTagsProvider, existingFileHelper);
        generator.addProvider(recipeProvider);
        generator.addProvider(lootModifierProvider);
        generator.addProvider(blockTagsProvider);
        generator.addProvider(itemTagsProvider);
    }
}
