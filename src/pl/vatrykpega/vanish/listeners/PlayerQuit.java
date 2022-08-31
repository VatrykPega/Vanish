package pl.vatrykpega.vanish.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.vatrykpega.vanish.Main;
import pl.vatrykpega.vanish.managers.VanishManager;

public class PlayerQuit extends VanishManager implements Listener {
    private Main main;

    public PlayerQuit(Main main) {
        super(main);
        this.main = main;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player admin = e.getPlayer();
        if (main.vanishedPlayers.contains(admin.getUniqueId())) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.hasPermission("vanish.show"))
                    player.showPlayer(admin);
            }
            main.vanishedPlayers.remove(admin.getUniqueId());
            admin.spigot().setCollidesWithEntities(true);
            messageForOthers(admin, getConfigColor("VanishQuitForOthers"));
        }
    }
}
