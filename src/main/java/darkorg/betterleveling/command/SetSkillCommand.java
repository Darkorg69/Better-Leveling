package darkorg.betterleveling.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.util.RegistryUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static net.minecraft.command.Commands.argument;
import static net.minecraft.command.Commands.literal;

public class SetSkillCommand {
    public SetSkillCommand(CommandDispatcher<CommandSource> pDispatcher) {
        pDispatcher.register(literal(BetterLeveling.MOD_ID).requires(source -> source.hasPermission(2)).then(literal("set").then(argument("skill", string()).then(argument("level", integer(0, 10)).executes(context -> setSkill(context.getSource(), getString(context, "skill"), getInteger(context, "level")))))));
    }

    private int setSkill(CommandSource pSource, String pSkill, int pLevel) throws CommandSyntaxException {
        ServerPlayerEntity serverPlayer = pSource.getPlayerOrException();

        if (!serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).isPresent()) {
            pSource.sendFailure(ModComponents.CAPABILITY_NOT_FOUND);
        }

        ISkill skill = RegistryUtil.getSkillFromName(pSkill);
        if (skill == null) {
            pSource.sendFailure(ModComponents.SKILL_NOT_FOUND);
        }

        serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> capability.setLevel(serverPlayer, skill, pLevel));

        return 1;
    }
}
