package darkorg.betterleveling.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.impl.specialization.Specialization;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.registry.Specializations;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.Arrays;

public class SetSpecializationCommand {
    public SetSpecializationCommand(CommandDispatcher<CommandSource> pDispatcher) {
        pDispatcher.register(Commands.literal(BetterLeveling.MOD_ID).requires(pSource -> pSource.hasPermission(2))
                .then(Commands.literal("set")
                        .then(Commands.literal("specialization")
                                .then(Commands.argument("name", StringArgumentType.string())
                                        .suggests((pContext, pBuilder) -> ISuggestionProvider.suggest(Specializations.getAllNames(), pBuilder))
                                        .then(Commands.argument("unlocked", BoolArgumentType.bool())
                                                .suggests((pContext, pBuilder) -> ISuggestionProvider.suggest(Arrays.asList("true", "false"), pBuilder))
                                                .executes(pContext -> setSpecialization(pContext.getSource(), StringArgumentType.getString(pContext, "name"), BoolArgumentType.getBool(pContext, "unlocked")))
                                        )
                                )
                        )
                )
        );
    }

    private int setSpecialization(CommandSource pSource, String pName, boolean pUnlocked) throws CommandSyntaxException {
        ServerPlayerEntity serverPlayer = pSource.getPlayerOrException();

        if (!serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).isPresent()) {
            pSource.sendFailure(ModComponents.CAPABILITY_NOT_FOUND);
            return 1;
        }

        Specialization specialization = Specializations.getFrom(pName);
        if (specialization == null) {
            pSource.sendFailure(ModComponents.SPEC_NOT_FOUND);
            return 1;
        }

        serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(pCapability -> {
            pCapability.setUnlocked(serverPlayer, specialization, pUnlocked);
        });

        return 1;
    }
}
