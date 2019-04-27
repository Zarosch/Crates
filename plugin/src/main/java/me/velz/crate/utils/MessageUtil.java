package me.velz.crate.utils;

import lombok.Getter;
import me.velz.crate.Crates;

public enum MessageUtil {

    PREFIX("", "§f[§eCrates§f] ", "§f[§eCrates§f] "),
    ERROR_NOPERMISSIONS("", "§cYou dont have permissions.", "§cDazu hast du keine Berechtigung."),
    ERROR_PLAYERNOTFOUND("", "§cThis player can not be found.", "§cDieser Spieler konnte nicht gefunden werden."),
    ERROR_NONUMBER("", "§cPlease enter a valid number.", "§cBitte gebe eine gültige Zahl ein."),
    ERROR_SYNTAX("", "§cSyntax error, please use: %command", "§cSyntax Fehler, bitte nutze: %command"),
    ERROR_CRATENOTFOUND("", "§cThis crate can not be found.", "§cDieses Crate konnte nicht gefunden werden."),
    ITEMWON("", "§aYou have §f§o%win §awon.", "§aDu hast §f§o%win §agewonnen."),
    COMMAND_HELP_RELOAD("", "§6/crates reload §fReload Configuration", "§6/crates reload §fKonfiguration neuladen"),
    COMMAND_HELP_GIVE("", "§6/crates give <Crate> [Player] §fGives you a crate", "§6/crates give <Crate> [Spieler] §fGebe dir ein Crate"),
    COMMAND_HELP_LIST("", "§6/crates list §fList of all crates.", "§6/crates list §fListe aller Crates"),
    COMMAND_RELOADED("", "§aConfiguration reloaded.", "§aKonfiguration neugeladen"),
    COMMAND_GIVE("", "§aYou have give §f§o%player §athe crate §f§o%crate§a.", "§aDu hast §f§o%player §adie Crate §f§o%crate§a gegeben."),
    COMMAND_LIST("", "§6All crates:§e %crates", "§6Alle verfügbaren Crates:§e %crates");

    @Getter
    private String local;

    @Getter
    private final String english, german;

    private MessageUtil(String local, String english, String german) {
        this.local = local;
        this.english = english;
        this.german = german;
    }

    public static void load() {
        FileBuilder message_en = new FileBuilder("plugins/Crates", "messages_en.yml");
        for (MessageUtil m : MessageUtil.values()) {
            message_en.addDefault("message." + m.toString().replaceAll("_", ".").toLowerCase(), m.english.replaceAll("§", "&"));
        }
        message_en.save();

        FileBuilder message_de = new FileBuilder("plugins/Crates", "messages_de.yml");
        for (MessageUtil m : MessageUtil.values()) {
            message_de.addDefault("message." + m.toString().replaceAll("_", ".").toLowerCase(), m.german.replaceAll("§", "&"));
        }
        message_de.save();

        FileBuilder message = new FileBuilder("plugins/Crates", "messages_" + Crates.getPlugin().getFileManager().getLanguage() + ".yml");
        if (!message.getFile().exists()) {
            message = new FileBuilder("plugins/Crates", "messages_" + Crates.getPlugin().getFileManager().getLanguage() + ".yml");
        }
        for (MessageUtil m : MessageUtil.values()) {
            message.addDefault("message." + m.toString().replaceAll("_", ".").toLowerCase(), m.english.replaceAll("§", "&"));
        }
        for (MessageUtil m : MessageUtil.values()) {
            m.local = message.getConfiguration().getString("message." + m.toString().replaceAll("_", ".").toLowerCase()).replaceAll("&", "§");
        }
        message.save();
    }

}
