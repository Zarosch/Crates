package me.velz.crate.version;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class Version_1_13_R1 implements Version {

    @Override
    public Sound getSound(String sound) {
        return Sound.valueOf(sound);
    }

    @Override
    public boolean isUnbreakable(ItemStack itemStack) {
        return itemStack.getItemMeta().isUnbreakable();
    }

    @Override
    public ItemStack setUnbreakable(ItemStack itemStack, boolean value) {
        itemStack.getItemMeta().setUnbreakable(value);
        return itemStack;
    }

    @Override
    public String getSkullOwner(SkullMeta skullMeta) {
        if (skullMeta.getOwningPlayer() == null) {
            return null;
        }
        return skullMeta.getOwningPlayer().getName();
    }

    @Override
    public Material getSkullItem() {
        return Material.PLAYER_HEAD;
    }

}
