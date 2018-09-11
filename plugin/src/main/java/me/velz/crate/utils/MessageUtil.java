package me.velz.crate.utils;

import lombok.Getter;

public enum MessageUtil {

    PREFIX("§f[§eCrates§f] "),
    
    ERROR_NOPERMISSIONS("§cYou dont have permissions."),
    ERROR_PLAYERNOTFOUND("§cThis player can not be found."),
    ERROR_NONUMBER("§cPlease enter a valid number."),
    ERROR_SYNTAX("§cSyntax error, please use: %command"),
    ERROR_CRATENOTFOUND("§cThis crate can not be found."),
    
    ITEMWON("§aYou have §f§o%win §awon."),
    
    COMMAND_HELP_RELOAD("§6/crates reload §fReload Configuration"),
    COMMAND_HELP_GIVE("§6/crates give <Crate> [Spieler] §fGives you a crate"),
    COMMAND_HELP_LIST("§6/crates list §fList of all crates."),
    COMMAND_RELOADED("§aConfiguration reloaded."),
    COMMAND_GIVE("§aYou have give §f§o%player §athe crate §f§o%crate§a."),
    COMMAND_LIST("§6All crates: %crates");

    @Getter
    private String local;

    private MessageUtil(String local) {
        this.local = local;
    }

    public static void load() {
        FileBuilder message = new FileBuilder("plugins/Crates", "messages.yml");
        for (MessageUtil m : MessageUtil.values()) {
            message.addDefault("message." + m.toString().replaceAll("_", ".").toLowerCase(), m.local.replaceAll("§", "&"));
        }
        message.save();
        for (MessageUtil m : MessageUtil.values()) {
            m.local = message.getConfiguration().getString("message." + m.toString().replaceAll("_", ".").toLowerCase()).replaceAll("&", "§");
        }
    }

}
