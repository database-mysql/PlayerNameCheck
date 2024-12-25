package top.gtb520.plugin.playernamecheck.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import top.gtb520.plugin.playernamecheck.main;
import top.gtb520.plugin.playernamecheck.unity.TimeMessage;
import top.gtb520.plugin.playernamecheck.unity.unity;

public class ReloadPlugin implements Listener {
    public boolean reload(@NotNull CommandSender sender) {
        main.instance.reloadConfig();
        if (unity.GetIntTemp("TimeMessageTaskId") != null) {
            Bukkit.getScheduler().cancelTask(unity.GetIntTemp("TimeMessageTaskId"));
        }
        if (main.instance.getConfig().getBoolean("TimeMessageSetting.TimeMessage")) {
            BukkitTask TimeMessage = (new TimeMessage(main.instance)).runTaskTimer(main.instance, 0L, (main.instance.getConfig().getInt("TimeMessageSetting.TimeMessageTime") * 20L));
            unity.SetIntTemp("TimeMessageTaskId",TimeMessage.getTaskId());
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a重载完成!"));
        return false;
    }
}
