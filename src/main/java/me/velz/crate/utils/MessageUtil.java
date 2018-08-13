package me.velz.crate.utils;

import lombok.Getter;

public enum MessageUtil {

    PREFIX("§f[§eCrates§f] "),
    
    ERROR_NOPERMISSIONS("§cDazu hast du keine Berechtigung."),
    ERROR_PLAYERNOTFOUND("§cDieser Spieler konnte nicht gefunden werden."),
    ERROR_NONUMBER("§cDu musst eine gültige Zahl angeben."),
    ERROR_SYNTAX("§cSyntax Fehler, bitte nutze: %command"),
    ERROR_CRATENOTFOUND("§cDiese Crate konnte nicht gefunden werden."),
    
    ITEMWON("§aDu hast §f§o%win §agewonnen."),
    
    COMMAND_HELP_RELOAD("§6/crates reload §fKonfiguration neuladen"),
    COMMAND_HELP_GIVE("§6/crates give <Crate> [Spieler] §fCrate geben"),
    COMMAND_HELP_LIST("§6/crates list §fCrate geben"),
    COMMAND_RELOADED("§aKonfiguration wurde neugeladen."),
    COMMAND_GIVE("§aDu hast §f§o%player §adie Crate §f§o%crate §agegeben."),
    COMMAND_LIST("§6Alle Crates: %crates");

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

    public static void save(MessageUtil m) {
        FileBuilder message = new FileBuilder("plugins/Crates", "messages.yml");
    }

}
