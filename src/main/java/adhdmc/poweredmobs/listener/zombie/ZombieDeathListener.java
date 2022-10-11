package adhdmc.poweredmobs.listener.zombie;

import adhdmc.poweredmobs.PoweredMobs;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class ZombieDeathListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onZombieDeath(EntityDeathEvent event) {
        // Zombie Types: Zombie, Drowned, Husk, PigZombie, VillagerZombie
        if (!(event.getEntity() instanceof Zombie)) return;
        // TODO: Check excluded Zombie Types
        event.setCancelled(true);
        Zombie zombie = (Zombie) event.getEntity();
        zombie.setAI(false);
        zombie.setInvulnerable(true);
        zombie.teleport(zombie.getLocation().subtract(0, 1.25, 0));
        Component zombieName = zombie.name();
        Bukkit.getScheduler().runTaskLater(PoweredMobs.getPlugin(), () -> {

        }, 20*10);
        // TODO: ^ Make time configurable.
    }

}
