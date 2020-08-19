package me.velz.crate;

import java.util.ArrayList;
import java.util.HashMap;
import lombok.Getter;
import me.velz.crate.commands.CratesCommand;
import me.velz.crate.listeners.InventoryClickListener;
import me.velz.crate.listeners.InventoryCloseListener;
import me.velz.crate.listeners.PlayerInteractListener;
import me.velz.crate.listeners.PlayerJoinListener;
import me.velz.crate.listeners.PlayerQuitListener;
import me.velz.crate.objects.Crate;
import me.velz.crate.objects.CrateChest;
import me.velz.crate.utils.FileManager;
import me.velz.crate.version.Version;
import me.velz.crate.version.VersionMatcher;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.inventivetalent.update.spiget.SpigetUpdate;
import org.inventivetalent.update.spiget.UpdateCallback;
import org.inventivetalent.update.spiget.comparator.VersionComparator;

public class Crates extends JavaPlugin {

    @Getter
    private static Crates plugin;

    @Getter
    private final FileManager fileManager = new FileManager(this);

    @Getter
    private final HashMap<String, Crate> crates = new HashMap();
    
    @Getter
    private final ArrayList<CrateChest> openers = new ArrayList<>();

    @Getter
    private final VersionMatcher versionMatcher = new VersionMatcher();

    @Getter
    private Version version;

    @Override
    public void onEnable() {
        plugin = this;
        version = versionMatcher.match();
        getCommand("crate").setExecutor(new CratesCommand(this));
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        Bukkit.getPluginManager().registerEvents(new InventoryCloseListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        fileManager.setDefaults();
        fileManager.load();
        Metrics metrics = new Metrics(this);
        if (plugin.getFileManager().getConfigBuilder().getBoolean("autoupdate")) {
            SpigetUpdate updater = new SpigetUpdate(plugin, 59904);
            updater.setVersionComparator(VersionComparator.SEM_VER);
            updater.checkForUpdate(new UpdateCallback() {
                @Override
                public void updateAvailable(String newVersion, String downloadUrl, boolean hasDirectDownload) {
                    if (hasDirectDownload) {
                        if (updater.downloadUpdate()) {
                            System.out.println("[Crates] Update downloaded. Please restart your server.");
                        } else {
                            System.out.println("[Crates] Please Update! Newest Version: " + newVersion);
                            System.out.println("[Crates] " + downloadUrl);
                            System.out.println("[Crates] Auto Update failed! Reason: " + updater.getFailReason());
                        }
                    } else {
                        System.out.println("[Crates] Please Update! Newest Version: " + newVersion);
                        System.out.println("[Crates] " + downloadUrl);
                    }
                }

                @Override
                public void upToDate() {
                    System.out.println("[Crates] Up to date! No updates available.");
                }
            });
        }
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
