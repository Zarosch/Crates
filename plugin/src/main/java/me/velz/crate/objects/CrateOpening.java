package me.velz.crate.objects;

import java.util.Random;
import me.velz.crate.Crates;
import me.velz.crate.utils.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitTask;

public class CrateOpening implements Runnable{
    
    private final Crates plugin;
    private final Player player;
    private final Inventory inventory;
    private final Crate crate;
    private final BukkitTask bukkitTask;
    private int count = 0;
    private CrateItem win;

    public CrateOpening(Crates plugin, Player player, Inventory inventory, Crate crate) {
        this.plugin = plugin;
        this.player = player;
        this.inventory = inventory;
        this.crate = crate;
        
        this.bukkitTask = this.plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this, 4L, 4L);
    }

    @Override
    public void run() {
        Random random = new Random();
        CrateItem nextCrateItem = crate.getCrateItems().get(random.nextInt(crate.getCrateItems().size()));
        
        for(int i = 10; i != 18; i++) {
            if(inventory.getItem(i) != null) {
                inventory.setItem(i-1, inventory.getItem(i));
            }
        }
        
        inventory.setItem(17, nextCrateItem.getItem());
        player.playSound(player.getLocation(), plugin.getVersion().getSound("UI_BUTTON_CLICK"), 10L, 10L);
        count++;
        if(count == 25) {
            this.win = nextCrateItem;
        }
        if(count == 30) {
            bukkitTask.cancel();
            win.runCommands(player);
            player.playSound(player.getLocation(), plugin.getVersion().getSound("ENTITY_PLAYER_LEVELUP"), 10L, 10L);
            player.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ITEMWON.getLocal().replaceAll("%win", win.getName()));
        }
    }
    
}
