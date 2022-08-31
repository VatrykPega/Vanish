package pl.vatrykpega.vanish.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.vatrykpega.vanish.Main;

public class BlockPlace implements Listener {
    private Main main;
    public BlockPlace(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (main.vanishedPlayers.contains(p.getUniqueId()) &&
                !p.hasPermission("vanish.blockplace"))
            e.setCancelled(true);
    }
}
