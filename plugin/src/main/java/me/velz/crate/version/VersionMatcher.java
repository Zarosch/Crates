package me.velz.crate.version;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import org.bukkit.Bukkit;

public class VersionMatcher {

    @Getter
    private final String serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);

    @Getter
    private final List<Class<? extends Version>> versions = Arrays.asList(
            Version_1_8_R3.class,
            Version_1_12_R1.class,
            Version_1_13_R1.class,
            Version_1_13_R2.class,
            Version_1_14_R1.class,
            Version_1_16_R1.class,
            Version_1_15_R1.class,
            Version_1_16_R2.class
    );
    
    public Version match() {
        try {
            return versions.stream()
                    .filter(version -> version.getSimpleName().substring(8).equals(serverVersion))
                    .findFirst().orElseThrow(() -> new RuntimeException("Your server version isn't supported in Crates! (" + serverVersion +")"))
                    .newInstance();
        } catch (IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex); 
        }
    }
    
}
