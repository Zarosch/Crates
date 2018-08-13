package me.velz.crate.listeners;

import me.velz.crate.Crates;
import me.velz.crate.objects.Crate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener {
    
    private final Crates plugin;

    public InventoryClickListener(Crates plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getView().getTopInventory();
        if(inventory == null) return;
        if(inventory.getTitle()== null) return;
        for(Crate crate : plugin.getCrates().values()) {
            if(event.getInventory().getTitle().equalsIgnoreCase(crate.getName())) {
                event.setCancelled(true);
            }
        }
    }
    
}
