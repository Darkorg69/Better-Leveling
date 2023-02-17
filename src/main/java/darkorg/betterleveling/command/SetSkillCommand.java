package darkorg.betterleveling.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.network.chat.ModTranslatableContents;
import darkorg.betterleveling.registry.SpecRegistry;
import darkorg.betterleveling.util.RegistryUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;

public class SetSkillCommand {
    public SetSkillCommand(CommandDispatcher<CommandSourceStack> pDispatcher) {
        pDispatcher.register(Commands.literal(BetterLeveling.MOD_ID).requires(pSource -> pSource.hasPermission(2))
                .then(Commands.literal("set")
                        .then(Commands.literal("skill")
                                .then(Commands.argument("name", StringArgumentType.string())
                                        .suggests((pContext, pBuilder) -> SharedSuggestionProvider.suggest(SpecRegistry.getSpecNameMap().keySet(), pBuilder))
                                        .then(Commands.argument("level", IntegerArgumentType.integer(0))
                                                .executes(pContext -> setSkill(pContext.getSource(), StringArgumentType.getString(pContext, "spec"), IntegerArgumentType.getInteger(pContext, "level")))
                                        )
                                )
                        )
                )
        );
    }

    private int setSkill(CommandSourceStack pSource, String pSkill, int pLevel) throws CommandSyntaxException {
        ServerPlayer serverPlayer = pSource.getPlayerOrException();

        if (!serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).isPresent()) {
            pSource.sendFailure(MutableComponent.create(ModTranslatableContents.CAPABILITY_NOT_FOUND));
        }

        ISkill skill = RegistryUtil.getSkillFromName(pSkill);
        if (skill == null) {
            pSource.sendFailure(MutableComponent.create(ModTranslatableContents.SKILL_NOT_FOUND));
        }

        serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(pCapability -> pCapability.setLevel(serverPlayer, skill, pLevel));

        return 1;
    }
}
