package me.velz.crate.listeners;

import me.velz.crate.Crates;
import me.velz.crate.objects.Crate;
import me.velz.crate.objects.CrateOpening;
import me.velz.crate.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class PlayerInteractListener implements Listener {

    private final Crates plugin;

    public PlayerInteractListener(Crates plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            for (Crate crate : plugin.getCrates().values()) {
                if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(crate.getItem().getItemMeta().getDisplayName())) {
                    Inventory inventory = Bukkit.createInventory(null, 27, crate.getName());
                    inventory.setItem(4, new ItemBuilder().setDisplayName(" ").setMaterial(Material.HOPPER).build());

                    event.getPlayer().openInventory(inventory);
                    if (plugin.inventoryContains(event.getPlayer().getInventory(), crate.getItem())) {
                        new CrateOpening(plugin, event.getPlayer(), inventory, crate);
                    }
                    plugin.removeFromInventory(event.getPlayer().getInventory(), crate.getItem());
                    event.setCancelled(true);
                }
            }
        }
    }

}
