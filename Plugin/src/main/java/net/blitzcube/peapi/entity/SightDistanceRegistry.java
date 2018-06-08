package net.blitzcube.peapi.entity;

import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.entity.fake.FakeEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

    public static Stream<Entity> getNearby(Player p, double error) {
        double max = entityDistances.containsKey(p.getWorld().getName()) ?
                entityDistances.get(p.getWorld().getName()) : entityDistances.get("default");
        max *= error;
        return p.getNearbyEntities(max, 256, max).stream();
    }

    public static Stream<IEntityIdentifier> getNearby(Player p, double error, Collection<FakeEntity> fakes) {
        double max = entityDistances.containsKey(p.getWorld().getName()) ?
                entityDistances.get(p.getWorld().getName()) : entityDistances.get("default");
        max *= error;
        Stream<IEntityIdentifier> real = p.getNearbyEntities(max, 256, max).stream().map(EntityIdentifier::new);
        if (fakes == null) {
            return real;
        } else {
            double finalMax = max;
            return Stream.concat(real, fakes.stream()
                    .filter(fakeEntity -> isNear(finalMax, p.getLocation(), fakeEntity.getLocation(), true))
                    .map(EntityIdentifier::new));
        }
    }

    private static boolean isNear(double max, Location l1, Location l2, boolean ignoreY) {
        return !(Math.abs(l1.getX() - l2.getX()) > max) && !(Math.abs(l1.getZ() - l2.getZ()) > max) && (ignoreY || !
                (Math.abs(l1.getY() - l2.getY()) > max));
    }

    public static Stream<Player> getViewers(IEntityIdentifier object, double err) {
        object.moreSpecific();
        Location l;
        if (object.isFakeEntity()) {
            l = Objects.requireNonNull(object.getFakeEntity().get()).getLocation();
        } else {
            l = Objects.requireNonNull(object.getEntity().get()).getLocation();
        }
        double max = entityDistances.containsKey(l.getWorld().getName()) ?
                entityDistances.get(l.getWorld().getName()) : entityDistances.get("default");
        max *= err;
        double finalMax = max;
        return l.getWorld().getPlayers().stream().filter(p -> isNear(finalMax, p.getLocation(), l, true));
    }
}
