package net.blitzcube.peapi.entity.hitbox;

import net.blitzcube.peapi.api.entity.hitbox.IHitbox;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class Hitbox implements IHitbox {
    //https://youtu.be/QWzYaZDK6Is?t=122
    private static final Map<EntityType, Hitbox> boxes = new HashMap<>();

    static {
        boxes.put(EntityType.DROPPED_ITEM, new Hitbox(0.25));
        boxes.put(EntityType.EXPERIENCE_ORB, new Hitbox(0.5));
        boxes.put(EntityType.AREA_EFFECT_CLOUD, new Hitbox(2.0, 0.5));
        boxes.put(EntityType.ELDER_GUARDIAN, new Hitbox(1.9975));
        boxes.put(EntityType.WITHER_SKELETON, new Hitbox(0.7, 2.4));
        boxes.put(EntityType.STRAY, new Hitbox(0.6, 1.99));
        boxes.put(EntityType.EGG, new Hitbox(0.25));
        boxes.put(EntityType.LEASH_HITCH, new Hitbox(0.375, 0.5));
        boxes.put(EntityType.ARROW, new Hitbox(0.5));
        boxes.put(EntityType.SNOWBALL, new Hitbox(0.25));
        boxes.put(EntityType.FIREBALL, new Hitbox(1.0));
        boxes.put(EntityType.SMALL_FIREBALL, new Hitbox(0.3125));
        boxes.put(EntityType.ENDER_PEARL, new Hitbox(0.25));
        boxes.put(EntityType.ENDER_SIGNAL, new Hitbox(0.25));
        boxes.put(EntityType.SPLASH_POTION, new Hitbox(0.25));
        boxes.put(EntityType.THROWN_EXP_BOTTLE, new Hitbox(0.25));
        boxes.put(EntityType.WITHER_SKULL, new Hitbox(0.3125));
        boxes.put(EntityType.PRIMED_TNT, new Hitbox(0.98));
        boxes.put(EntityType.FALLING_BLOCK, new Hitbox(0.98));
        boxes.put(EntityType.FIREWORK, new Hitbox(0.25));
        boxes.put(EntityType.HUSK, new Hitbox(0.6, 1.95));
        boxes.put(EntityType.SPECTRAL_ARROW, new Hitbox(0.5));
        boxes.put(EntityType.SHULKER_BULLET, new Hitbox(0.3125));
        boxes.put(EntityType.DRAGON_FIREBALL, new Hitbox(1.0));
        boxes.put(EntityType.ZOMBIE_VILLAGER, new Hitbox(0.6, 1.95));
        boxes.put(EntityType.SKELETON_HORSE, new Hitbox(1.3964844, 1.6));
        boxes.put(EntityType.ZOMBIE_HORSE, new Hitbox(1.3964844, 1.6));
        boxes.put(EntityType.DONKEY, new Hitbox(1.3964844, 1.6));
        boxes.put(EntityType.MULE, new Hitbox(1.3964844, 1.6));
        boxes.put(EntityType.EVOKER_FANGS, new Hitbox(0.5, 0.8));
        boxes.put(EntityType.EVOKER, new Hitbox(0.6, 1.95));
        boxes.put(EntityType.VEX, new Hitbox(0.4, 0.8));
        boxes.put(EntityType.VINDICATOR, new Hitbox(0.6, 1.95));
        boxes.put(EntityType.ILLUSIONER, new Hitbox(0.6, 1.95));
        boxes.put(EntityType.MINECART_COMMAND, new Hitbox(0.98, 0.7));
        boxes.put(EntityType.BOAT, new Hitbox(1.375, 0.5625));
        boxes.put(EntityType.MINECART, new Hitbox(0.98, 0.7));
        boxes.put(EntityType.MINECART_CHEST, new Hitbox(0.98, 0.7));
        boxes.put(EntityType.MINECART_FURNACE, new Hitbox(0.98, 0.7));
        boxes.put(EntityType.MINECART_TNT, new Hitbox(0.98, 0.7));
        boxes.put(EntityType.MINECART_HOPPER, new Hitbox(0.98, 0.7));
        boxes.put(EntityType.MINECART_MOB_SPAWNER, new Hitbox(0.98, 0.7));
        boxes.put(EntityType.CREEPER, new Hitbox(0.6, 1.7));
        boxes.put(EntityType.SKELETON, new Hitbox(0.6, 1.99));
        boxes.put(EntityType.SPIDER, new Hitbox(1.4, 0.9));
        boxes.put(EntityType.GIANT, new Hitbox(3.6, 10.8));
        boxes.put(EntityType.ZOMBIE, new Hitbox(0.6, 1.95));
        boxes.put(EntityType.SLIME, new Hitbox(0.51000005));
        boxes.put(EntityType.GHAST, new Hitbox(4));
        boxes.put(EntityType.PIG_ZOMBIE, new Hitbox(0.6, 1.95));
        boxes.put(EntityType.ENDERMAN, new Hitbox(0.6, 2.9));
        boxes.put(EntityType.CAVE_SPIDER, new Hitbox(0.7, 0.5));
        boxes.put(EntityType.SILVERFISH, new Hitbox(0.4, 0.3));
        boxes.put(EntityType.BLAZE, new Hitbox(0.6, 1.8));
        boxes.put(EntityType.MAGMA_CUBE, new Hitbox(0.51000005));
        boxes.put(EntityType.ENDER_DRAGON, new Hitbox(16.0, 8.0));
        boxes.put(EntityType.WITHER, new Hitbox(0.9, 3.5));
        boxes.put(EntityType.BAT, new Hitbox(0.5, 0.9));
        boxes.put(EntityType.WITCH, new Hitbox(0.6, 1.95));
        boxes.put(EntityType.ENDERMITE, new Hitbox(0.4, 0.3));
        boxes.put(EntityType.GUARDIAN, new Hitbox(0.85));
        boxes.put(EntityType.SHULKER, new Hitbox(1.0, 1.0));
        boxes.put(EntityType.PIG, new Hitbox(0.9));
        boxes.put(EntityType.SHEEP, new Hitbox(0.9, 1.3));
        boxes.put(EntityType.COW, new Hitbox(0.9, 1.4));
        boxes.put(EntityType.CHICKEN, new Hitbox(0.4, 0.7));
        boxes.put(EntityType.SQUID, new Hitbox(0.8));
        boxes.put(EntityType.WOLF, new Hitbox(0.6, 0.85));
        boxes.put(EntityType.MUSHROOM_COW, new Hitbox(0.9, 1.4));
        boxes.put(EntityType.SNOWMAN, new Hitbox(0.7, 1.9));
        boxes.put(EntityType.OCELOT, new Hitbox(0.6, 0.7));
        boxes.put(EntityType.IRON_GOLEM, new Hitbox(1.4, 2.7));
        boxes.put(EntityType.HORSE, new Hitbox(1.3964844, 1.6));
        boxes.put(EntityType.RABBIT, new Hitbox(0.4, 0.5));
        boxes.put(EntityType.POLAR_BEAR, new Hitbox(1.3, 1.4));
        boxes.put(EntityType.LLAMA, new Hitbox(0.9, 1.87));
        boxes.put(EntityType.LLAMA_SPIT, new Hitbox(0.25));
        boxes.put(EntityType.PARROT, new Hitbox(0.5, 0.9));
        boxes.put(EntityType.VILLAGER, new Hitbox(0.6, 1.95));
        boxes.put(EntityType.ENDER_CRYSTAL, new Hitbox(2.0));
    }

    private Vector min;
    private Vector max;

    public Hitbox(Vector min, Vector max) {
        this.min = min;
        this.max = max;
    }

    private Hitbox(double side) {
        this(new Vector(-side / 2, 0.0, -side / 2), new Vector(side / 2, side, side / 2));
    }

    private Hitbox(double side, double height) {
        this(new Vector(-side / 2, 0.0, -side / 2), new Vector(side / 2, height, side / 2));
    }

    public static IHitbox getByType(EntityType type) {
        return boxes.get(type);
    }

    public static IHitbox getByEntity(Entity entity) {
        if (entity instanceof Slime) {
            Hitbox hb = boxes.get(EntityType.SLIME);
            int size = ((Slime) entity).getSize();
            return new Hitbox(
                    new Vector(hb.min.getX() * size, hb.min.getY() * size, hb.min.getZ() * size),
                    new Vector(hb.max.getX() * size, hb.max.getY() * size, hb.max.getZ() * size)
            );
        } else if (entity instanceof AreaEffectCloud) {
            Hitbox hb = boxes.get(EntityType.SLIME);
            float size = ((AreaEffectCloud) entity).getRadius();
            return new Hitbox(
                    new Vector(hb.min.getX() * size, hb.min.getY(), hb.min.getZ() * size),
                    new Vector(hb.max.getX() * size, hb.max.getY(), hb.max.getZ() * size)
            );
        } else if (entity instanceof ArmorStand) {
            ArmorStand as = (ArmorStand) entity;
            if (as.isMarker()) return new Hitbox(0);
            if (as.isSmall()) return new Hitbox(0.25, 0.9875);
            return new Hitbox(0.5, 1.975);
        } else if (entity instanceof Player) {
            Player p = (Player) entity;
            if (p.isSneaking()) return new Hitbox(0.6, 1.65);
            if (p.isGliding()) return new Hitbox(0.6);
            if (p.isSleeping()) return new Hitbox(0.2);
            return new Hitbox(0.6, 1.8);
        } else return boxes.get(entity.getType());
    }

    @Override
    public Vector getMin() {
        return min;
    }

    @Override
    public void setMin(Vector min) {
        this.min = min;
    }

    @Override
    public Vector getMax() {
        return max;
    }

    @Override
    public void setMax(Vector max) {
        this.max = max;
    }

    @Override
    public boolean intersects(Vector origin, Vector lineOfSight, Vector hitboxLocation) {
        Vector sMin, sMax;
        sMin = min.clone().add(hitboxLocation);
        sMax = max.clone().add(hitboxLocation);

        Vector divided = new Vector(1, 1, 1).divide(lineOfSight);

        double t1 = (sMin.getX() - origin.getX()) * divided.getX();
        double t2 = (sMax.getX() - origin.getX()) * divided.getX();
        double t3 = (sMin.getY() - origin.getY()) * divided.getY();
        double t4 = (sMax.getY() - origin.getY()) * divided.getY();
        double t5 = (sMin.getZ() - origin.getZ()) * divided.getZ();
        double t6 = (sMax.getZ() - origin.getZ()) * divided.getZ();
        double tMin = Math.max(Math.max(Math.min(t1, t2), Math.min(t3, t4)), Math.min(t5, t6));
        double tMax = Math.min(Math.min(Math.max(t1, t2), Math.max(t3, t4)), Math.max(t5, t6));

        return tMax > 0 && tMin < tMax;
    }

    @Override
    public IHitbox deepClone() {
        return new Hitbox(this.min.clone(), this.max.clone());
    }
}
