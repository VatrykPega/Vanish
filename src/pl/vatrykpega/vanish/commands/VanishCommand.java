package pl.vatrykpega.vanish.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.vatrykpega.vanish.Main;
import pl.vatrykpega.vanish.managers.VanishManager;

public class VanishCommand extends VanishManager implements CommandExecutor {
    private Main main;

    public VanishCommand(Main main) {
        super(main);
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player admin = (Player)sender;
                if (admin.hasPermission("vanish.command.vanish")) {
                    toggleVanish(admin);
                    effect(admin);
                } else {
                    permissionMessage(admin, "vanish.command.vanish");
                }
            } else {
                consoleSenderMessage(sender);
            }
        } else if (args[0].equalsIgnoreCase("silent") || args[0].equalsIgnoreCase("s")) {
            if (sender instanceof Player) {
                Player admin = (Player)sender;
                if (admin.hasPermission("vanish.silent")) {
                    toggleVanish(admin);
                } else {
                    permissionMessage(admin, "vanish.silent");
                }
            } else {
                consoleSenderMessage(sender);
            }
        } else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
            if (sender.hasPermission("vanish.reload")) {
                main.reloadVanish();
            } else {
                permissionMessage(sender, "vanish.reload");
            }
        } else {
            sender.sendMessage("§c§lCos zle wpisujesz!");
        }
        return false;
    }
}
