package darkorg.betterleveling.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.registry.Skills;
import darkorg.betterleveling.registry.Specializations;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.server.level.ServerPlayer;

public class SetSkillCommand {
    public SetSkillCommand(CommandDispatcher<CommandSourceStack> pDispatcher) {
        pDispatcher.register(Commands.literal(BetterLeveling.MOD_ID).requires(pSource -> pSource.hasPermission(2))
                .then(Commands.literal("set")
                        .then(Commands.literal("skill")
                                .then(Commands.argument("spec", StringArgumentType.string())
                                        .suggests((pContext, pBuilder) -> SharedSuggestionProvider.suggest(Specializations.getAllNames(), pBuilder))
                                        .then(Commands.argument("name", StringArgumentType.string())
                                                .suggests((pContext, pBuilder) -> SharedSuggestionProvider.suggest(Skills.getAllNamesFrom(StringArgumentType.getString(pContext, "spec")), pBuilder))
                                                .then(Commands.argument("level", IntegerArgumentType.integer(0))
                                                        .executes(pContext -> setSkill(pContext.getSource(), StringArgumentType.getString(pContext, "name"), IntegerArgumentType.getInteger(pContext, "level")))
                                                )
                                                .then(Commands.literal("max")
                                                        .executes(pContext -> setSkill(pContext.getSource(), StringArgumentType.getString(pContext, "name"), Skills.getFrom(StringArgumentType.getString(pContext, "name")).getProperties().getMaxLevel()))
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private int setSkill(CommandSourceStack pSource, String pName, int pLevel) throws CommandSyntaxException {
        ServerPlayer serverPlayer = pSource.getPlayerOrException();

        if (!serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).isPresent()) {
            pSource.sendFailure(ModComponents.CAPABILITY_NOT_FOUND);
            return 1;
        }

        Skill skill = Skills.getFrom(pName);
        if (skill == null) {
            pSource.sendFailure(ModComponents.SKILL_NOT_FOUND);
            return 1;
        }

        if (pLevel < skill.getProperties().getMinLevel() || pLevel > skill.getProperties().getMaxLevel()) {
            pSource.sendFailure(ModComponents.INVALID_LEVEL);
            return 1;
        }

        serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(pCapability -> pCapability.setLevel(serverPlayer, skill, pLevel));

        return 1;
    }
}
