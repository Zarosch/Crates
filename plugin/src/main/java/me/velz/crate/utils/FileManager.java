package me.velz.crate.utils;

import java.util.ArrayList;
import lombok.Getter;
import me.velz.crate.Crates;
import me.velz.crate.objects.Crate;
import me.velz.crate.objects.CrateItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class FileManager {

    private final Crates plugin;

    public FileManager(Crates plugin) {
        this.plugin = plugin;
    }

    @Getter
    private final FileBuilder cratesBuilder = new FileBuilder("plugins/Crates/", "crates.yml");

    @Getter
    private final FileBuilder configBuilder = new FileBuilder("plugins/Crates/", "config.yml");

    @Getter
    private boolean animation;

    @Getter
    private String language;

    public void setDefaults() {
        this.getConfigBuilder().addDefault("language", "en");
        this.getConfigBuilder().addDefault("options.animation", true);
        this.getConfigBuilder().save();
        if (!getCratesBuilder().getConfiguration().contains("crates")) {
            this.getCratesBuilder().addDefault("crates.epic.name", "Epic Crate");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("§7Lore..");
            this.getCratesBuilder().addDefault("crates.epic.item", new ItemBuilder().setMaterial(Material.DRAGON_EGG).setDisplayName("§d§lEpic Crate").setLore(lore).addEnchantment(Enchantment.LUCK, 1, false).build());
            this.getCratesBuilder().addDefault("crates.epic.content.egg.name", "1x Egg");
            this.getCratesBuilder().addDefault("crates.epic.content.egg.chance", 10);
            this.getCratesBuilder().addDefault("crates.epic.content.egg.item", new ItemBuilder().setMaterial(Material.EGG).setDisplayName("Egg").build());
            this.getCratesBuilder().addDefault("crates.epic.content.egg.commands", new String[]{
                "give %player minecraft:egg 1"
            });
            this.getCratesBuilder().addDefault("crates.epic.content.melon.name", "1x Piece of melone");
            this.getCratesBuilder().addDefault("crates.epic.content.melon.chance", 30);
            this.getCratesBuilder().addDefault("crates.epic.content.melon.item", new ItemBuilder().setMaterial(Material.MELON).setDisplayName("Piece of melone").build());
            this.getCratesBuilder().addDefault("crates.epic.content.melon.items.melon", new ItemBuilder().setMaterial(Material.MELON).build());
            this.getCratesBuilder().addDefault("crates.epic.content.melon.commands", new String[]{
                "give %player minecraft:melon 1"
            });
        }
        this.getCratesBuilder().save();
    }

    public void load() {
        this.getConfigBuilder().load();
        this.getCratesBuilder().load();
        this.language = this.getConfigBuilder().getString("language");
        this.animation = this.getConfigBuilder().getBoolean("options.animation");
        plugin.getCrates().clear();
        for (String crates : getCratesBuilder().getConfiguration().getConfigurationSection("crates").getKeys(false)) {
            String name = getCratesBuilder().getString("crates." + crates + ".name");
            ItemStack item = getCratesBuilder().getItemStack("crates." + crates + ".item");
            ArrayList<CrateItem> items = new ArrayList<>();
            for (String content : getCratesBuilder().getConfiguration().getConfigurationSection("crates." + crates + ".content").getKeys(false)) {
                String contentName = getCratesBuilder().getString("crates." + crates + ".content." + content + ".name");
                ItemStack contentItem = getCratesBuilder().getItemStack("crates." + crates + ".content." + content + ".item");
                ArrayList<String> commands = new ArrayList<>();
                int chance = 1;
                if(!getCratesBuilder().getConfiguration().contains("crates." + crates + ".content." + content + ".chance")) {
                    getCratesBuilder().addDefault("crates." + crates + ".content." + content + ".chance", 1);
                    getCratesBuilder().save();
                } else {
                    chance = getCratesBuilder().getInt("crates." + crates + ".content." + content + ".chance");
                }
                if (getCratesBuilder().getConfiguration().contains("crates." + crates + ".content." + content + ".commands")) {
                    for (String command : getCratesBuilder().getStringList("crates." + crates + ".content." + content + ".commands")) {
                        commands.add(command);
                    }
                }
                ArrayList<ItemStack> i = new ArrayList<>();
                if (getCratesBuilder().getConfiguration().contains("crates." + crates + ".content." + content + ".items")) {
                    for (String crateItems : getCratesBuilder().getConfiguration().getConfigurationSection("crates." + crates + ".content." + content + ".items").getKeys(false)) {
                        i.add(getCratesBuilder().getItemStack("crates." + crates + ".content." + content + ".items." + crateItems));
                    }
                }
                CrateItem crateItem = new CrateItem(contentName, contentItem, commands, i);
                for(int c = 0; c != chance; c++) {
                    items.add(crateItem);
                }
            }
            Crate crate = new Crate(name, item, items);
            plugin.getCrates().put(crates, crate);
        }
        MessageUtil.load();
    }
}
