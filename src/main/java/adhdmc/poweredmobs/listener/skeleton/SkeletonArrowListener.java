package adhdmc.poweredmobs.listener.skeleton;

import adhdmc.poweredmobs.PoweredMobs;
import adhdmc.poweredmobs.config.ConfigSetting;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.AbstractSkeleton;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class SkeletonArrowListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onSkeletonShootArrow(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof AbstractSkeleton)) return;
        if (!(event.getProjectile() instanceof Arrow)) return;

        FileConfiguration config = PoweredMobs.getPlugin().getConfig();
        List<String> arrowTypes = config.getStringList(ConfigSetting.SKELETON_SPECIAL_ARROW_TYPES.path);
        int arrowChance = config.getInt(ConfigSetting.SKELETON_SPECIAL_ARROW_CHANCE.path, 0);

        if (arrowTypes.size() == 0) return;
        if ((int)(Math.random() * 100 + 1) > arrowChance) return;

        int randomIndex = (int)(Math.random() * arrowTypes.size());
        String arrowType = arrowTypes.get(randomIndex);
        String[] arrowProperties = arrowType.split(" ");

        PotionEffectType type = (arrowProperties.length >= 1) ? PotionEffectType.getByName(arrowProperties[0]) : null;
        if (type == null) return;
        int duration = (arrowProperties.length >= 2) ? Integer.parseInt(arrowProperties[1]) : 0;
        int amplifier = (arrowProperties.length >= 3) ? Integer.parseInt(arrowProperties[2]) : 0;
        PoweredMobs.broadcast(type.getName() + " " + duration + " " + amplifier);

        Arrow arrow = (Arrow) event.getProjectile();
        arrow.addCustomEffect(type.createEffect(duration, amplifier), false);
    }

}
