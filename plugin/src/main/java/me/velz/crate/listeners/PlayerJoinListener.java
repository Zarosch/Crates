package me.velz.crate.listeners;

import me.velz.crate.Crates;
import me.velz.crate.utils.MessageUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.inventivetalent.update.spiget.SpigetUpdate;
import org.inventivetalent.update.spiget.UpdateCallback;
import org.inventivetalent.update.spiget.comparator.VersionComparator;

public class PlayerJoinListener implements Listener {

    private final Crates plugin;

    public PlayerJoinListener(Crates plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (plugin.getFileManager().getConfigBuilder().getBoolean("autoupdate") && event.getPlayer().hasPermission("crate.autoupdate")) {
            SpigetUpdate updater = new SpigetUpdate(plugin, 59904);
            updater.setVersionComparator(VersionComparator.SEM_VER);
            updater.checkForUpdate(new UpdateCallback() {
                @Override
                public void updateAvailable(String newVersion, String downloadUrl, boolean hasDirectDownload) {
                    if (hasDirectDownload) {
                        if (updater.downloadUpdate()) {
                            event.getPlayer().sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.UPDATE_RESTART.getLocal());
                            System.out.println("[Crates] Update downloaded. Please restart your server.");
                        } else {
                            System.out.println("[Crates] Please Update! Newest Version: " + newVersion);
                            System.out.println("[Crates] " + downloadUrl);
                            System.out.println("[Crates] Auto Update failed! Reason: " + updater.getFailReason());
                            event.getPlayer().sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.UPDATE.getLocal().replaceAll("%url", downloadUrl));
                        }
                    } else {
                        System.out.println("[Crates] Please Update! Newest Version: " + newVersion);
                        System.out.println("[Crates] " + downloadUrl);
                    }
                }

                @Override
                public void upToDate() {
                }
            });
        }
    }

}
