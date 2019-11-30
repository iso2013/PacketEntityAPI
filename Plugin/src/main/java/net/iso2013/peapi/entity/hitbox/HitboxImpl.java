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
