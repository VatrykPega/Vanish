package pl.vatrykpega.vanish.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.vatrykpega.vanish.Main;
import pl.vatrykpega.vanish.managers.VanishManager;

public class TogglePickUpCommand extends VanishManager implements CommandExecutor {
    private Main main;
    public TogglePickUpCommand(Main main) {
        super(main);
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (sender.hasPermission("vanish.togglepickup")) {
                if (main.vanishedPlayers.contains(p.getUniqueId())) {
                    if (!main.pickUpPlayers.contains(p.getUniqueId())) {
                        main.pickUpPlayers.add(p.getUniqueId());
                        p.spigot().setCollidesWithEntities(true);
                        sender.sendMessage(getConfigColor("EnablePickUp"));
                    } else {
                        main.pickUpPlayers.remove(p.getUniqueId());
                        p.spigot().setCollidesWithEntities(false);
                        sender.sendMessage(getConfigColor("DisablePickUp"));
                    }
                } else {
                    sender.sendMessage("§cTej komendy mozesz uzyc tylko bedac na vanishu!");
                }
            } else {
                permissionMessage(sender, "vanish.togglepickup");
            }
        } else {
            consoleSenderMessage(sender);
        }
        return false;
    }
}
