package me.velz.crate.version;

import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Version_1_8_R3 implements Version {

    @Override
    public Sound getSound(String sound) {
        if (sound.equalsIgnoreCase("ENTITY_PLAYER_LEVELUP")) {
            return Sound.LEVEL_UP;
        }
        if (sound.equalsIgnoreCase("UI_BUTTON_CLICK")) {
            return Sound.CLICK;
        }

        return null;
    }

    @Override
    public boolean isUnbreakable(ItemStack itemStack) {
        return itemStack.getItemMeta().spigot().isUnbreakable();
    }

    @Override
    public ItemStack setUnbreakable(ItemStack itemStack, boolean value) {
        itemStack.getItemMeta().spigot().setUnbreakable(value);
        return itemStack;
    }

    @Override
    public String getSkullOwner(SkullMeta skullMeta) {
        return skullMeta.getOwner();
    }

    @Override
    public Material getSkullItem() {
        return Material.SKULL_ITEM;
    }

    @Override
    public ItemStack getDefaultCrateLimeGlass() {
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE);
        itemStack.setDurability((short)4);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(" ");
        itemStack.setItemMeta(itemMeta);
        
        return itemStack;
    }
    
    @Override
    public String getPropertySignature(Property property) {
        return property.getSignature();
    }

    @Override
    public String getPropertyValue(Property property) {
        return property.getValue();
    }
    
    

}
