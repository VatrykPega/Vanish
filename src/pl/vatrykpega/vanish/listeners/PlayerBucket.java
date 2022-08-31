package pl.vatrykpega.vanish.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import pl.vatrykpega.vanish.Main;

public class PlayerBucket implements Listener {
    private Main main;

    public PlayerBucket(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerBucketFill(PlayerBucketFillEvent e) {
        Player p = e.getPlayer();
        if (main.vanishedPlayers.contains(p.getUniqueId()) &&
                !p.hasPermission("vanish.interact"))
            e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent e) {
        Player p = e.getPlayer();
        if (main.vanishedPlayers.contains(p.getUniqueId()) &&
                !p.hasPermission("vanish.interact"))
            e.setCancelled(true);
    }
}
