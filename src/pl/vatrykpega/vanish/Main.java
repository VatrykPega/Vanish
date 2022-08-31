package pl.vatrykpega.vanish;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import pl.vatrykpega.vanish.commands.TogglePickUpCommand;
import pl.vatrykpega.vanish.commands.VanishCommand;
import pl.vatrykpega.vanish.listeners.BlockBreak;
import pl.vatrykpega.vanish.listeners.BlockPlace;
import pl.vatrykpega.vanish.listeners.EntityDamage;
import pl.vatrykpega.vanish.listeners.EntityTarget;
import pl.vatrykpega.vanish.listeners.FoodLevelChange;
import pl.vatrykpega.vanish.listeners.InventoryManager;
import pl.vatrykpega.vanish.listeners.PlayerBucket;
import pl.vatrykpega.vanish.listeners.PlayerDropItem;
import pl.vatrykpega.vanish.listeners.PlayerInteract;
import pl.vatrykpega.vanish.listeners.PlayerJoin;
import pl.vatrykpega.vanish.listeners.PlayerPickupItem;
import pl.vatrykpega.vanish.listeners.PlayerQuit;

public class Main extends JavaPlugin {
    public List<UUID> vanishedPlayers = new ArrayList<>();
    public List<UUID> chestSpectators = new ArrayList<>();
    public List<UUID> pickUpPlayers = new ArrayList<>();
    private static Main main;

    public static Main getInstance() {
        return main;
    }

    public void reloadVanish() {
        getServer().getScheduler().cancelAllTasks();
        HandlerList.unregisterAll();
        onDisable();
        onEnable();
        Bukkit.getConsoleSender().sendMessage("[Vanish] Przeladowano!");
    }

    public void onEnable() {
        main = this;

        registerCommands();
        registerEvents();

        saveDefaultConfig();
        reloadConfig();

        removeInvisibilityForAdmins();

        if (!vanishedPlayers.isEmpty())
            vanishedPlayers.clear();
        if (!chestSpectators.isEmpty())
            chestSpectators.clear();

        restoreInvisibilityForAdmins();
    }

    public void onDisable() {
        vanishedPlayers.clear();
        chestSpectators.clear();
        pickUpPlayers.clear();
    }

    private void registerCommands(){
        getCommand("togglepickup").setExecutor(new TogglePickUpCommand(this));
        getCommand("vanish").setExecutor(new VanishCommand(this));
    }
    private void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new BlockBreak(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlace(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamage(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityTarget(this), this);
        Bukkit.getPluginManager().registerEvents(new FoodLevelChange(this), this);
        Bukkit.getPluginManager().registerEvents(new InventoryManager(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerBucket(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDropItem(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerPickupItem(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(this), this);
    }

    private void removeInvisibilityForAdmins(){
        for (Player admin : Bukkit.getOnlinePlayers()) {
            if (vanishedPlayers.contains(admin.getUniqueId())) {
                for (Player player : Bukkit.getOnlinePlayers())
                    player.showPlayer(admin);
                vanishedPlayers.remove(admin.getUniqueId());
            }
        }
    }
    private void restoreInvisibilityForAdmins(){
        for (Player admin : Bukkit.getOnlinePlayers()) {
            if (admin.hasPermission("vanish.join") || admin.hasPermission("vanish.command.vanish")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!player.hasPermission("vanish.show"))
                        player.hidePlayer(admin);
                }
                vanishedPlayers.add(admin.getUniqueId());
                admin.spigot().setCollidesWithEntities(false);
                admin.sendMessage("§3[Vanish] Przeladowano serwer! Ponownie stales sie niewidzialny! Poof.");
            }
        }
    }
}
