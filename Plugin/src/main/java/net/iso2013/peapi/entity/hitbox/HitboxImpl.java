package net.iso2013.peapi.entity.hitbox;

import net.iso2013.peapi.api.entity.hitbox.Hitbox;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class HitboxImpl implements Hitbox {
    //https://youtu.be/QWzYaZDK6Is?t=122
    private static final Map<EntityType, HitboxImpl> boxes = new EnumMap<>(EntityType.class);

    static {
        boxes.put(EntityType.DROPPED_ITEM, new HitboxImpl(0.25));
        boxes.put(EntityType.EXPERIENCE_ORB, new HitboxImpl(0.5));
        boxes.put(EntityType.AREA_EFFECT_CLOUD, new HitboxImpl(2.0, 0.5));
        boxes.put(EntityType.ELDER_GUARDIAN, new HitboxImpl(1.9975));
        boxes.put(EntityType.WITHER_SKELETON, new HitboxImpl(0.7, 2.4));
        boxes.put(EntityType.STRAY, new HitboxImpl(0.6, 1.99));
        boxes.put(EntityType.EGG, new HitboxImpl(0.25));
        boxes.put(EntityType.LEASH_HITCH, new HitboxImpl(0.375, 0.5));
        boxes.put(EntityType.ARROW, new HitboxImpl(0.5));
        boxes.put(EntityType.SNOWBALL, new HitboxImpl(0.25));
        boxes.put(EntityType.FIREBALL, new HitboxImpl(1.0));
        boxes.put(EntityType.SMALL_FIREBALL, new HitboxImpl(0.3125));
        boxes.put(EntityType.ENDER_PEARL, new HitboxImpl(0.25));
        boxes.put(EntityType.ENDER_SIGNAL, new HitboxImpl(0.25));
        boxes.put(EntityType.SPLASH_POTION, new HitboxImpl(0.25));
        boxes.put(EntityType.THROWN_EXP_BOTTLE, new HitboxImpl(0.25));
        boxes.put(EntityType.WITHER_SKULL, new HitboxImpl(0.3125));
        boxes.put(EntityType.PRIMED_TNT, new HitboxImpl(0.98));
        boxes.put(EntityType.FALLING_BLOCK, new HitboxImpl(0.98));
        boxes.put(EntityType.FIREWORK, new HitboxImpl(0.25));
        boxes.put(EntityType.HUSK, new HitboxImpl(0.6, 1.95));
        boxes.put(EntityType.SPECTRAL_ARROW, new HitboxImpl(0.5));
        boxes.put(EntityType.SHULKER_BULLET, new HitboxImpl(0.3125));
        boxes.put(EntityType.DRAGON_FIREBALL, new HitboxImpl(1.0));
        boxes.put(EntityType.ZOMBIE_VILLAGER, new HitboxImpl(0.6, 1.95));
        boxes.put(EntityType.SKELETON_HORSE, new HitboxImpl(1.3964844, 1.6));
        boxes.put(EntityType.ZOMBIE_HORSE, new HitboxImpl(1.3964844, 1.6));
        boxes.put(EntityType.DONKEY, new HitboxImpl(1.3964844, 1.6));
        boxes.put(EntityType.MULE, new HitboxImpl(1.3964844, 1.6));
        boxes.put(EntityType.EVOKER_FANGS, new HitboxImpl(0.5, 0.8));
        boxes.put(EntityType.EVOKER, new HitboxImpl(0.6, 1.95));
        boxes.put(EntityType.VEX, new HitboxImpl(0.4, 0.8));
        boxes.put(EntityType.VINDICATOR, new HitboxImpl(0.6, 1.95));
        boxes.put(EntityType.ILLUSIONER, new HitboxImpl(0.6, 1.95));
        boxes.put(EntityType.MINECART_COMMAND, new HitboxImpl(0.98, 0.7));
        boxes.put(EntityType.BOAT, new HitboxImpl(1.375, 0.5625));
        boxes.put(EntityType.MINECART, new HitboxImpl(0.98, 0.7));
        boxes.put(EntityType.MINECART_CHEST, new HitboxImpl(0.98, 0.7));
        boxes.put(EntityType.MINECART_FURNACE, new HitboxImpl(0.98, 0.7));
        boxes.put(EntityType.MINECART_TNT, new HitboxImpl(0.98, 0.7));
        boxes.put(EntityType.MINECART_HOPPER, new HitboxImpl(0.98, 0.7));
        boxes.put(EntityType.MINECART_MOB_SPAWNER, new HitboxImpl(0.98, 0.7));
        boxes.put(EntityType.CREEPER, new HitboxImpl(0.6, 1.7));
        boxes.put(EntityType.SKELETON, new HitboxImpl(0.6, 1.99));
        boxes.put(EntityType.SPIDER, new HitboxImpl(1.4, 0.9));
        boxes.put(EntityType.GIANT, new HitboxImpl(3.6, 10.8));
        boxes.put(EntityType.ZOMBIE, new HitboxImpl(0.6, 1.95));
        boxes.put(EntityType.SLIME, new HitboxImpl(0.51000005));
        boxes.put(EntityType.GHAST, new HitboxImpl(4));
        boxes.put(EntityType.PIG_ZOMBIE, new HitboxImpl(0.6, 1.95));
        boxes.put(EntityType.ENDERMAN, new HitboxImpl(0.6, 2.9));
        boxes.put(EntityType.CAVE_SPIDER, new HitboxImpl(0.7, 0.5));
        boxes.put(EntityType.SILVERFISH, new HitboxImpl(0.4, 0.3));
        boxes.put(EntityType.BLAZE, new HitboxImpl(0.6, 1.8));
        boxes.put(EntityType.MAGMA_CUBE, new HitboxImpl(0.51000005));
        boxes.put(EntityType.ENDER_DRAGON, new HitboxImpl(16.0, 8.0));
        boxes.put(EntityType.WITHER, new HitboxImpl(0.9, 3.5));
        boxes.put(EntityType.BAT, new HitboxImpl(0.5, 0.9));
        boxes.put(EntityType.WITCH, new HitboxImpl(0.6, 1.95));
        boxes.put(EntityType.ENDERMITE, new HitboxImpl(0.4, 0.3));
        boxes.put(EntityType.GUARDIAN, new HitboxImpl(0.85));
        boxes.put(EntityType.SHULKER, new HitboxImpl(1.0, 1.0));
        boxes.put(EntityType.PIG, new HitboxImpl(0.9));
        boxes.put(EntityType.SHEEP, new HitboxImpl(0.9, 1.3));
        boxes.put(EntityType.COW, new HitboxImpl(0.9, 1.4));
        boxes.put(EntityType.CHICKEN, new HitboxImpl(0.4, 0.7));
        boxes.put(EntityType.SQUID, new HitboxImpl(0.8));
        boxes.put(EntityType.WOLF, new HitboxImpl(0.6, 0.85));
        boxes.put(EntityType.MUSHROOM_COW, new HitboxImpl(0.9, 1.4));
        boxes.put(EntityType.SNOWMAN, new HitboxImpl(0.7, 1.9));
        boxes.put(EntityType.OCELOT, new HitboxImpl(0.6, 0.7));
        boxes.put(EntityType.IRON_GOLEM, new HitboxImpl(1.4, 2.7));
        boxes.put(EntityType.HORSE, new HitboxImpl(1.3964844, 1.6));
        boxes.put(EntityType.RABBIT, new HitboxImpl(0.4, 0.5));
        boxes.put(EntityType.POLAR_BEAR, new HitboxImpl(1.3, 1.4));
        boxes.put(EntityType.LLAMA, new HitboxImpl(0.9, 1.87));
        boxes.put(EntityType.LLAMA_SPIT, new HitboxImpl(0.25));
        boxes.put(EntityType.PARROT, new HitboxImpl(0.5, 0.9));
        boxes.put(EntityType.VILLAGER, new HitboxImpl(0.6, 1.95));
        boxes.put(EntityType.ENDER_CRYSTAL, new HitboxImpl(2.0));
    }

    private Vector min;
    private Vector max;

    public HitboxImpl(Vector min, Vector max) {
        this.min = min;
        this.max = max;
    }

    private HitboxImpl(double side) {
        this(side, side);
    }

    public HitboxImpl(double side, double height) {
        this(new Vector(-side / 2, 0.0, -side / 2), new Vector(side / 2, height, side / 2));
    }

    public static void initHitboxes(Map<EntityType, HitboxImpl> newBoxes) {
        boxes.putAll(newBoxes);
    }

    public static Hitbox getByType(EntityType type) {
        return boxes.get(type);
    }

    public static Hitbox getByEntity(Entity entity) {
        if (entity instanceof Slime) {
            HitboxImpl hb = boxes.get(EntityType.SLIME);
            int size = ((Slime) entity).getSize();
            return new HitboxImpl(
                    new Vector(hb.min.getX() * size, hb.min.getY() * size, hb.min.getZ() * size),
                    new Vector(hb.max.getX() * size, hb.max.getY() * size, hb.max.getZ() * size)
            );
        } else if (entity instanceof AreaEffectCloud) {
            HitboxImpl hb = boxes.get(EntityType.SLIME);
            float size = ((AreaEffectCloud) entity).getRadius();
            return new HitboxImpl(
                    new Vector(hb.min.getX() * size, hb.min.getY(), hb.min.getZ() * size),
                    new Vector(hb.max.getX() * size, hb.max.getY(), hb.max.getZ() * size)
            );
        } else if (entity instanceof ArmorStand) {
            ArmorStand as = (ArmorStand) entity;
            if (as.isMarker()) return new HitboxImpl(0);
            if (as.isSmall()) return new HitboxImpl(0.25, 0.9875);
            return new HitboxImpl(0.5, 1.975);
        } else if (entity instanceof Player) {
            Player p = (Player) entity;
            if (p.isSneaking()) return new HitboxImpl(0.6, 1.65);
            if (p.isGliding()) return new HitboxImpl(0.6);
            if (p.isSleeping()) return new HitboxImpl(0.2);
            return new HitboxImpl(0.6, 1.8);
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
    public Hitbox deepClone() {
        return new HitboxImpl(this.min.clone(), this.max.clone());
    }
}
