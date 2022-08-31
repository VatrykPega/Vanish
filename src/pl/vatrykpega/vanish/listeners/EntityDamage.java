package pl.vatrykpega.vanish.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import pl.vatrykpega.vanish.Main;

public class EntityDamage implements Listener {
    private Main main;
    public EntityDamage(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (main.vanishedPlayers.contains(p.getUniqueId()))
                e.setCancelled(true);
        }
    }
}
