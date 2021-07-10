package me.velz.crate.objects;

import lombok.Getter;
import org.bukkit.Location;

public class CrateChest {

    @Getter
    private final Location location;

    public CrateChest(Location location) {
        this.location = location;
    }

}
