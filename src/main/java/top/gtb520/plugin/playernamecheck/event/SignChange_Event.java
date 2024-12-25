package top.gtb520.plugin.playernamecheck.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import top.gtb520.plugin.playernamecheck.main;

import java.util.Objects;

public class SignChange_Event implements Listener {
    @EventHandler
    public void ChangeSignEvent(SignChangeEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) {
            if (main.instance.getConfig().getBoolean("ColorSignSetting.AllowAdminUsedColorSign")) {
                for (int i = 0; i < 4; i++) {
                    if (!Objects.equals(event.getLine(i), "&"))
                        event.setLine(i, Objects.requireNonNull(event.getLine(i)).replace("&", "§"));
                }
            }
        }else {
            if (main.instance.getConfig().getBoolean("ColorSignSetting.AllowPlayerUsedColorSign")) {
                for (int i = 0; i < 4; i++) {
                    if (!Objects.equals(event.getLine(i), "&"))
                        event.setLine(i, Objects.requireNonNull(event.getLine(i)).replace("&", "§"));
                }
            }
        }
    }
}
