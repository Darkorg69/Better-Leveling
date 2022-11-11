package darkorg.betterleveling.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.util.CapabilityUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

import static com.mojang.brigadier.arguments.BoolArgumentType.bool;
import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class SetSpecializationCommand {
    public SetSpecializationCommand(CommandDispatcher<CommandSourceStack> pDispatcher) {
        pDispatcher.register(
                literal(BetterLeveling.MOD_ID)
                        .requires(source -> source.hasPermission(2))
                        .then(literal("set")
                                .then(argument("spec", string())
                                        .then(argument("unlocked", bool())
                                                .executes(context -> setSpecialization(context.getSource(), getString(context, "spec"), getBool(context, "unlocked")))
                                        )
                                )
                        )
        );
    }

    private int setSpecialization(CommandSourceStack pSource, String pSpecialization, boolean pUnlocked) throws CommandSyntaxException {
        ServerPlayer serverPlayer = pSource.getPlayerOrException();

        if (!serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).isPresent()) {
            pSource.sendFailure(ModComponents.FAILURE_CAPABILITY);
        }

        ISpecialization specialization = CapabilityUtil.getSpecFromName(pSpecialization);

        if (specialization == null) {
            pSource.sendFailure(ModComponents.FAILURE_SPECIALIZATION);
        }

        serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
            capability.setUnlocked(serverPlayer, specialization, pUnlocked);
        });

        return 1;
    }
}
