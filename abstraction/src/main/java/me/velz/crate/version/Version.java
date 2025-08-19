package me.velz.crate.version;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import com.mojang.authlib.properties.Property;
import org.bukkit.entity.Player;


public interface Version {
    
    public Sound getSound(String sound);
    
    public boolean isUnbreakable(ItemStack ItemStack);
    
    public ItemStack setUnbreakable(ItemStack itemStack, boolean value);
    
    public String getSkullOwner(SkullMeta skullMeta);
        
    public Material getSkullItem();
    
    public ItemStack getDefaultCrateLimeGlass();
    
    public String getPropertySignature(Property property);
    
    public String getPropertyValue(Property property);
    
    public ItemStack getMainHandItem(Player player);
    
}
