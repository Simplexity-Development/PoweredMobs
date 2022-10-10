package adhdmc.poweredmobs.config;

import adhdmc.poweredmobs.PoweredMobs;
import org.bukkit.plugin.Plugin;

public class Config {

    public static void loadConfig() {
        Plugin plugin = PoweredMobs.getPlugin();
        // TODO: Allow for a dynamically updating configuration.
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
    }

}
