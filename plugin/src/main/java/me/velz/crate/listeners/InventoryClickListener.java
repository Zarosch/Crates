package me.velz.crate.listeners;

import me.velz.crate.Crates;
import me.velz.crate.objects.Crate;
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
        Inventory inventory = event.getView().getTopInventory();
        if(inventory == null) return;
        if(event.getView().getTitle() == null) return;
        for(Crate crate : plugin.getCrates().values()) {
            if(event.getView().getTitle().equalsIgnoreCase(crate.getName())) {
                event.setCancelled(true);
            }
        }
    }
    
}
