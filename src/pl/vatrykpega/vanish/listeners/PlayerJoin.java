package pl.vatrykpega.vanish.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.vatrykpega.vanish.Main;
import pl.vatrykpega.vanish.managers.VanishManager;

public class PlayerJoin extends VanishManager implements Listener {
    private Main main;

    public PlayerJoin(Main main) {
        super(main);
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player admin = e.getPlayer();
        if (admin.hasPermission("vanish.join")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.hasPermission("vanish.show"))
                    player.hidePlayer(admin);
            }
            main.vanishedPlayers.add(admin.getUniqueId());
            admin.spigot().setCollidesWithEntities(false);
            admin.sendMessage(getConfigColor("VanishJoin"));
            messageForOthers(admin, getConfigColor("VanishJoinForOthers"));
        }
        if (!admin.hasPermission("vanish.show")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (main.vanishedPlayers.contains(player.getUniqueId()))
                    admin.hidePlayer(player);
            }
        }
    }
}
