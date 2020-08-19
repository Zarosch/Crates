package me.velz.crate.objects;

import java.util.ArrayList;
import lombok.Getter;
import org.bukkit.Location;

public class CrateChest {

    @Getter
    private final String type;

    @Getter
    private final ArrayList<Location> locations;

    @Getter
    private final ArrayList<String> crates;

    public CrateChest(String type, ArrayList<Location> locations, ArrayList<String> crates) {
        this.type = type;
        this.locations = locations;
        this.crates = crates;
    }

}
