package darkorg.betterleveling.event;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.IMachineCapability;
import darkorg.betterleveling.api.IPlayerCapability;
import darkorg.betterleveling.capability.MachineCapabilityStorage;
import darkorg.betterleveling.capability.PlayerCapabilityStorage;
import darkorg.betterleveling.data.ModBlockTagsProvider;
import darkorg.betterleveling.data.ModItemTagsProvider;
import darkorg.betterleveling.data.ModLanguageProvider;
import darkorg.betterleveling.impl.MachineCapability;
import darkorg.betterleveling.impl.PlayerCapability;
import darkorg.betterleveling.network.NetworkHandler;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IPlayerCapability.class, new PlayerCapabilityStorage(), PlayerCapability::new);
        CapabilityManager.INSTANCE.register(IMachineCapability.class, new MachineCapabilityStorage(), MachineCapability::new);
        event.enqueueWork(NetworkHandler::init);
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(dataGenerator, BetterLeveling.MOD_ID, existingFileHelper);
        ModItemTagsProvider itemTagsProvider = new ModItemTagsProvider(dataGenerator, blockTagsProvider, BetterLeveling.MOD_ID, existingFileHelper);
        ModLanguageProvider en_us = new ModLanguageProvider(dataGenerator, BetterLeveling.MOD_ID, "en_us");

        dataGenerator.addProvider(blockTagsProvider);
        dataGenerator.addProvider(itemTagsProvider);
        dataGenerator.addProvider(en_us);
    }
}
