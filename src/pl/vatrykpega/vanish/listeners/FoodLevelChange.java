package pl.vatrykpega.vanish.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import pl.vatrykpega.vanish.Main;

public class FoodLevelChange implements Listener {
    private Main main;

    public FoodLevelChange(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        Player p = (Player)e.getEntity();
        if (main.vanishedPlayers.contains(p.getUniqueId()))
            e.setCancelled(true);
    }
}
