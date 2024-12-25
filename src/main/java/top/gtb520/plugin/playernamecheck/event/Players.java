package top.gtb520.plugin.playernamecheck.event;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static top.gtb520.plugin.playernamecheck.main.instance;

public class Players implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        FileConfiguration config = instance.getConfig();

        // 获取配置文件中的违禁词列表
        String[] badWords = config.getStringList("bad-words").toArray(new String[0]);

        for (String badWord : badWords) {
            if (playerName.toLowerCase().contains(badWord.toLowerCase())) {
                // 如果名字包含违禁词，踢出玩家并发送消息
                event.setJoinMessage(null); // 可选：不显示加入信息
                event.getPlayer().kickPlayer(config.getString("ban-message"));
                return; // 结束方法执行
            }
        }
    }
}
