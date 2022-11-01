package darkorg.betterleveling.event;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.IPlayerCapability;
import darkorg.betterleveling.api.ITileCapability;
import darkorg.betterleveling.capability.PlayerCapabilityStorage;
import darkorg.betterleveling.capability.TileEntityCapabilityStorage;
import darkorg.betterleveling.data.ModGlobalLootModifierProvider;
import darkorg.betterleveling.data.ModLanguageProvider;
import darkorg.betterleveling.impl.PlayerCapability;
import darkorg.betterleveling.impl.TileCapability;
import darkorg.betterleveling.network.NetworkHandler;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IPlayerCapability.class, new PlayerCapabilityStorage(), PlayerCapability::new);
        CapabilityManager.INSTANCE.register(ITileCapability.class, new TileEntityCapabilityStorage(), TileCapability::new);
        event.enqueueWork(NetworkHandler::init);
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        dataGenerator.addProvider(new ModLanguageProvider(dataGenerator, BetterLeveling.MOD_ID, "en_us"));
        dataGenerator.addProvider(new ModGlobalLootModifierProvider(dataGenerator, BetterLeveling.MOD_ID));
    }
}
