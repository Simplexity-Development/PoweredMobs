package adhdmc.poweredmobs;

import adhdmc.poweredmobs.config.Config;
import adhdmc.poweredmobs.listener.skeleton.SkeletonArrowListener;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class PoweredMobs extends JavaPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        Config.loadConfig();
        registerEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Plugin getPlugin() { return plugin; }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new SkeletonArrowListener(), this);
    }

    public static void broadcast(String message) {
        PoweredMobs.getPlugin().getServer().broadcast(MiniMessage.miniMessage().deserialize(message));
    }

}
