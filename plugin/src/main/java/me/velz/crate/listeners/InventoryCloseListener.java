package me.velz.crate.listeners;

import java.util.Random;
import me.velz.crate.Crates;
import me.velz.crate.utils.MessageUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class InventoryCloseListener implements Listener {

    private final Crates plugin;

    public InventoryCloseListener(Crates plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().startsWith("Crate:")) {
            String crate = event.getView().getTitle().replaceAll("Crate: ", "");
            if (!plugin.getCrates().containsKey(crate)) {
                event.getPlayer().sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_CRATENOTFOUND.getLocal());
            } else {
                int i = 0;
                for (ItemStack stack : event.getInventory().getContents()) {
                    if (stack != null) {
                        i++;
                        Random random = new Random();
                        String name = System.currentTimeMillis() + "-" + random.nextInt(1000) + "-" + i;
                        plugin.getFileManager().getCratesBuilder().set("crates." + crate + ".content." + name + ".name", stack.getType().toString());
                        if (stack.getItemMeta() != null) {
                            if (stack.getItemMeta().getDisplayName() != null) {
                                if (!stack.getItemMeta().getDisplayName().equalsIgnoreCase("")) {
                                    plugin.getFileManager().getCratesBuilder().set("crates." + crate + ".content." + name + ".name", stack.getItemMeta().getDisplayName());
                                }
                            }
                        }
                        if (stack.getType().equals(Material.PLAYER_HEAD) && stack.hasItemMeta() && ((SkullMeta) stack.getItemMeta()).getOwner() != null && !((SkullMeta) stack.getItemMeta()).getOwner().equalsIgnoreCase("")) {
                            plugin.getFileManager().getCratesBuilder().set("crates." + crate + ".content." + name + ".item.meta.owningPlayer", ((SkullMeta) stack.getItemMeta()).getOwner());
                            plugin.getFileManager().getCratesBuilder().set("crates." + crate + ".content." + name + ".items.item.meta.owningPlayer", ((SkullMeta) stack.getItemMeta()).getOwner());
                        }
                        plugin.getFileManager().getCratesBuilder().set("crates." + crate + ".content." + name + ".chance", 1);
                        plugin.getFileManager().getCratesBuilder().set("crates." + crate + ".content." + name + ".item", stack);
                        plugin.getFileManager().getCratesBuilder().set("crates." + crate + ".content." + name + ".items.item", stack);
                        plugin.getFileManager().getCratesBuilder().set("crates." + crate + ".content." + name + ".commands", new String[]{});
                    }
                }
                plugin.getFileManager().getCratesBuilder().save();
                plugin.getFileManager().load();
                event.getPlayer().sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.COMMAND_ADDITEM_ADDED.getLocal());
            }
        }
    }

}
