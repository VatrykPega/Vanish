package pl.vatrykpega.vanish.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import pl.vatrykpega.vanish.Main;

public class PlayerPickupItem implements Listener {
    private Main main;

    public PlayerPickupItem(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        if (main.vanishedPlayers.contains(p.getUniqueId()) &&
                !main.pickUpPlayers.contains(p.getUniqueId()))
            e.setCancelled(true);
    }
}
