package me.velz.crate.utils;

import lombok.Getter;
import me.velz.crate.Crates;

public enum MessageUtil {

    PREFIX("", "§f[§eCrates§f] ", "§f[§eCrates§f] ", "§f[§eCrates§f] "),
    UPDATE("", "§cPlease Update! §e%url", "§cBitte Updaten! §e%url", "§cPor favor actualice! §e%url"),
    UPDATE_RESTART("", "§cCrates have §eupdated automatically§c, please §erestart your server§c!", "§cCrates hat sich §eautomatisch geupdatet§c, bitte §estarte deinen Server neu§c!", "§cLas cajas se han actualizado automáticamente, reinicie su servidor!"),
    ERROR_NOPERMISSIONS("", "§cYou dont have permissions.", "§cDazu hast du keine Berechtigung.", "§cNo tienes permiso."),
    ERROR_PLAYERNOTFOUND("", "§cThis player can not be found.", "§cDieser Spieler konnte nicht gefunden werden.", "§cJugador no encontrado"),
    ERROR_NONUMBER("", "§cPlease enter a valid number.", "§cBitte gebe eine gültige Zahl ein.", "§cPor favor ingresa un número válido."),
    ERROR_SYNTAX("", "§cSyntax error, please use: %command", "§cSyntax Fehler, bitte nutze: %command", "§cError de sintaxis, por favor use: %command"),
    ERROR_CRATENOTFOUND("", "§cThis crate can not be found.", "§cDieses Crate konnte nicht gefunden werden.", "§cNo se pudo encontrar esa crate."),
    ERROR_CRATEFOUND("", "§cThis crate already exists.", "§cDieses Crate existiert bereits.", "§cEste crate ya existe."),
    ERROR_PLAYERONLY("", "§cThis command is only for players.", "§cDieser Befehl ist nur für Spieler.", "§cEste comando es solo para jugadores."),
    ITEMWON("", "§aYou have §f§o%win §awon.", "§aDu hast §f§o%win §agewonnen.", "§aHas ganado: §f§o%win"),
    COMMAND_HELP_RELOAD("", "§6/crates reload §fReload Configuration", "§6/crates reload §fKonfiguration neuladen", "§6/crates reload §fRecarga la configuración."),
    COMMAND_HELP_GIVE("", "§6/crates give <Crate> [Player] §fGives you a crate", "§6/crates give <Crate> [Spieler] §fGebe dir ein Crate", "§6/crates give <crate> [jugador] §fDa una caja."),
    COMMAND_HELP_LIST("", "§6/crates list §fList of all crates.", "§6/crates list §fListe aller Crates", "§6/crates list §fLista de crates disponibles."),
    COMMAND_HELP_ADDITEM("", "§6/crates additem <Crate> §fAdd Item to crate content", "§6/crates additem <Crate> §fItem zum Crate Content hinzufügen.", "§6/crates additem <Crate> §fAñadir artículo al contenido de la caja"),
    COMMAND_HELP_ADDCRATE("", "§6/crates addcrate <Crate> §fAdd a new crate", "§6/crates addcrate <Crate> §fCrate hinzufügen", ""),
    COMMAND_RELOADED("", "§aConfiguration reloaded.", "§aKonfiguration neugeladen", "§aConfiguración recargada."),
    COMMAND_GIVE("", "§aYou have give §f§o%player §athe crate §f§o%crate§a.", "§aDu hast §f§o%player §adie Crate §f§o%crate§a gegeben.", "§aLe has dado una crate §f§o%crate§a a §f§o%player§a."),
    COMMAND_LIST("", "§6All crates:§e %crates", "§6Alle verfügbaren Crates:§e %crates", "§6Crates disponibles:§e %crates"),
    COMMAND_ADDITEM_OPEN("", "§6Put all Items they you will add in this Inventory.", "§6Packe alle Items die du hinzufügen willst in dieses Inventar.", "Empaque todos los artículos que desee agregar a este inventario."),
    COMMAND_ADDITEM_ADDED("", "§aYour Items are added and can now edited in the crates.yml", "§aDeine Items wurden hinzugefügt und können nun in der crates.yml editiert werden.", "§aSus artículos se han agregado y ahora se pueden editar en crates.yml."),
    COMMAND_ADDCRATE_NOITEMINHAND("", "§cYou have to hold an item for the crate in your hand.", "§cDu musst ein Item für das Crate in der Hand halten.", "§cTienes que sostener un objeto para el cajón en tu mano."),
    COMMAND_ADDCRATE_NODISPLAYNAME("", "§cThe crate item must have a unique name.", "§cDas Crate Item muss einen einzigartigen namen haben.", "§cEl elemento de caja debe tener un nombre único."),
    COMMAND_ADDCRATE_ADDED("", "§aYou have the crate §f§o%crate §aadded.", "§aDu hast das Crate §f§o%crate §ahinzugefügt.", "§aUsted tiene el cajón §f§o%crate §aañadido.");

    @Getter
    private String local;

    @Getter
    private final String english, german, spanish;

    private MessageUtil(String local, String english, String german, String spanish) {
        this.local = local;
        this.english = english;
        this.german = german;
        this.spanish = spanish;
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

        FileBuilder message_es = new FileBuilder("plugins/Crates", "messages_es.yml");
        for (MessageUtil m : MessageUtil.values()) {
            message_es.addDefault("message." + m.toString().replaceAll("_", ".").toLowerCase(), m.spanish.replaceAll("§", "&"));
        }
        message_es.save();

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
