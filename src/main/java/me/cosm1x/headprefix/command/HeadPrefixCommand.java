package me.cosm1x.headprefix.command;

import static net.minecraft.server.command.CommandManager.*;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.cosm1x.headprefix.headprefix.HeadPrefix;
import me.cosm1x.headprefix.networking.PacketRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.command.argument.TeamArgumentType;
import net.minecraft.command.argument.TextArgumentType;

public class HeadPrefixCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> 
            dispatcher.register(literal("headprefix").requires(context -> context.hasPermissionLevel(4))
                .then(literal("set")
                    .then(argument("team", TeamArgumentType.team())
                        .then(argument("prefix", TextArgumentType.text())
                            .executes(context -> executeHeadPrefixModify(context.getSource(), TeamArgumentType.getTeam(context, "team"), TextArgumentType.getTextArgument(context, "prefix"))))))
                .then(literal("remove")
                    .then(argument("team", TeamArgumentType.team())
                        .executes(context -> executeHeadPrefixRemove(context.getSource(), TeamArgumentType.getTeam(context, "team")))))));
    }

    private static int executeHeadPrefixModify(ServerCommandSource source, Team team, Text prefix) throws CommandSyntaxException{
        HeadPrefix hp = source.getServer().getWorld(World.OVERWORLD).getPersistentStateManager().get(HeadPrefix::readNbt, "headprefix");
        hp.setHeadPrefix(team, prefix);
        source.getServer().getPlayerManager().getPlayerList().forEach(player -> {
            ServerPlayNetworking.send(player, PacketRegistry.HEAD_PREFIX_UPDATE, hp.createBuf());
        });
        source.sendFeedback(Text.translatable("commands.headprefix.set.success", prefix), false);
        return 1;
    }

    private static int executeHeadPrefixRemove(ServerCommandSource source, Team team) throws CommandSyntaxException{
        HeadPrefix hp = source.getServer().getWorld(World.OVERWORLD).getPersistentStateManager().get(HeadPrefix::readNbt, "headprefix");
        hp.removeHeadPrefix(team);
        source.getServer().getPlayerManager().getPlayerList().forEach(player -> {
            ServerPlayNetworking.send(player, PacketRegistry.HEAD_PREFIX_UPDATE, hp.createBuf());
        });
        source.sendFeedback(Text.translatable("commands.headprefix.remove.success", Text.literal(" ")), false);
        return 1;
    }
}
