package net.blitzcube.peapi.entity;

import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.entity.fake.FakeEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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
    private static final Map<EntityType, String> categories = new HashMap<>();
    private static Map<String, Map<String, Integer>> entityDistances;

    static {
        categories.put(EntityType.ARMOR_STAND, "other");
        categories.put(EntityType.ARROW, "other");
        categories.put(EntityType.BAT, "animals");
        categories.put(EntityType.BLAZE, "monsters");
        categories.put(EntityType.BOAT, "other");
        categories.put(EntityType.CAVE_SPIDER, "monsters");
        categories.put(EntityType.CHICKEN, "animals");
        categories.put(EntityType.COMPLEX_PART, "other");
        categories.put(EntityType.COW, "animals");
        categories.put(EntityType.CREEPER, "monsters");
        categories.put(EntityType.DONKEY, "animals");
        categories.put(EntityType.DRAGON_FIREBALL, "other");
        categories.put(EntityType.DROPPED_ITEM, "misc");
        categories.put(EntityType.EGG, "other");
        categories.put(EntityType.ELDER_GUARDIAN, "monsters");
        categories.put(EntityType.ENDER_CRYSTAL, "other");
        categories.put(EntityType.ENDER_DRAGON, "other");
        categories.put(EntityType.ENDER_PEARL, "other");
        categories.put(EntityType.ENDER_SIGNAL, "other");
        categories.put(EntityType.ENDERMAN, "monsters");
        categories.put(EntityType.ENDERMITE, "monsters");
        categories.put(EntityType.EVOKER, "monsters");
        categories.put(EntityType.EVOKER_FANGS, "other");
        categories.put(EntityType.EXPERIENCE_ORB, "misc");
        categories.put(EntityType.FALLING_BLOCK, "other");
        categories.put(EntityType.FIREBALL, "other");
        categories.put(EntityType.FIREWORK, "other");
        categories.put(EntityType.FISHING_HOOK, "other");
        categories.put(EntityType.GHAST, "animals");
        categories.put(EntityType.GIANT, "monsters");
        categories.put(EntityType.GUARDIAN, "monsters");
        categories.put(EntityType.HORSE, "animals");
        categories.put(EntityType.HUSK, "monsters");
        categories.put(EntityType.ILLUSIONER, "monsters");
        categories.put(EntityType.IRON_GOLEM, "animals");
        categories.put(EntityType.ITEM_FRAME, "misc");
        categories.put(EntityType.LEASH_HITCH, "other");
        categories.put(EntityType.LIGHTNING, "misc");
        categories.put(EntityType.LINGERING_POTION, "other");
        categories.put(EntityType.LLAMA, "animals");
        categories.put(EntityType.LLAMA_SPIT, "other");
        categories.put(EntityType.MAGMA_CUBE, "monsters");
        categories.put(EntityType.MINECART, "other");
        categories.put(EntityType.MINECART_CHEST, "other");
        categories.put(EntityType.MINECART_COMMAND, "other");
        categories.put(EntityType.MINECART_FURNACE, "other");
        categories.put(EntityType.MINECART_HOPPER, "other");
        categories.put(EntityType.MINECART_MOB_SPAWNER, "other");
        categories.put(EntityType.MINECART_TNT, "other");
        categories.put(EntityType.MULE, "animals");
        categories.put(EntityType.MUSHROOM_COW, "animals");
        categories.put(EntityType.OCELOT, "animals");
        categories.put(EntityType.PAINTING, "misc");
        categories.put(EntityType.PARROT, "animals");
        categories.put(EntityType.PIG, "animals");
        categories.put(EntityType.PIG_ZOMBIE, "monsters");
        categories.put(EntityType.PLAYER, "players");
        categories.put(EntityType.POLAR_BEAR, "animals");
        categories.put(EntityType.PRIMED_TNT, "other");
        categories.put(EntityType.RABBIT, "animals");
        categories.put(EntityType.SHEEP, "animals");
        categories.put(EntityType.SHULKER, "animals");
        categories.put(EntityType.SHULKER_BULLET, "other");
        categories.put(EntityType.SILVERFISH, "monsters");
        categories.put(EntityType.SKELETON, "monsters");
        categories.put(EntityType.SKELETON_HORSE, "animals");
        categories.put(EntityType.SLIME, "monsters");
        categories.put(EntityType.SMALL_FIREBALL, "other");
        categories.put(EntityType.SNOWBALL, "other");
        categories.put(EntityType.SNOWMAN, "animals");
        categories.put(EntityType.SPECTRAL_ARROW, "other");
        categories.put(EntityType.SPIDER, "monsters");
        categories.put(EntityType.SPLASH_POTION, "other");
        categories.put(EntityType.SQUID, "other");
        categories.put(EntityType.STRAY, "monsters");
        categories.put(EntityType.THROWN_EXP_BOTTLE, "other");
        categories.put(EntityType.TIPPED_ARROW, "other");
        categories.put(EntityType.UNKNOWN, "other");
        categories.put(EntityType.VEX, "monsters");
        categories.put(EntityType.VILLAGER, "animals");
        categories.put(EntityType.VINDICATOR, "monsters");
        categories.put(EntityType.WEATHER, "other");
        categories.put(EntityType.WITCH, "monsters");
        categories.put(EntityType.WITHER, "monsters");
        categories.put(EntityType.WITHER_SKELETON, "monsters");
        categories.put(EntityType.WITHER_SKULL, "other");
        categories.put(EntityType.WOLF, "animals");
        categories.put(EntityType.ZOMBIE, "monsters");
        categories.put(EntityType.ZOMBIE_HORSE, "animals");
        categories.put(EntityType.ZOMBIE_VILLAGER, "monsters");
    }

    static {
        entityDistances = new HashMap<>();
        ConfigurationSection section = Bukkit.spigot().getConfig().getConfigurationSection("world-settings");
        ConfigurationSection defaultSec = section.getConfigurationSection("default.entity-tracking-range");
        for (String s : section.getKeys(false)) {
            ConfigurationSection world = section.getConfigurationSection(s + ".entity-tracking-range");
            entityDistances.put(s, new HashMap<>());
            Map<String, Integer> m = entityDistances.get(s);
            m.put("players", world.contains("players") ? world.getInt("players") : defaultSec.getInt("players"));
            m.put("animals", world.contains("animals") ? world.getInt("animals") : defaultSec.getInt("animals"));
            m.put("monsters", world.contains("monsters") ? world.getInt("monsters") : defaultSec.getInt("monsters"));
            m.put("misc", world.contains("misc") ? world.getInt("misc") : defaultSec.getInt("misc"));
            m.put("other", world.contains("other") ? world.getInt("other") : defaultSec.getInt("other"));
            int max = 0;
            for (Integer i : m.values()) {
                if (i > max) max = i;
            }
            m.put("max", max);
        }
    }


    public static boolean isVisible(Location l, Player p, double error, EntityType type) {
        if (!l.getWorld().getUID().equals(p.getWorld().getUID())) return false;
        double max = entityDistances.containsKey(l.getWorld().getName()) ?
                entityDistances.get(l.getWorld().getName()).get(categories.get(type)) : entityDistances.get
                ("default").get(categories.get(type));
        max *= error;
        return Math.abs(l.getX() - p.getLocation().getX()) < max
                && Math.abs(l.getZ() - p.getLocation().getZ()) < max;
    }

    public static Stream<Entity> getNearby(Player p, double error) {
        Map<String, Integer> distances = entityDistances.containsKey(p.getWorld().getName()) ?
                entityDistances.get(p.getWorld().getName()) : entityDistances.get("default");
        double max = distances.get("max");
        max *= error;
        return p.getNearbyEntities(max, 256, max).stream()
                .filter(entity ->
                        isVisible(entity.getLocation(), p, error, entity.getType())
                );
    }

    public static Stream<IEntityIdentifier> getNearby(Player p, double error, Collection<FakeEntity> fakes) {
        Stream<IEntityIdentifier> real = getNearby(p, error).map(EntityIdentifier::new);
        if (fakes == null) {
            return real;
        } else {
            return Stream.concat(real, fakes.stream().filter(
                    fakeEntity -> isVisible(fakeEntity.getLocation(), p, error, fakeEntity.getType())
            ).map(FakeEntity::getIdentifier));
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
        double max = entityDistances.containsKey(l.getWorld().getName()) ?
                entityDistances.get(l.getWorld().getName()).get(categories.get(t)) : entityDistances.get("default").get(categories.get(t));
        max *= err;
        double finalMax = max;
        return l.getWorld().getPlayers().stream().filter(p -> isNear(finalMax, p.getLocation(), l));
    }
}
