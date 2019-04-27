package me.velz.crate.listeners;

import java.util.Random;
import me.velz.crate.Crates;
import me.velz.crate.objects.Crate;
import me.velz.crate.objects.CrateItem;
import me.velz.crate.utils.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final Crates plugin;

    public PlayerQuitListener(Crates plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (event.getPlayer().hasMetadata("crate")) {
            Player player = event.getPlayer();
            Crate crate = (Crate) event.getPlayer().getMetadata("crate").get(0).value();
            Random random = new Random();
            CrateItem nextCrateItem = crate.getCrateItems().get(random.nextInt(crate.getCrateItems().size()));
            if (event.getPlayer().hasMetadata("cratewinning")) {
                nextCrateItem = (CrateItem) event.getPlayer().getMetadata("cratewinning").get(0).value();
                player.removeMetadata("cratewinning", plugin);
            }
            nextCrateItem.runCommands(player);
            player.playSound(player.getLocation(), plugin.getVersion().getSound("ENTITY_PLAYER_LEVELUP"), 10L, 10L);
            player.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ITEMWON.getLocal().replaceAll("%win", nextCrateItem.getName()));
            player.removeMetadata("crate", plugin);
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        if (event.getPlayer().hasMetadata("crate")) {
            Player player = event.getPlayer();
            Crate crate = (Crate) event.getPlayer().getMetadata("crate").get(0).value();
            Random random = new Random();
            CrateItem nextCrateItem = crate.getCrateItems().get(random.nextInt(crate.getCrateItems().size()));
            if (event.getPlayer().hasMetadata("cratewinning")) {
                nextCrateItem = (CrateItem) event.getPlayer().getMetadata("cratewinning").get(0).value();
                player.removeMetadata("cratewinning", plugin);
            }
            nextCrateItem.runCommands(player);
            player.playSound(player.getLocation(), plugin.getVersion().getSound("ENTITY_PLAYER_LEVELUP"), 10L, 10L);
            player.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ITEMWON.getLocal().replaceAll("%win", nextCrateItem.getName()));
            player.removeMetadata("crate", plugin);
        }
    }

}
