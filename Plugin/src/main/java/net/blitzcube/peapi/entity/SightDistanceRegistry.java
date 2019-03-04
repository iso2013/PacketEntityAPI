package net.blitzcube.peapi.entity;

import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.entity.fake.FakeEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.*;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by iso2013 on 03/04/19.
 */
public class SightDistanceRegistry {
    private static final Map<String, EnumMap<EntityType, Integer>> distances = new HashMap<>();
    private static final Map<String, Integer> maximums = new HashMap<>();

    static {
        ConfigurationSection settings = Bukkit.spigot().getConfig().getConfigurationSection("world-settings");
        ConfigurationSection defSec = settings.getConfigurationSection("default");
        for (String s : settings.getKeys(false)) {
            if (s.equals("default")) continue;
            loadSettings(s, settings.getConfigurationSection(s), defSec);
        }
        loadSettings(null, defSec, defSec);

        for (Map.Entry<String, EnumMap<EntityType, Integer>> e : distances.entrySet()) {
            maximums.put(e.getKey(), maximums.values().stream().max(Integer::compare).orElse(0));
        }
    }

    private static void loadSettings(String key, ConfigurationSection sec, ConfigurationSection defSec) {
        if (!distances.containsKey(key)) distances.put(key, new EnumMap<>(EntityType.class));
        EnumMap<EntityType, Integer> map = distances.get(key);
        for (EntityType t : EntityType.values()) {
            Class<? extends Entity> clazz = t.getEntityClass();
            if(clazz == null) continue;

            if (Player.class.isAssignableFrom(clazz)) {
                map.put(t, getWithDefaults(sec, defSec, "entity-tracking-range.players", 48));
            } else if (Monster.class.isAssignableFrom(clazz) || Slime.class.isAssignableFrom(clazz)) {
                map.put(t, getWithDefaults(sec, defSec, "entity-tracking-range.monsters", 48));
            } else if (Ghast.class.isAssignableFrom(clazz)) {
                int tracking = getWithDefaults(sec, defSec, "entity-tracking-range.monsters", 48);
                int activation = getWithDefaults(sec, defSec, "entity-activation-range.monsters", 32);
                map.put(t, tracking > activation ? tracking : activation);
            } else if (Creature.class.isAssignableFrom(clazz) || Ambient.class.isAssignableFrom(clazz)) {
                map.put(t, getWithDefaults(sec, defSec, "entity-tracking-range.animals", 48));
            } else if (ItemFrame.class.isAssignableFrom(clazz) || Painting.class.isAssignableFrom(clazz)
                    || Item.class.isAssignableFrom(clazz) || ExperienceOrb.class.isAssignableFrom(clazz)) {
                map.put(t, getWithDefaults(sec, defSec, "entity-tracking-range.misc", 32));
            } else {
                map.put(t, getWithDefaults(sec, defSec, "entity-tracking-range.other", 64));
            }
        }
    }

    private static int getWithDefaults(ConfigurationSection section, ConfigurationSection def, String key, int backup) {
        return section.getInt(key, def.getInt(key, backup));
    }

    private static int defaultGet(String world, EntityType type) {
        if (distances.containsKey(world)) {
            return distances.get(world).get(type);
        } else {
            return distances.get(null).get(type);
        }
    }

    public static boolean isVisible(Location l, Player p, double error, EntityType type) {
        if (!l.getWorld().getUID().equals(p.getWorld().getUID())) return false;
        double max = defaultGet(l.getWorld().getName(), type);
        max *= error;
        return Math.abs(l.getX() - p.getLocation().getX()) < max
                && Math.abs(l.getZ() - p.getLocation().getZ()) < max;
    }

    public static Stream<Entity> getNearby(Player p, double error) {
        if(p == null) return Stream.empty();
        Map<EntityType, Integer> worldDistances;
        double max;
        String world = p.getWorld().getName();
        if (distances.containsKey(world)) {
            worldDistances = distances.get(world);
            max = maximums.get(world);
        } else {
            worldDistances = distances.get(null);
            max = maximums.get(null);
        }
        max *= error;
        return p.getNearbyEntities(max, 256, max).stream()
                .filter(entity ->
                        isNear(worldDistances.get(entity.getType()), p.getLocation(), entity.getLocation())
                );
    }

    public static Stream<IEntityIdentifier> getNearby(Player p, double error, Collection<FakeEntity> fakes) {
        Map<EntityType, Integer> worldDistances;
        double max;
        String world = p.getWorld().getName();
        if (distances.containsKey(world)) {
            worldDistances = distances.get(world);
            max = maximums.get(world);
        } else {
            worldDistances = distances.get(null);
            max = maximums.get(null);
        }
        max *= error;
        Stream<IEntityIdentifier> real =
                p.getNearbyEntities(max, 256, max).stream().filter(
                        entity -> isNear(worldDistances.get(entity.getType()), p.getLocation(), entity.getLocation()
                        )
                )
                        .map(EntityIdentifier::new);
        if (fakes == null) {
            return real;
        } else {
            return Stream.concat(real, fakes.stream()
                    .filter(fakeEntity -> isNear(worldDistances.get(fakeEntity.getType()), p.getLocation(),
                            fakeEntity.getLocation()))
                    .map(EntityIdentifier::new));
        }
    }

    private static boolean isNear(double max, Location l1, Location l2) {
        return !(Math.abs(l1.getX() - l2.getX()) > max) && !(Math.abs(l1.getZ() - l2.getZ()) > max);
    }

    public static Stream<Player> getViewers(IEntityIdentifier object, double err) {
        object.moreSpecific();
        Location l;
        EntityType t;
        if (object.isFakeEntity()) {
            l = Objects.requireNonNull(object.getFakeEntity().get()).getLocation();
            t = Objects.requireNonNull(object.getFakeEntity().get()).getType();
        } else {
            l = Objects.requireNonNull(object.getEntity().get()).getLocation();
            t = Objects.requireNonNull(object.getEntity().get()).getType();
        }
        double val = defaultGet(l.getWorld().getName(), t);
        val *= err;
        double finalVal = val;
        return l.getWorld().getPlayers().stream().filter(p -> isNear(finalVal, p.getLocation(), l));
    }
}
