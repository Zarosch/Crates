package me.velz.crate.commands;

import java.io.IOException;
import java.util.ArrayList;
import me.velz.crate.Crates;
import me.velz.crate.objects.CrateChest;
import me.velz.crate.utils.ItemBuilder;
import me.velz.crate.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
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
            cs.sendMessage(MessageUtil.COMMAND_HELP_ADDCRATEV2.getLocal());
            cs.sendMessage(MessageUtil.COMMAND_HELP_REMOVECRATE.getLocal());
            cs.sendMessage(MessageUtil.COMMAND_HELP_ADDOPENER.getLocal());
            cs.sendMessage(MessageUtil.COMMAND_HELP_REMOVEOPENER.getLocal());
            cs.sendMessage(MessageUtil.COMMAND_HELP_SETMATERIAL.getLocal());
            cs.sendMessage("");
            return true;
        }
        
        if(args[0].equalsIgnoreCase("help")) {
            cs.sendMessage("");
            cs.sendMessage(MessageUtil.COMMAND_HELP_RELOAD.getLocal());
            cs.sendMessage(MessageUtil.COMMAND_HELP_GIVE.getLocal());
            cs.sendMessage(MessageUtil.COMMAND_HELP_LIST.getLocal());
            cs.sendMessage(MessageUtil.COMMAND_HELP_ADDITEM.getLocal());
            cs.sendMessage(MessageUtil.COMMAND_HELP_ADDCRATEV2.getLocal());
            cs.sendMessage(MessageUtil.COMMAND_HELP_REMOVECRATE.getLocal());
            cs.sendMessage(MessageUtil.COMMAND_HELP_ADDOPENER.getLocal());
            cs.sendMessage(MessageUtil.COMMAND_HELP_REMOVEOPENER.getLocal());
            cs.sendMessage(MessageUtil.COMMAND_HELP_SETMATERIAL.getLocal());
            cs.sendMessage("");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.getFileManager().load();
            cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.COMMAND_RELOADED.getLocal());
            return true;
        }

        if (args[0].equalsIgnoreCase("addopener")) {
            try {
                Player player = (Player) cs;
                Block block = player.getTargetBlock(null, 5);
                if (block.getLocation().distance(player.getLocation()) > 15) {
                    cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_BLOCKNOTFOUND.getLocal());
                    return true;
                }
                plugin.getOpeners().add(new CrateChest(block.getLocation()));
                ArrayList<String> locations = plugin.getFileManager().getCratesBuilder().getStringListAsArrayList("opener.locations");
                locations.add(block.getLocation().getWorld().getName() + ";" + block.getLocation().getX() + ";" + block.getLocation().getY() + ";" + block.getLocation().getZ());
                plugin.getFileManager().getCratesBuilder().set("opener.locations", locations);
                plugin.getFileManager().getCratesBuilder().save();
                plugin.getFileManager().load();
                player.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.COMMAND_ADDOPENER.getLocal());
            } catch (NullPointerException ex) {
                cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_BLOCKNOTFOUND.getLocal());
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("removeopener")) {
            try {
                Player player = (Player) cs;
                Block block = player.getTargetBlock(null, 5);
                if (block.getLocation().distance(player.getLocation()) > 15) {
                    cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_BLOCKNOTFOUND.getLocal());
                    return true;
                }
                plugin.getOpeners().add(new CrateChest(block.getLocation()));
                ArrayList<String> locations = plugin.getFileManager().getCratesBuilder().getStringListAsArrayList("opener.locations");
                if (locations.contains(block.getLocation().getWorld().getName() + ";" + block.getLocation().getX() + ";" + block.getLocation().getY() + ";" + block.getLocation().getZ())) {
                    locations.remove(block.getLocation().getWorld().getName() + ";" + block.getLocation().getX() + ";" + block.getLocation().getY() + ";" + block.getLocation().getZ());
                    plugin.getFileManager().getCratesBuilder().set("opener.locations", locations);
                    plugin.getFileManager().getCratesBuilder().save();
                    plugin.getFileManager().load();
                } else {
                    player.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_NOTOPENHERE.getLocal());
                    return true;
                }
            } catch (NullPointerException ex) {
                cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_BLOCKNOTFOUND.getLocal());
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("addcrate")) {
            if (args.length <= 3) {
                cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_SYNTAX.getLocal().replaceAll("%command", "/crate addcrate <crate> <key|crate> <itemname>"));
                return true;
            }
            if(!args[2].equalsIgnoreCase("key") && args[3].equalsIgnoreCase("crate")) {
                cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_SYNTAX.getLocal().replaceAll("%command", "/crate addcrate <crate> <key|crate> <itemname>"));
                return true;
            }
            String crate = args[1];
            if (plugin.getCrates().containsKey(crate)) {
                cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_CRATEFOUND.getLocal());
                return true;
            }

            String displayname = "";
            for (int i = 3; i != args.length; i++) {
                displayname = displayname + args[i] + " ";
            }
            displayname = displayname.substring(0, displayname.length() - 1);
            if(!displayname.startsWith("§")) {
                displayname = "§e" + displayname;
            }

            plugin.getFileManager().getCratesBuilder().set("crates." + crate + ".name", crate + " Crate");
            if(args[2].equalsIgnoreCase("key")) {
                plugin.getFileManager().getCratesBuilder().set("crates." + crate + ".type", "key");
            }
            if(args[2].equalsIgnoreCase("crate")) {
                plugin.getFileManager().getCratesBuilder().set("crates." + crate + ".type", "crate");
            }
            plugin.getFileManager().getCratesBuilder().set("crates." + crate + ".item", new ItemBuilder().setMaterial(Material.DRAGON_EGG).addEnchantment(Enchantment.LUCK, 1, false).setShowEnchant(false).setDisplayName(displayname).build());
            plugin.getFileManager().getCratesBuilder().save();
            plugin.getFileManager().load();
            cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.COMMAND_ADDCRATE_ADDED.getLocal().replaceAll("%crate", crate));
            return true;
        }
        
        if(args[0].equalsIgnoreCase("setmaterial")) {
            if (args.length <= 1) {
                cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_SYNTAX.getLocal().replaceAll("%command", "/crate setmaterial <crate>"));
                return true;
            }
            String crate = args[1];
            if (!plugin.getCrates().containsKey(crate)) {
                cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_CRATENOTFOUND.getLocal());
                return true;
            }
            
            Player player = (Player)cs;
            if(plugin.getVersion().getMainHandItem(player).getType() == Material.AIR) {
                cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_NOITEMINHAND.getLocal());
                return true;
            }
            
            plugin.getFileManager().getCratesBuilder().set("crates." + crate + ".item.material", plugin.getVersion().getMainHandItem(player).getType().toString());
            plugin.getFileManager().getCratesBuilder().save();
            plugin.getFileManager().load();
            cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.COMMAND_SETMATERIAL.getLocal().replaceAll("%crate", crate).replaceAll("%item", plugin.getVersion().getMainHandItem(player).getType().toString()));
            return true;
        }

        if (args[0].equalsIgnoreCase("removecrate")) {
            if (args.length != 2) {
                cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_SYNTAX.getLocal().replaceAll("%command", "/crate removecrate <crate>"));
                return true;
            }
            String crate = args[1];
            if (!plugin.getCrates().containsKey(crate)) {
                cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_CRATENOTFOUND.getLocal());
                return true;
            }

            plugin.getFileManager().getCratesBuilder().getConfiguration().set("crates." + crate, null);
            plugin.getFileManager().getCratesBuilder().save();
            plugin.getFileManager().load();
            cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.COMMAND_REMOVECRATE.getLocal().replaceAll("%crate", crate));
            return true;
        }

        if (args[0].equalsIgnoreCase("additem")) {
            if (args.length != 2) {
                cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_SYNTAX.getLocal().replaceAll("%command", "/crate additem <crate>"));
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

            boolean all = false;

            if (args.length == 3) {
                if (Bukkit.getPlayer(args[2]) == null) {
                    cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_PLAYERNOTFOUND.getLocal());
                    return true;
                }
                if (args[2].equalsIgnoreCase("@all")) {
                    all = true;
                } else {
                    player = Bukkit.getPlayer(args[2]);
                }
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
            if (all) {
                for (Player a : Bukkit.getOnlinePlayers()) {
                    a.getInventory().addItem(plugin.getCrates().get(crate).getItem());
                }
            } else {
                player.getInventory().addItem(plugin.getCrates().get(crate).getItem());
            }
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
