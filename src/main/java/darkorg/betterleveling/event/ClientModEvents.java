package darkorg.betterleveling.event;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.key.KeyMappings;
import darkorg.betterleveling.registry.ModBlocks;
import darkorg.betterleveling.registry.ModItems;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(RegisterKeyMappingsEvent event) {
        event.register(KeyMappings.OPEN_GUI);
    }

    @SubscribeEvent
    public static void onCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tabKey = event.getTabKey();

        if (tabKey == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.RAW_DEBRIS_BLOCK);
        } else if (tabKey == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.RAW_DEBRIS);
        }
    }
}