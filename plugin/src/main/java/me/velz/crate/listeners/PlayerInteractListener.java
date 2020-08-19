package me.velz.crate.listeners;

import java.util.Random;
import me.velz.crate.Crates;
import me.velz.crate.objects.Crate;
import me.velz.crate.objects.CrateChest;
import me.velz.crate.objects.CrateItem;
import me.velz.crate.objects.CrateOpening;
import me.velz.crate.utils.ItemBuilder;
import me.velz.crate.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() == null) {
                return;
            }
            if (event.getItem().getItemMeta() == null) {
                return;
            }
            if (event.getItem().getItemMeta().getDisplayName() == null) {
                return;
            }
            for (Crate crate : plugin.getCrates().values()) {
                if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(crate.getItem().getItemMeta().getDisplayName())) {
                    boolean open = false;
                    event.setCancelled(true);
                    if (crate.getType().equalsIgnoreCase("crate")) {
                        open = true;
                    }
                    if (crate.getType().equalsIgnoreCase("key")) {
                        if(event.getClickedBlock() != null) {
                            for(CrateChest chest : plugin.getOpeners()) {
                                if(chest.getLocations().contains(event.getClickedBlock().getLocation())) {
                                    if(chest.getCrates().contains(crate.getName())) {
                                        open = true;
                                    }
                                }
                            }
                        }
                    }
                    if (open) {
                        if (plugin.inventoryContains(event.getPlayer().getInventory(), crate.getItem())) {
                            if (!crate.getCrateItems().isEmpty()) {
                                if (plugin.getFileManager().isAnimation()) {
                                    Inventory inventory = Bukkit.createInventory(null, 27, crate.getName());
                                    inventory.setItem(4, new ItemBuilder().setDisplayName(" ").setMaterial(Material.HOPPER).build());
                                    if (crate.getBackground() != null) {
                                        inventory.setItem(0, crate.getBackground());
                                        inventory.setItem(1, crate.getBackground());
                                        inventory.setItem(2, crate.getBackground());
                                        inventory.setItem(3, crate.getBackground());
                                        inventory.setItem(5, crate.getBackground());
                                        inventory.setItem(6, crate.getBackground());
                                        inventory.setItem(7, crate.getBackground());
                                        inventory.setItem(8, crate.getBackground());
                                        inventory.setItem(26, crate.getBackground());
                                        inventory.setItem(25, crate.getBackground());
                                        inventory.setItem(24, crate.getBackground());
                                        inventory.setItem(23, crate.getBackground());
                                        inventory.setItem(22, crate.getBackground());
                                        inventory.setItem(21, crate.getBackground());
                                        inventory.setItem(20, crate.getBackground());
                                        inventory.setItem(19, crate.getBackground());
                                        inventory.setItem(18, crate.getBackground());
                                    }
                                    event.getPlayer().openInventory(inventory);
                                    new CrateOpening(plugin, event.getPlayer(), inventory, crate);
                                    plugin.removeFromInventory(event.getPlayer().getInventory(), crate.getItem());
                                } else {
                                    Random random = new Random();
                                    CrateItem nextCrateItem = crate.getCrateItems().get(random.nextInt(crate.getCrateItems().size()));
                                    nextCrateItem.runCommands(player);
                                    player.playSound(player.getLocation(), plugin.getVersion().getSound("ENTITY_PLAYER_LEVELUP"), 10L, 10L);
                                    player.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ITEMWON.getLocal().replaceAll("%win", nextCrateItem.getName()));
                                    plugin.removeFromInventory(event.getPlayer().getInventory(), crate.getItem());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
