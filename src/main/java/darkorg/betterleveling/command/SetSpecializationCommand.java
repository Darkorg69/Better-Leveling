package darkorg.betterleveling.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.util.RegistryUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;

import static com.mojang.brigadier.arguments.BoolArgumentType.bool;
import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static net.minecraft.command.Commands.argument;
import static net.minecraft.command.Commands.literal;

public class SetSpecializationCommand {
    public SetSpecializationCommand(CommandDispatcher<CommandSource> pDispatcher) {
        pDispatcher.register(literal(BetterLeveling.MOD_ID).requires(source -> source.hasPermission(2)).then(literal("set").then(argument("spec", string()).then(argument("unlocked", bool()).executes(context -> setSpecialization(context.getSource(), getString(context, "spec"), getBool(context, "unlocked")))))));
    }

    private int setSpecialization(CommandSource pSource, String pSpecialization, boolean pUnlocked) throws CommandSyntaxException {
        ServerPlayerEntity serverPlayer = pSource.getPlayerOrException();

        if (!serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).isPresent()) {
            pSource.sendFailure(ModComponents.CAPABILITY_NOT_FOUND);
        }

        ISpecialization specialization = RegistryUtil.getSpecFromName(pSpecialization);

        if (specialization == null) {
            pSource.sendFailure(ModComponents.SPEC_NOT_FOUND);
        }

        serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> capability.setUnlocked(serverPlayer, specialization, pUnlocked));

        return 1;
    }
}
