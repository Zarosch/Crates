package me.velz.crate.commands;

import me.velz.crate.Crates;
import me.velz.crate.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CratesCommand implements CommandExecutor {

    private final Crates plugin;

    public CratesCommand(Crates plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {

        if (!cs.hasPermission("crates.command")) {
            cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_NOPERMISSIONS.getLocal());
            return true;
        }

        if (args.length == 0) {
            cs.sendMessage("");
            cs.sendMessage(MessageUtil.COMMAND_HELP_RELOAD.getLocal());
            cs.sendMessage(MessageUtil.COMMAND_HELP_GIVE.getLocal());
            cs.sendMessage(MessageUtil.COMMAND_HELP_LIST.getLocal());
            cs.sendMessage(MessageUtil.COMMAND_HELP_ADDITEM.getLocal());
            cs.sendMessage("");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.getFileManager().load();
            cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.COMMAND_RELOADED.getLocal());
            return true;
        }

        if (args[0].equalsIgnoreCase("additem")) {
            if (args.length != 2) {
                cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_SYNTAX.getLocal().replaceAll("%command", "/crate additem [crate]"));
                return true;
            }
            if (!(cs instanceof Player)) {
                cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_PLAYERONLY.getLocal());
                return true;
            }

            Player player = (Player) cs;
            String crate = args[1];

            if (!plugin.getCrates().containsKey(crate)) {
                cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_CRATENOTFOUND.getLocal());
                return true;
            }

            Inventory inventory = Bukkit.createInventory(null, 27, "Crate: " + crate);
            player.openInventory(inventory);
            cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.COMMAND_ADDITEM_OPEN.getLocal());
            return true;
        }

        if (args[0].equalsIgnoreCase("give")) {
            Player player = null;

            if (args.length != 3 && args.length != 2) {
                cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_SYNTAX.getLocal().replaceAll("%command", "/crate give [crate] [player]"));
                return true;
            }

            if (args.length == 3) {
                if (Bukkit.getPlayer(args[2]) == null) {
                    cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_PLAYERNOTFOUND.getLocal());
                    return true;
                }
                player = Bukkit.getPlayer(args[2]);
            } else if (cs instanceof Player) {
                player = (Player) cs;
            } else {
                cs.sendMessage("This command can only be used in the console with the player argument");
                return true;
            }

            String crate = args[1];
            if (!plugin.getCrates().containsKey(crate)) {
                cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_CRATENOTFOUND.getLocal());
                return true;
            }

            player.getInventory().addItem(plugin.getCrates().get(crate).getItem());
            cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.COMMAND_GIVE.getLocal().replaceAll("%player", player.getName()).replaceAll("%crate", crate));
            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {
            String crates = "";
            for (String crate : plugin.getCrates().keySet()) {
                crates = crates + crate + ", ";
            }
            crates = crates.substring(0, crates.length() - 2);
            cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.COMMAND_LIST.getLocal().replaceAll("%crates", crates));
            return true;
        }

        return false;
    }

}
