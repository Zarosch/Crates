package me.velz.crate.objects;

import java.util.ArrayList;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CrateItem {
    
    @Getter
    private final String name;
    
    @Getter
    private final ItemStack item;
    
    @Getter
    private final ArrayList<String> commands;

    public CrateItem(String name, ItemStack item, ArrayList<String> commands) {
        this.name = name;
        this.item = item;
        this.commands = commands;
    }
    
    public void runCommands(Player player) {
        commands.forEach((command) -> {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%player", player.getName()));
        });
    }

}
