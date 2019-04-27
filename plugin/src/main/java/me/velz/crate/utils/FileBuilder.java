package me.velz.crate.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import me.velz.crate.Crates;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class FileBuilder {

    @Getter
    private File file;

    @Getter
    private FileConfiguration configuration;

    @Getter
    private final String path, filename;

    public FileBuilder(String path) {
        this.path = path;
        this.filename = null;
        load();
    }

    public FileBuilder(String path, String filename) {
        this.path = path;
        this.filename = filename;
        load();
    }

    //<editor-fold defaultstate="collapsed" desc="addDefault">
    public void addDefault(String path, String s) {
        if (!this.configuration.contains(path)) {
            this.configuration.set(path, s);
        }
    }

    public void addDefault(String path, Boolean b) {
        if (!this.configuration.contains(path)) {
            this.configuration.set(path, b);
        }
    }

    public void addDefault(String path, Integer i) {
        if (!this.configuration.contains(path)) {
            this.configuration.set(path, i);
        }
    }

    public void addDefault(String path, Double d) {
        if (!this.configuration.contains(path)) {
            this.configuration.set(path, d);
        }
    }

    public void addDefault(String path, Long l) {
        if (!this.configuration.contains(path)) {
            this.configuration.set(path, l);
        }
    }

    public void addDefault(String path, String[] list) {
        if (!this.configuration.contains(path)) {
            this.configuration.set(path, list);
        }
    }

    public void addDefault(String path, ItemStack stack) {
        if (!this.configuration.contains(path)) {
            this.configuration.set(path + ".material", stack.getType().toString());
            this.configuration.set(path + ".amount", stack.getAmount());
            this.configuration.set(path + ".durability", stack.getDurability());
            if (stack.getItemMeta() != null) {
                if (stack.getItemMeta().getDisplayName() != null) {
                    this.configuration.set(path + ".meta.displayName", stack.getItemMeta().getDisplayName());
                }
                if (stack.getItemMeta().getLore() != null) {
                    this.configuration.set(path + ".meta.lore", stack.getItemMeta().getLore());
                }
                if (Crates.getPlugin().getVersion().isUnbreakable(stack)) {
                    this.configuration.set(path + ".meta.unbreakable", true);
                }
                try {
                    LeatherArmorMeta armorMeta = (LeatherArmorMeta) stack.getItemMeta();
                    if (armorMeta.getColor() != null) {
                        this.configuration.set(path + ".meta.armorColor", armorMeta.getColor().toString());
                    }
                } catch (ClassCastException ex) {
                }
                try {
                    SkullMeta skullMeta = (SkullMeta) stack.getItemMeta();
                    if (Crates.getPlugin().getVersion().getSkullOwner(skullMeta) != null) {
                        this.configuration.set(path + ".meta.owningPlayer", Crates.getPlugin().getVersion().getSkullOwner(skullMeta));
                    }
                } catch (ClassCastException ex) {
                }

                if (stack.getEnchantments() != null) {
                    List<String> safe = new ArrayList<>();
                    List<String> unsafe = new ArrayList<>();
                    if (!stack.getEnchantments().isEmpty()) {
                        for (Enchantment enchant : stack.getEnchantments().keySet()) {
                            if (ItemBuilder.getPossibleEnchantments(stack).contains(enchant)) {
                                safe.add(enchant.getName() + ":" + stack.getEnchantments().get(enchant));
                            } else {
                                unsafe.add(enchant.getName() + ":" + stack.getEnchantments().get(enchant));
                            }
                        }
                    }
                    if (!safe.isEmpty()) {
                        this.configuration.set(path + ".enchantments.safe", safe);
                    }
                    if (!unsafe.isEmpty()) {
                        this.configuration.set(path + ".enchantments.unsafe", unsafe);
                    }
                }
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="set">
    public void set(String path, String s) {
        this.configuration.set(path, s);
    }

    public void set(String path, Boolean b) {
        this.configuration.set(path, b);
    }

    public void set(String path, Integer i) {
        this.configuration.set(path, i);
    }

    public void set(String path, Double d) {
        this.configuration.set(path, d);
    }

    public void set(String path, Long l) {
        this.configuration.set(path, l);
    }

    public void set(String path, List<String> list) {
        this.configuration.set(path, list);
    }

    public void set(String path, String[] list) {
        this.configuration.set(path, list);
    }

    public void set(String path, Location loc) {
        this.configuration.set(path + ".world", loc.getWorld().getName());
        this.configuration.set(path + ".x", loc.getX());
        this.configuration.set(path + ".y", loc.getY());
        this.configuration.set(path + ".z", loc.getZ());
        this.configuration.set(path + ".yaw", loc.getYaw());
        this.configuration.set(path + ".pitch", loc.getPitch());
    }

    public void set(String path, ItemStack stack) {
        this.configuration.set(path + ".material", stack.getType().toString());
        this.configuration.set(path + ".amount", stack.getAmount());
        this.configuration.set(path + ".durability", stack.getDurability());
        if (stack.getItemMeta() != null) {
            if (stack.getItemMeta().getDisplayName() != null) {
                this.configuration.set(path + ".meta.displayName", stack.getItemMeta().getDisplayName());
            }
            if (stack.getItemMeta().getLore() != null) {
                this.configuration.set(path + ".meta.lore", stack.getItemMeta().getLore());
            }
            if (Crates.getPlugin().getVersion().isUnbreakable(stack)) {
                this.configuration.set(path + ".meta.unbreakable", true);
            }
            if (stack.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ENCHANTS)) {
                this.configuration.set(path + ".meta.showenchant", false);
            }

            if (stack.getItemMeta() instanceof LeatherArmorMeta) {
                LeatherArmorMeta armorMeta = (LeatherArmorMeta) stack.getItemMeta();
                if (armorMeta.getColor() != null) {
                    this.configuration.set(path + ".meta.armorColor", armorMeta.getColor().asRGB());
                }
            }

            if (stack.getItemMeta() instanceof SkullMeta) {
                SkullMeta skullMeta = (SkullMeta) stack.getItemMeta();
                if (Crates.getPlugin().getVersion().getSkullOwner(skullMeta) != null) {
                    this.configuration.set(path + ".meta.owningPlayer", Crates.getPlugin().getVersion().getSkullOwner(skullMeta));
                }
            }
        }
        if (stack.getEnchantments() != null) {
            List<String> safe = new ArrayList<>();
            List<String> unsafe = new ArrayList<>();
            if (!stack.getEnchantments().isEmpty()) {
                for (Enchantment enchant : stack.getEnchantments().keySet()) {
                    if (ItemBuilder.getPossibleEnchantments(stack).contains(enchant)) {
                        safe.add(enchant.getName() + ":" + stack.getEnchantments().get(enchant));
                    } else {
                        unsafe.add(enchant.getName() + ":" + stack.getEnchantments().get(enchant));
                    }
                }
            }
            if (!safe.isEmpty()) {
                this.configuration.set(path + ".enchantments.safe", safe);
            }
            if (!unsafe.isEmpty()) {
                this.configuration.set(path + ".enchantments.unsafe", unsafe);
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get">
    public String getString(String path) {
        return this.configuration.getString(path);
    }

    public List<String> getStringList(String path) {
        return this.configuration.getStringList(path);
    }

    public ArrayList<String> getStringListAsArrayList(String path) {
        ArrayList<String> array = new ArrayList<>();
        for (String permission : this.configuration.getStringList(path)) {
            array.add(permission);
        }
        return array;
    }

    public Boolean getBoolean(String path) {
        return this.configuration.getBoolean(path);
    }

    public Integer getInt(String path) {
        return this.configuration.getInt(path);
    }

    public Long getLong(String path) {
        return this.configuration.getLong(path);
    }

    public Double getDouble(String path) {
        return this.configuration.getDouble(path);
    }

    public Location getLocation(String path) {
        World world = Bukkit.getWorld(this.configuration.getString(path + ".world"));
        double x = this.configuration.getDouble(path + ".x");
        double y = this.configuration.getDouble(path + ".y");
        double z = this.configuration.getDouble(path + ".z");
        float yaw = this.configuration.getInt(path + ".yaw");
        float pitch = this.configuration.getInt(path + ".pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }

    public ItemStack getItemStack(String path) {
        ItemBuilder builder = new ItemBuilder();
        builder.setMaterial(Material.valueOf(this.configuration.getString(path + ".material")));
        builder.setAmount(this.configuration.getInt(path + ".amount"));
        builder.setDurability((short) this.configuration.getInt(path + ".durability"));
        if (this.configuration.contains(path + ".meta.displayName")) {
            builder.setDisplayName(this.configuration.getString(path + ".meta.displayName"));
        }
        if (this.configuration.contains(path + ".meta.lore")) {
            builder.setLore(this.configuration.getStringList(path + ".meta.lore"));
        }
        if (this.configuration.contains(path + ".meta.unbreakable")) {
            builder.setUnbreakable(this.configuration.getBoolean(path + ".meta.unbreakable"));
        }
        if (this.configuration.contains(path + ".meta.showenchant")) {
            builder.setShowEnchant(this.configuration.getBoolean(path + ".meta.showenchant"));
        }
        if (this.configuration.contains(path + ".meta.armorColor")) {
            builder.setColor(this.configuration.getInt(path + ".meta.armorColor"));
        }
        if (this.configuration.contains(path + ".meta.owningPlayer")) {
            builder.setOwner(this.configuration.getString(path + ".meta.owningPlayer"));
        }
        if (this.configuration.contains(path + ".enchantments.safe")) {
            for (String safeEnchant : this.configuration.getStringList(path + ".enchantments.safe")) {
                String[] enchant = safeEnchant.split(":");
                builder.addEnchantment(Enchantment.getByName(enchant[0]), Integer.valueOf(enchant[1]), true);
            }
        }
        if (this.configuration.contains(path + ".enchantments.unsafe")) {
            for (String unsafeEnchant : this.configuration.getStringList(path + ".enchantments.unsafe")) {
                String[] enchant = unsafeEnchant.split(":");
                builder.addEnchantment(Enchantment.getByName(enchant[0]), Integer.valueOf(enchant[1]), false);
            }
        }
        return builder.build();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="save/load">
    public final void save() {
        try {
            this.configuration.save(this.file);
        } catch (IOException ex) {
            Logger.getLogger(FileBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final void load() {
        if (filename == null) {
            this.file = new File(getPath());
        } else {
            this.file = new File(getPath(), getFilename());
        }
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }
    //</editor-fold>

}
