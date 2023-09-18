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
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::init);
    }

    public static final CreativeModeTab BETTER_LEVELING = new CreativeModeTab(BetterLeveling.MOD_ID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return ModItems.RAW_DEBRIS.get().getDefaultInstance();
        }
    };

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        ModLanguageProvider en_us = new ModLanguageProvider(generator, "en_us");
        ModItemModelProvider itemModelProvider = new ModItemModelProvider(generator, existingFileHelper);
        generator.addProvider(event.includeClient(), en_us);
        generator.addProvider(event.includeClient(), itemModelProvider);
        ModRecipeProvider recipeProvider = new ModRecipeProvider(generator);
        ModGlobalLootModifierProvider lootModifierProvider = new ModGlobalLootModifierProvider(generator);
        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(generator, BetterLeveling.MOD_ID, existingFileHelper);
        ModItemTagsProvider itemTagsProvider = new ModItemTagsProvider(generator, blockTagsProvider, BetterLeveling.MOD_ID, existingFileHelper);
        generator.addProvider(event.includeServer(), recipeProvider);
        generator.addProvider(event.includeServer(), lootModifierProvider);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), itemTagsProvider);
    }
}
