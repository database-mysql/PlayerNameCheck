package top.gtb520.plugin.playernamecheck.controller;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.gtb520.plugin.playernamecheck.commands.ReloadPlugin;
import static top.gtb520.plugin.playernamecheck.unity.unity.ColorMessage;
import static top.gtb520.plugin.playernamecheck.unity.unity.GetLoggerPlus;

import java.util.ArrayList;
import java.util.List;

public class command_redirect implements TabExecutor {
    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1) {
            if (args[0].equals("plugin")) {
                if (args.length >= 2) {
                    if (args[1].equals("reload") && sender.isOp()) {
                        new ReloadPlugin().reload(sender);
                        sender.sendMessage(ColorMessage("Command Run Done!"));
                        GetLoggerPlus("&2插件已重载！Command Run Done!");
                        return false;
                    }else {
                        sender.sendMessage(ColorMessage("&4您的权限不足！"));
                    }
                }
            }
        }
        return false;
    }

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender  Source of the command.  For players tab-completing a
     *                command inside of a command block, this will be the player, not
     *                the command block.
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> commandlist1 = new ArrayList<>();
        List<String> commandlist2 = new ArrayList<>();
        commandlist1.add("plugin");
        commandlist2.add("reload");
        if (args.length == 1) {
            return commandlist1;
        }
        if (args.length == 2) {
            return commandlist2;
        }
        return null;
    }
}
