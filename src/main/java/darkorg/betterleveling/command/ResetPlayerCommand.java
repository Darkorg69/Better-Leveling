package darkorg.betterleveling.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.util.CapabilityUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public class ResetPlayerCommand {
    public ResetPlayerCommand(CommandDispatcher<CommandSourceStack> pDispatcher) {
        pDispatcher.register(Commands.literal(BetterLeveling.MOD_ID).requires(pSource -> pSource.hasPermission(2))
                .then(Commands.literal("reset")
                        .executes(pContext -> resetPlayer(pContext.getSource()))
                )
        );
    }

    private int resetPlayer(CommandSourceStack pSource) throws CommandSyntaxException {
        ServerPlayer serverPlayer = pSource.getPlayerOrException();

        if (!serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).isPresent()) {
            pSource.sendFailure(ModComponents.CAPABILITY_NOT_FOUND);
            return 1;
        }

        serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> CapabilityUtil.reset(capability, serverPlayer));

        return 1;
    }
}

