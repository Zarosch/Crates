package me.velz.crate.objects;

import java.util.ArrayList;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public class Crate {
    
    @Getter
    private final String name;
    
    @Getter
    private final ItemStack item;
    
    @Getter
    private final ArrayList<CrateItem> crateItems;

    public Crate(String name, ItemStack item, ArrayList<CrateItem> crateItems) {
        this.name = name;
        this.item = item;
        this.crateItems = crateItems;
    }
    
}
