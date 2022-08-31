package pl.vatrykpega.vanish.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import pl.vatrykpega.vanish.Main;

public class PlayerDropItem implements Listener {
    private Main main;

    public PlayerDropItem(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (main.vanishedPlayers.contains(p.getUniqueId()) &&
                !p.hasPermission("vanish.dropitem"))
            e.setCancelled(true);
    }
}
