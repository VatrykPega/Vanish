package pl.vatrykpega.vanish.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import pl.vatrykpega.vanish.Main;
import pl.vatrykpega.vanish.managers.VanishManager;

public class PlayerInteract extends VanishManager implements Listener {
    private Main main;

    public PlayerInteract(Main main) {
        super(main);
        this.main = main;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (main.vanishedPlayers.contains(p.getUniqueId())) {
            if (e.getAction() == Action.PHYSICAL)
                switch (e.getClickedBlock().getType()) {
                    case GOLD_PLATE:
                    case IRON_PLATE:
                    case WOOD_PLATE:
                    case STONE_PLATE:
                        e.setCancelled(true);
                        break;
                }
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (e.getClickedBlock().getType() == Material.ENDER_CHEST)
                    if (!p.isSneaking()) {
                        e.setCancelled(true);
                        p.openInventory(p.getEnderChest());
                    } else if (!p.hasPermission("vanish.interact")) {
                        e.setCancelled(true);
                        p.openInventory(p.getEnderChest());
                    }
                if (e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.TRAPPED_CHEST) {
                    Chest chest = (Chest) e.getClickedBlock().getState();
                    Inventory inv = Bukkit.createInventory(p, chest.getInventory().getSize(), getConfigColor("ChestName"));
                    inv.setContents(chest.getInventory().getContents());
                    if (!p.isSneaking()) {
                        e.setCancelled(true);
                        p.openInventory(inv);
                        main.chestSpectators.add(p.getUniqueId());
                        p.sendMessage(getConfigColor("OpenChestSilent"));
                    } else if (!p.hasPermission("vanish.interact")) {
                        e.setCancelled(true);
                        p.openInventory(inv);
                        main.chestSpectators.add(p.getUniqueId());
                        p.sendMessage(getConfigColor("OpenChestSilent"));
                    }
                }
                if (!p.hasPermission("vanish.interact")) {
                    String[] interactedBlocks = {
                            "LEVER", "STONE_BUTTON", "WOOD_BUTTON", "FURNACE", "DISPENSER", "BEACON", "HOPPER", "DROPPER", "WOODEN_DOOR", "SPRUCE_DOOR",
                            "BIRCH_DOOR", "JUNGLE_DOOR", "ACACIA_DOOR", "DARK_OAK_DOOR", "DROPPER", "TRAP_DOOR", "ACACIA_FENCE_GATE", "DARK_OAK_FENCE_GATE", "CHEST", "TRAPPED_CHEST",
                            "JUNGLE_FENCE_GATE", "BIRCH_FENCE_GATE", "SPRUCE_FENCE_GATE", "FENCE_GATE"};
                    for (String interactedBlock : interactedBlocks) {
                        if (interactedBlock.equals(e.getClickedBlock().getType().toString())) {
                            if (!p.isSneaking()) {
                                e.setCancelled(true);
                            } else if (p.getItemInHand().getType() == Material.AIR) {
                                e.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }
}
