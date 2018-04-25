package net.blitzcube.peapi.entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by iso2013 on 4/23/2018.
 */
public class SightDistanceRegistry {
    private static Map<String, Integer> entityDistances;

    static {
        entityDistances = new HashMap<>();
        ConfigurationSection section = Bukkit.spigot().getConfig().getConfigurationSection("world-settings");
        ConfigurationSection defaultSec = section.getConfigurationSection("default.entity-tracking-range");
        for (String s : section.getKeys(false)) {
            ConfigurationSection world = section.getConfigurationSection(s + ".entity-tracking-range");
            int[] ranges = new int[]{
                    world.contains("players") ? world.getInt("players") : defaultSec.getInt("players"),
                    world.contains("animals") ? world.getInt("animals") : defaultSec.getInt("animals"),
                    world.contains("monsters") ? world.getInt("monsters") : defaultSec.getInt("monsters"),
                    world.contains("misc") ? world.getInt("misc") : defaultSec.getInt("misc"),
                    world.contains("other") ? world.getInt("other") : defaultSec.getInt("other")
            };
            int max = 0;
            for (int range : ranges) {
                if (range > max) max = range;
            }
            entityDistances.putIfAbsent(s, max);
        }
    }


    public static boolean isVisible(Location l, Player p, double error) {
        if (!l.getWorld().getUID().equals(p.getWorld().getUID())) return false;
        double max = entityDistances.containsKey(l.getWorld().getName()) ?
                entityDistances.get(l.getWorld().getName()) : entityDistances.get("default");
        max *= error;
        return Math.abs(l.getX() - p.getLocation().getX()) < max
                && Math.abs(l.getZ() - p.getLocation().getZ()) < max;
    }

    static Stream<Entity> getNearby(Player p, double error) {
        double max = entityDistances.containsKey(p.getWorld().getName()) ?
                entityDistances.get(p.getWorld().getName()) : entityDistances.get("default");
        max *= error;
        return p.getNearbyEntities(max, max, max).stream();
    }
}
