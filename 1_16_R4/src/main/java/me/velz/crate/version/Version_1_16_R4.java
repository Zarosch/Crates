package me.velz.crate.version;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Version_1_16_R4 implements Version {

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

    @Override
    public ItemStack getDefaultCrateLimeGlass() {
        ItemStack itemStack = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(" ");
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

}
