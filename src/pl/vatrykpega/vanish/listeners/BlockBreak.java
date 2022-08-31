package pl.vatrykpega.vanish.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.vatrykpega.vanish.Main;

public class BlockBreak implements Listener {
    private Main main;
    public BlockBreak(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (main.vanishedPlayers.contains(p.getUniqueId()) &&
                !p.hasPermission("vanish.blockbreak"))
            e.setCancelled(true);
    }
}
