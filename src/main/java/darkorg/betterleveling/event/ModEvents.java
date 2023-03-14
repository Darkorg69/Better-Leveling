package darkorg.betterleveling.event;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.BlockEntityCapabilityStorage;
import darkorg.betterleveling.capability.PlayerCapabilityStorage;
import darkorg.betterleveling.data.client.ModBlockStateProvider;
import darkorg.betterleveling.data.client.ModItemModelProvider;
import darkorg.betterleveling.data.client.ModLanguageProvider;
import darkorg.betterleveling.data.server.*;
import darkorg.betterleveling.impl.BlockEntityCapability;
import darkorg.betterleveling.impl.PlayerCapability;
import darkorg.betterleveling.network.NetworkHandler;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void onModConfigLoading(ModConfig.Loading event) {
        BetterLeveling.LOGGER.debug("Loaded betterleveling config file {}", event.getConfig().getFileName());
    }

    @SubscribeEvent
    public static void onModConfigReloading(ModConfig.Reloading event) {
        BetterLeveling.LOGGER.debug("Better Leveling config just got changed on the file system! {}", event.getConfig().getFileName());
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::init);
        CapabilityManager.INSTANCE.register(PlayerCapability.class, new PlayerCapabilityStorage(), PlayerCapability::new);
        CapabilityManager.INSTANCE.register(BlockEntityCapability.class, new BlockEntityCapabilityStorage(), BlockEntityCapability::new);
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
