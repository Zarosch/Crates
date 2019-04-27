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

    @Getter
    private final ArrayList<ItemStack> items;

    public CrateItem(String name, ItemStack item, ArrayList<String> commands, ArrayList<ItemStack> items) {
        this.name = name;
        this.item = item;
        this.commands = commands;
        this.items = items;
    }

    public void runCommands(Player player) {
        if (!commands.isEmpty()) {
            commands.forEach((command) -> {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%player", player.getName()));
            });
        }
        if (!items.isEmpty()) {
            items.forEach((item) -> {
                player.getInventory().addItem(item);
            });
        }
    }

}
