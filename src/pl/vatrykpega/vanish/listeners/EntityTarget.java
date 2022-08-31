package pl.vatrykpega.vanish.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import pl.vatrykpega.vanish.Main;

public class EntityTarget implements Listener {
    private Main main;

    public EntityTarget(Main main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityTarget(EntityTargetEvent e) {
        if (e.getTarget() instanceof Player &&
                main.vanishedPlayers.contains(e.getTarget().getUniqueId()))
            e.setCancelled(true);
    }
}
