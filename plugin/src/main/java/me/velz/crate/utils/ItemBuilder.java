package me.velz.crate.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import me.velz.crate.Crates;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {

    private String displayname;
    private List<String> lore;
    private Material material;
    private short durability = 0;
    private Color color;
    private int amount = 1;
    private String owner;
    private final HashMap<Enchantment, Integer> safeEnchant = new HashMap<>();
    private final HashMap<Enchantment, Integer> unSafeEnchant = new HashMap<>();
    private boolean unbreakable = false, showenchant = true, showattributes = true;

    //<editor-fold defaultstate="collapsed" desc="setMaterial">
    public ItemBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setDurability">
    public ItemBuilder setDurability(short durability) {
        this.durability = durability;
        return this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setColor">
    public ItemBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public ItemBuilder setColor(Integer color) {
        this.color = Color.fromRGB(color);
        return this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setDisplayName">
    public ItemBuilder setDisplayName(String displayname) {
        this.displayname = ChatColor.translateAlternateColorCodes('&', displayname);
        return this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="fromItemStack">
    public ItemBuilder fromItemStack(ItemStack is) {
        amount = is.getAmount();
        if (is.getItemMeta().getDisplayName() != null) {
            displayname = is.getItemMeta().getDisplayName();
        }
        durability = is.getDurability();
        if (is.getItemMeta().getLore() != null) {
            lore = is.getItemMeta().getLore();
        }
        material = is.getType();
        if (material == Crates.getPlugin().getVersion().getSkullItem()) {
            SkullMeta sm = (SkullMeta) is.getItemMeta();
            if (sm.getOwner() != null) {
                owner = sm.getOwner();
            }
        }
        if (material == Material.LEATHER_BOOTS || material == Material.LEATHER_CHESTPLATE || material == Material.LEATHER_HELMET || material == Material.LEATHER_LEGGINGS) {
            LeatherArmorMeta lam = (LeatherArmorMeta) is.getItemMeta();
            if (lam.getColor() != null) {
                color = lam.getColor();
            }
        }
        return this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="addEnchantment">
    public ItemBuilder addEnchantment(Enchantment enchant, int level, boolean save) {
        if (save) {
            safeEnchant.put(enchant, level);
        } else {
            unSafeEnchant.put(enchant, level);
        }
        return this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setAmount">
    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setLore">
    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setOwner">
    public ItemBuilder setOwner(String owner) {
        this.owner = owner;
        return this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setShowEnchant">
    public ItemBuilder setShowEnchant(boolean showenchant) {
        this.showenchant = showenchant;
        return this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setShowAttributes">
    public ItemBuilder setShowAttributes(boolean showattributes) {
        this.showattributes = showattributes;
        return this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setUnbreakable">
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getPossibleEnchantments">
    public static List<Enchantment> getPossibleEnchantments(ItemStack stack) {
        List<Enchantment> possible = new ArrayList<>();
        for (Enchantment ench : Enchantment.values()) {
            if (ench.canEnchantItem(stack)) {
                possible.add(ench);
            }
        }
        return possible;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="build">
    public ItemStack build() {
        ItemStack is = new ItemStack(material);
        is.setDurability(durability);
        is.setAmount(amount);
        if (!safeEnchant.isEmpty()) {
            for (Enchantment enchant : safeEnchant.keySet()) {
                is.addEnchantment(enchant, safeEnchant.get(enchant));
            }
        }
        if (!unSafeEnchant.isEmpty()) {
            for (Enchantment enchant : unSafeEnchant.keySet()) {
                is.addUnsafeEnchantment(enchant, unSafeEnchant.get(enchant));
            }
        }
        ItemMeta im = is.getItemMeta();
        if (displayname != null) {
            im.setDisplayName(displayname);
        }
        if (lore != null) {
            im.setLore(lore);
        }
        if (!showenchant) {
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        if (!showattributes) {
            im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }
        is.setItemMeta(im);
        if (unbreakable) {
            Crates.getPlugin().getVersion().setUnbreakable(is, true);
        }
        if (owner != null) {
            SkullMeta sm = (SkullMeta) is.getItemMeta();
            sm.setOwner(owner);
            is.setItemMeta(sm);
        }
        if (color != null) {
            LeatherArmorMeta lam = (LeatherArmorMeta) is.getItemMeta();
            lam.setColor(color);
            is.setItemMeta(lam);
        }
        return is;
    }
    //</editor-fold>

}
