package me.velz.crate;

import java.util.HashMap;
import lombok.Getter;
import me.velz.crate.commands.CratesCommand;
import me.velz.crate.listeners.InventoryClickListener;
import me.velz.crate.listeners.PlayerInteractListener;
import me.velz.crate.objects.Crate;
import me.velz.crate.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Crates extends JavaPlugin {

    @Getter
    private final FileManager fileManager = new FileManager(this);

    @Getter
    private final HashMap<String, Crate> crates = new HashMap();

    @Override
    public void onEnable() {
        getCommand("crates").setExecutor(new CratesCommand(this));
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(this), this);

        fileManager.setDefaults();
        fileManager.load();
    }

    @Override
    public void onDisable() {

    }

    public boolean inventoryContains(Inventory inventory, ItemStack item) {
        int count = 0;
        ItemStack[] items = inventory.getContents();
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].getType() == item.getType() && items[i].getDurability() == item.getDurability()) {
                count += items[i].getAmount();
            }
            if (count >= item.getAmount()) {
                return true;
            }
        }
        return false;
    }

    public void removeFromInventory(Inventory inventory, ItemStack item) {
        int amt = item.getAmount();
        ItemStack[] items = inventory.getContents();
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].getType() == item.getType() && items[i].getDurability() == item.getDurability()) {
                if (items[i].getAmount() > amt) {
                    items[i].setAmount(items[i].getAmount() - amt);
                    break;
                } else if (items[i].getAmount() == amt) {
                    items[i] = null;
                    break;
                } else {
                    amt -= items[i].getAmount();
                    items[i] = null;
                }
            }
        }
        inventory.setContents(items);
    }

}
