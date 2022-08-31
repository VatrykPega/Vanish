package pl.vatrykpega.vanish.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import pl.vatrykpega.vanish.Main;

public class InventoryManager implements Listener {
    private Main main;

    public InventoryManager(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onClickEvent(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (main.chestSpectators.contains(p.getUniqueId()))
            e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Player p = (Player)e.getPlayer();
        if (main.chestSpectators.contains(p.getUniqueId()))
            main.chestSpectators.remove(p.getUniqueId());
    }
}
