package darkorg.betterleveling.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.registry.SkillRegistry;
import darkorg.betterleveling.registry.SpecRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public class MaxPlayerCommand {
    public MaxPlayerCommand(CommandDispatcher<CommandSourceStack> pDispatcher) {
        pDispatcher.register(Commands.literal(BetterLeveling.MOD_ID).requires(pSource -> pSource.hasPermission(2))
                .then(Commands.literal("max")
                        .executes(pContext -> maxPlayer(pContext.getSource()))
                )
        );
    }

    private int maxPlayer(CommandSourceStack pSource) throws CommandSyntaxException {
        ServerPlayer serverPlayer = pSource.getPlayerOrException();

        serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(pCapability -> {
            SpecRegistry.getSpecRegistry().forEach(pSpecialization -> pCapability.setUnlocked(serverPlayer, pSpecialization, true));
            SkillRegistry.getSkillRegistry().forEach(pSkill -> pCapability.setLevel(serverPlayer, pSkill, pSkill.getMaxLevel()));
        });

        return 1;
    }
}
