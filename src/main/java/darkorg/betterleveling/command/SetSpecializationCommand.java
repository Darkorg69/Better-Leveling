package darkorg.betterleveling.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.network.chat.ModTranslatableContents;
import darkorg.betterleveling.registry.SpecRegistry;
import darkorg.betterleveling.util.RegistryUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;

import java.util.Arrays;

public class SetSpecializationCommand {
    public SetSpecializationCommand(CommandDispatcher<CommandSourceStack> pDispatcher) {
        pDispatcher.register(Commands.literal(BetterLeveling.MOD_ID).requires(pSource -> pSource.hasPermission(2))
                .then(Commands.literal("set")
                        .then(Commands.literal("specialization")
                                .then(Commands.argument("name", StringArgumentType.string())
                                        .suggests((pContext, pBuilder) -> SharedSuggestionProvider.suggest(SpecRegistry.getSpecNameMap().keySet(), pBuilder))
                                        .then(Commands.argument("unlocked", BoolArgumentType.bool())
                                                .suggests((pContext, pBuilder) -> SharedSuggestionProvider.suggest(Arrays.asList("true", "false"), pBuilder))
                                                .executes(pContext -> setSpecialization(pContext.getSource(), StringArgumentType.getString(pContext, "name"), BoolArgumentType.getBool(pContext, "unlocked")))
                                        )
                                )
                        )
                )
        );
    }

    private int setSpecialization(CommandSourceStack pSource, String pName, boolean pUnlocked) throws CommandSyntaxException {
        ServerPlayer serverPlayer = pSource.getPlayerOrException();

        if (!serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).isPresent()) {
            pSource.sendFailure(MutableComponent.create(ModTranslatableContents.CAPABILITY_NOT_FOUND));
        }

        ISpecialization specialization = RegistryUtil.getSpecFromName(pName);

        if (specialization == null) {
            pSource.sendFailure(MutableComponent.create(ModTranslatableContents.SPEC_NOT_FOUND));
        }

        serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(pCapability -> pCapability.setUnlocked(serverPlayer, specialization, pUnlocked));

        return 1;
    }
}
