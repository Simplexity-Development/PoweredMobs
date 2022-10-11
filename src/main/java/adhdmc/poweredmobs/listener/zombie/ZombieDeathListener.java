package adhdmc.poweredmobs.listener.zombie;

import adhdmc.poweredmobs.PoweredMobs;
import adhdmc.poweredmobs.config.ConfigSetting;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;

public class ZombieDeathListener implements Listener {

    public static final NamespacedKey hasResurrected = new NamespacedKey(PoweredMobs.getPlugin(), "has-resurrected");
    private static final HashSet<Zombie> zombiesOnResurrect = new HashSet<>();

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onZombieDeath(EntityDeathEvent event) {
        // Zombie Types: Zombie, Drowned, Husk, PigZombie, VillagerZombie
        if (!(event.getEntity() instanceof Zombie)) return;
        FileConfiguration config = PoweredMobs.getPlugin().getConfig();
        if (!config.getBoolean(ConfigSetting.ZOMBIE_RESURRECT_ENABLED.path, false)) return;

        // TODO: Check excluded Zombie Types
        PersistentDataContainer mobPDC = event.getEntity().getPersistentDataContainer();
        if (mobPDC.has(hasResurrected)) return;
        event.setCancelled(true);
        Zombie zombie = (Zombie) event.getEntity();
        zombiesOnResurrect.add(zombie);
        zombie.setAI(false);
        zombie.setInvulnerable(true);
        zombie.setGravity(false);
        Bukkit.getScheduler().runTaskLater(PoweredMobs.getPlugin(), () -> {

            if (zombie.isDead()) return;
            int chance = config.getInt(ConfigSetting.ZOMBIE_RESURRECT_CHANCE.path);
            if ((int)(Math.random() * 100 + 1) > chance) {
                killZombie(zombie);
                return;
            }
            
            zombie.setAI(true);
            zombie.setInvulnerable(false);
            zombie.setGravity(true);
            AttributeInstance health = zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            assert health != null;
            zombie.setHealth(health.getValue());
            mobPDC.set(hasResurrected, PersistentDataType.BYTE, (byte) 0);
            zombiesOnResurrect.remove(zombie);

        }, config.getInt(ConfigSetting.ZOMBIE_RESURRECT_DURATION.path, 200));
    }

    public static void stopZombieResurrects() {
        for (Zombie zombie : zombiesOnResurrect) {
            killZombie(zombie);
        }
        zombiesOnResurrect.clear();
    }

    private static void killZombie(Zombie zombie) {
        if (zombie.isDead()) return;
        zombie.setInvulnerable(false);
        AttributeInstance health = zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        assert health != null;
        zombie.damage(health.getValue());
    }

}
