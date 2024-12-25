package top.gtb520.plugin.playernamecheck.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import top.gtb520.plugin.playernamecheck.unity.unity;

public class PlayerReSpawn_Event implements Listener {
    @EventHandler
    public void On_Event(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Location DiedLocation = unity.getLocationTempTemp(player.getName() + "_Back");
        player.sendMessage(unity.ColorMessage("&a&l您的死亡位置是X:" + DiedLocation.getBlockX() + " Y:" + DiedLocation.getBlockY() + "_DiedY" + " Z:" + DiedLocation.getBlockZ()));
    }
}
