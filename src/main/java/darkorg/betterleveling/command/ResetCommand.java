package darkorg.betterleveling.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.registry.SkillRegistry;
import darkorg.betterleveling.registry.SpecRegistry;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;

import static net.minecraft.command.Commands.literal;

public class ResetCommand {
    public ResetCommand(CommandDispatcher<CommandSource> pDispatcher) {
        pDispatcher.register(
                literal(BetterLeveling.MOD_ID)
                        .requires(pSource -> pSource.hasPermission(2))
                        .then(
                                literal("reset")
                                        .executes(context -> resetPlayer(context.getSource()))
                        )
        );
    }

    private int resetPlayer(CommandSource pSource) throws CommandSyntaxException {
        ServerPlayerEntity serverPlayer = pSource.getPlayerOrException();

        serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
            SpecRegistry.getSpecRegistry().forEach(specialization -> capability.setUnlocked(serverPlayer, specialization, false));
            SkillRegistry.getSkillRegistry().forEach(skill -> capability.setLevel(serverPlayer, skill, skill.getMinLevel()));
        });

        return 1;
    }
}
