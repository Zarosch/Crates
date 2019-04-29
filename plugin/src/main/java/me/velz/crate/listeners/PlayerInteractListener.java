package me.velz.crate.listeners;

import java.util.Random;
import me.velz.crate.Crates;
import me.velz.crate.objects.Crate;
import me.velz.crate.objects.CrateItem;
import me.velz.crate.objects.CrateOpening;
import me.velz.crate.utils.ItemBuilder;
import me.velz.crate.utils.MessageUtil;
import org.bukkit.Bukkit;
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
                    event.setCancelled(true);
                    if (plugin.inventoryContains(event.getPlayer().getInventory(), crate.getItem())) {
                        if (!crate.getCrateItems().isEmpty()) {
                            if (plugin.getFileManager().isAnimation()) {
                                Inventory inventory = Bukkit.createInventory(null, 27, crate.getName());
                                inventory.setItem(4, new ItemBuilder().setDisplayName(" ").setMaterial(Material.HOPPER).build());

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
