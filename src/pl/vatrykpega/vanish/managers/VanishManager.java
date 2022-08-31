package pl.vatrykpega.vanish.managers;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import pl.vatrykpega.vanish.Main;

public class VanishManager {

    private Main main;

    public VanishManager(Main main) {
        this.main = main;
    }

    public List<UUID> vanishedPlayers = new ArrayList<>();


    public String getConfigColor(String message) {
        message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString(message));
        return message;
    }

    public void toggleVanish(Player admin) {
        if (!main.vanishedPlayers.contains(admin.getUniqueId())) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.hasPermission("vanish.show"))
                    player.hidePlayer(admin);
            }
            main.vanishedPlayers.add(admin.getUniqueId());
            admin.spigot().setCollidesWithEntities(false);
            admin.sendMessage(getConfigColor("EnableVanish"));
            messageForOthers(admin, getConfigColor("EnableVanishForOthers"));
        } else {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.hasPermission("vanish.show"))
                    player.showPlayer(admin);
            }
            main.vanishedPlayers.remove(admin.getUniqueId());
            admin.spigot().setCollidesWithEntities(true);
            admin.sendMessage(getConfigColor("DisableVanish"));
            messageForOthers(admin, getConfigColor("DisableVanishForOthers"));
        }
    }

    public final void effect(Player admin) {
        admin.getWorld().playSound(admin.getLocation(), Sound.GHAST_CHARGE, 50.0F, 0.0F);
        admin.getWorld().playEffect(admin.getLocation().add(0.5D, 0.5D, 0.0D), Effect.SMOKE, 10);
        admin.getWorld().playEffect(admin.getLocation().add(0.5D, 0.5D, 0.0D), Effect.EXPLOSION, 10);
        final Set<UUID> bat = new HashSet<>();
        for (int i = 0; i < 12; i++)
            bat.add(admin.getWorld().spawnEntity(admin.getLocation().add(0.0D, 1.0D, 0.0D), EntityType.BAT).getUniqueId());
        final Set<UUID> bats = new HashSet<>(bat);
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
            public void run() {
                for (Entity entity : admin.getWorld().getEntities()) {
                    if (bats.contains(entity.getUniqueId())) {
                        entity.getWorld().strikeLightningEffect(entity.getLocation());
                        entity.remove();
                    }
                }
                bats.removeAll(bat);
            }
        },  35L);
    }

    public void consoleSenderMessage(CommandSender sender) {
        sender.sendMessage("Nie mozesz wywolac tej komendy z poziomu konsoli!");
    }

    public void permissionMessage(CommandSender sender, String permission) {
        sender.sendMessage("Nie masz uprawnien, aby uzyc tej komendy!" + permission);
    }

    public void messageForOthers(Player admin, String message) {
        Bukkit.getConsoleSender().sendMessage(message.replaceAll("%player%", admin.getName()));
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("vanish.notification") &&
                    p != admin)
                p.sendMessage(message.replaceAll("%player%", admin.getName()));
        }
    }
}
