package darkorg.betterleveling.event;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.gui.screen.ChooseSpecScreen;
import darkorg.betterleveling.gui.screen.SpecsScreen;
import darkorg.betterleveling.key.KeyMappings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientForgeEvents {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyMappings.OPEN_GUI.consumeClick()) {
            Minecraft minecraft = Minecraft.getInstance();
            ClientPlayerEntity clientPlayer = minecraft.player;

            if (clientPlayer != null) {
                clientPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.hasUnlocked(clientPlayer)) {
                        minecraft.setScreen(new SpecsScreen());
                    } else {
                        if (capability.canUnlock(clientPlayer)) {
                            minecraft.setScreen(new ChooseSpecScreen());
                        }
                    }
                });
            }
        }
    }
}
