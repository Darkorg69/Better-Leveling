package darkorg.betterleveling.event;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.gui.screen.ChooseSpecScreen;
import darkorg.betterleveling.gui.screen.SpecsScreen;
import darkorg.betterleveling.key.KeyMappings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID, value = Dist.CLIENT)
public class ClientForgeEvents {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KeyMappings.OPEN_GUI.consumeClick()) {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer localPlayer = minecraft.player;
            if (localPlayer != null) {
                localPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.hasUnlocked(localPlayer)) {
                        minecraft.setScreen(new SpecsScreen());
                    } else {
                        if (capability.canUnlock(localPlayer)) {
                            minecraft.setScreen(new ChooseSpecScreen());
                        }
                    }
                });
            }
        }
    }
}
