package me.velz.crate.utils;

import java.util.ArrayList;
import lombok.Getter;
import me.velz.crate.Crates;
import me.velz.crate.objects.Crate;
import me.velz.crate.objects.CrateChest;
import me.velz.crate.objects.CrateItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
        this.getConfigBuilder().addDefault("autoupdate", true);
        this.getConfigBuilder().addDefault("options.animation", true);
        this.getConfigBuilder().save();
        if (!getCratesBuilder().getConfiguration().contains("crates")) {
            this.getCratesBuilder().addDefault("crates.money.name", "Money Crate");
            this.getCratesBuilder().addDefault("crates.money.type", "crate");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("§7Money! Anybody needs money.");
            this.getCratesBuilder().addDefault("crates.money.item", new ItemBuilder().setMaterial(Material.GOLD_INGOT).setShowEnchant(false).setDisplayName("§6Money Crate").setLore(lore).addEnchantment(Enchantment.LUCK, 1, false).build());
            this.getCratesBuilder().addDefault("crates.money.content.rivet.name", "§cRivet");
            this.getCratesBuilder().addDefault("crates.money.content.rivet.chance", 50);
            this.getCratesBuilder().addDefault("crates.money.content.rivet.item", new ItemBuilder().setMaterial(Material.BARRIER).setDisplayName("§cRivet").build());
            this.getCratesBuilder().addDefault("crates.money.content.rivet.commands", new String[]{});

            this.getCratesBuilder().addDefault("crates.money.content.100-dollar.name", "§6100 Dollar!");
            this.getCratesBuilder().addDefault("crates.money.content.100-dollar.chance", 30);
            this.getCratesBuilder().addDefault("crates.money.content.100-dollar.item", new ItemBuilder().setMaterial(Material.GOLD_INGOT).setDisplayName("§6100 Dollar!").build());
            this.getCratesBuilder().addDefault("crates.money.content.100-dollar.commands", new String[]{"money give %player 100"});

            this.getCratesBuilder().addDefault("crates.money.content.250-dollar.name", "§6250 Dollar!");
            this.getCratesBuilder().addDefault("crates.money.content.250-dollar.chance", 15);
            this.getCratesBuilder().addDefault("crates.money.content.250-dollar.item", new ItemBuilder().setMaterial(Material.GOLD_INGOT).setDisplayName("§6100 Dollar!").build());
            this.getCratesBuilder().addDefault("crates.money.content.250-dollar.commands", new String[]{"money give %player 250"});

            this.getCratesBuilder().addDefault("crates.money.content.500-dollar.name", "§6500 Dollar!");
            this.getCratesBuilder().addDefault("crates.money.content.500-dollar.chance", 5);
            this.getCratesBuilder().addDefault("crates.money.content.500-dollar.item", new ItemBuilder().setMaterial(Material.GOLD_BLOCK).setDisplayName("§6500 Dollar!").build());
            this.getCratesBuilder().addDefault("crates.money.content.500-dollar.commands", new String[]{"money give %player 500"});

            this.getCratesBuilder().addDefault("crates.money.inventory-background", plugin.getVersion().getDefaultCrateLimeGlass());

            this.getCratesBuilder().addDefault("crates.food.name", "Food Key");
            this.getCratesBuilder().addDefault("crates.food.type", "key");
            lore.clear();
            lore.add("§7Food! Anybody needs food.");
            this.getCratesBuilder().addDefault("crates.food.item", new ItemBuilder().setMaterial(Material.TRIPWIRE_HOOK).setShowEnchant(false).setDisplayName("§aFood Key").setLore(lore).addEnchantment(Enchantment.LUCK, 1, false).build());
            this.getCratesBuilder().addDefault("crates.food.content.melon.name", "§aMelon");
            this.getCratesBuilder().addDefault("crates.food.content.melon.chance", 1);
            this.getCratesBuilder().addDefault("crates.food.content.melon.item", new ItemBuilder().setMaterial(Material.MELON).setDisplayName("§aMelon").setAmount(16).build());
            this.getCratesBuilder().addDefault("crates.food.content.melon.items.item", new ItemBuilder().setMaterial(Material.MELON).setAmount(16).build());

            this.getCratesBuilder().addDefault("crates.food.content.bread.name", "§6Bread");
            this.getCratesBuilder().addDefault("crates.food.content.bread.chance", 1);
            this.getCratesBuilder().addDefault("crates.food.content.bread.item", new ItemBuilder().setMaterial(Material.BREAD).setDisplayName("§6Bread").setAmount(16).build());
            this.getCratesBuilder().addDefault("crates.food.content.bread.items.item", new ItemBuilder().setMaterial(Material.BREAD).setAmount(16).build());

            this.getCratesBuilder().addDefault("crates.food.content.apple.name", "§cApple");
            this.getCratesBuilder().addDefault("crates.food.content.apple.chance", 1);
            this.getCratesBuilder().addDefault("crates.food.content.apple.item", new ItemBuilder().setMaterial(Material.APPLE).setDisplayName("§cApple").setAmount(16).build());
            this.getCratesBuilder().addDefault("crates.food.content.apple.items.item", new ItemBuilder().setMaterial(Material.APPLE).setAmount(16).build());

            this.getCratesBuilder().addDefault("crates.food.content.cookie.name", "§6Cookie");
            this.getCratesBuilder().addDefault("crates.food.content.cookie.chance", 1);
            this.getCratesBuilder().addDefault("crates.food.content.cookie.item", new ItemBuilder().setMaterial(Material.COOKIE).setDisplayName("§6Cookie").setAmount(16).build());
            this.getCratesBuilder().addDefault("crates.food.content.cookie.items.item", new ItemBuilder().setMaterial(Material.COOKIE).setAmount(16).build());

            this.getCratesBuilder().addDefault("crates.food.content.beef.name", "§dBeef");
            this.getCratesBuilder().addDefault("crates.food.content.beef.chance", 1);
            this.getCratesBuilder().addDefault("crates.food.content.beef.item", new ItemBuilder().setMaterial(Material.COOKED_BEEF).setDisplayName("§dBeef").setAmount(16).build());
            this.getCratesBuilder().addDefault("crates.food.content.beef.items.item", new ItemBuilder().setMaterial(Material.COOKED_BEEF).setAmount(16).build());

            this.getCratesBuilder().addDefault("crates.food.content.chicken.name", "§dChicken");
            this.getCratesBuilder().addDefault("crates.food.content.chicken.chance", 1);
            this.getCratesBuilder().addDefault("crates.food.content.chicken.item", new ItemBuilder().setMaterial(Material.COOKED_CHICKEN).setDisplayName("§dChicken").setAmount(16).build());
            this.getCratesBuilder().addDefault("crates.food.content.chicken.items.item", new ItemBuilder().setMaterial(Material.COOKED_CHICKEN).setAmount(16).build());
        }
        if (!getCratesBuilder().getConfiguration().contains("opener.locations")) {
            this.getCratesBuilder().addDefault("opener.locations", new String[]{});
        }
        this.getCratesBuilder().save();
    }

    public void load() {
        this.getConfigBuilder().load();
        this.getCratesBuilder().load();
        this.language = this.getConfigBuilder().getString("language");
        this.animation = this.getConfigBuilder().getBoolean("options.animation");
        plugin.getCrates().clear();
        plugin.getOpeners().clear();

        ArrayList<String> openerLocations = getCratesBuilder().getStringListAsArrayList("opener.locations");
        for (String location : openerLocations) {
            String[] splitted = location.split(";");
            plugin.getOpeners().add(new CrateChest(new Location(Bukkit.getWorld(splitted[0]), Double.valueOf(splitted[1]), Double.valueOf(splitted[2]), Double.valueOf(splitted[3]))));
        }

        for (String crates : getCratesBuilder().getConfiguration().getConfigurationSection("crates").getKeys(false)) {
            String name = getCratesBuilder().getString("crates." + crates + ".name");
            if (!getCratesBuilder().getConfiguration().contains("crates." + crates + ".type")) {
                getCratesBuilder().set("crates." + crates + ".type", "crate");
                getCratesBuilder().save();
            }
            String type = getCratesBuilder().getString("crates." + crates + ".type");
            ItemStack item = getCratesBuilder().getItemStack("crates." + crates + ".item");
            ArrayList<CrateItem> items = new ArrayList<>();
            ItemStack background = null;
            if (getCratesBuilder().getConfiguration().contains("crates." + crates + ".inventory-background")) {
                background = getCratesBuilder().getItemStack("crates." + crates + ".inventory-background");
            }
            if (getCratesBuilder().getConfiguration().contains("crates." + crates + ".content")) {
                for (String content : getCratesBuilder().getConfiguration().getConfigurationSection("crates." + crates + ".content").getKeys(false)) {
                    String contentName = getCratesBuilder().getString("crates." + crates + ".content." + content + ".name");
                    ItemStack contentItem = getCratesBuilder().getItemStack("crates." + crates + ".content." + content + ".item");
                    ArrayList<String> commands = new ArrayList<>();
                    int chance = 1;
                    if (!getCratesBuilder().getConfiguration().contains("crates." + crates + ".content." + content + ".chance")) {
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
                    for (int c = 0; c != chance; c++) {
                        items.add(crateItem);
                    }
                }
            }
            Crate crate = new Crate(name, item, background, items, type);
            plugin.getCrates().put(crates, crate);
        }
        MessageUtil.load();
    }
}
