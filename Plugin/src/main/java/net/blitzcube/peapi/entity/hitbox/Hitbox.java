package net.blitzcube.peapi.entity.hitbox;

import net.blitzcube.peapi.api.entity.hitbox.IHitbox;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class Hitbox implements IHitbox {
    //https://youtu.be/QWzYaZDK6Is?t=122
    private static final Map<EntityType, Hitbox> boxes = new HashMap<>();

    private static final String version;
    private static final Class<?> world;
    private static final Method getBB;
    private static final Method getHandle;
    private static final Field[] coordinates;

    static {
        String v = Bukkit.getServer().getClass().getPackage().getName();
        version = v.substring(v.lastIndexOf('.') + 1);

        Class<?> tempWorld;
        Method tempGetBB;
        Method tempGetHandle;
        Field[] tempCoordinates;
        try {
            tempWorld = Class.forName("net.minecraft.server." + version + ".World");
            tempGetBB = Class.forName("net.minecraft.server." + version + ".Entity")
                    .getDeclaredMethod("getBoundingBox");
            Class<?> bbClass = Class.forName("net.minecraft.server." + version + ".AxisAlignedBB");
            tempCoordinates = new Field[]{
                    bbClass.getDeclaredField("a"),
                    bbClass.getDeclaredField("b"),
                    bbClass.getDeclaredField("c"),
                    bbClass.getDeclaredField("d"),
                    bbClass.getDeclaredField("e"),
                    bbClass.getDeclaredField("f"),
            };
            tempGetHandle = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftEntity")
                    .getDeclaredMethod("getHandle");
        } catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
            tempWorld = null;
            tempGetBB = null;
            tempCoordinates = null;
            tempGetHandle = null;
        }
        world = tempWorld;
        getBB = tempGetBB;
        getHandle = tempGetHandle;
        coordinates = tempCoordinates;
    }

    private Vector min;
    private Vector max;

    public Hitbox(Vector min, Vector max) {
        this.min = min;
        this.max = max;
    }

    public static IHitbox getByType(EntityType type) {
        if (boxes.containsKey(type)) return boxes.get(type);

        try {
            String className = type.getEntityClass().getName();
            Class<?> mcClass = Class.forName("net.minecraft.server." + version + ".Entity" + className.substring
                    (className.lastIndexOf('.') + 1));
            Object bb = getBB.invoke(mcClass.getConstructor(world).newInstance(new Object[]{null}));
            Hitbox box = new Hitbox(
                    new Vector(
                            (double) coordinates[0].get(bb),
                            (double) coordinates[1].get(bb),
                            (double) coordinates[2].get(bb)
                    ),
                    new Vector(
                            (double) coordinates[3].get(bb),
                            (double) coordinates[4].get(bb),
                            (double) coordinates[5].get(bb)
                    )
            );
            boxes.put(type, box);
            return box;
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException
                | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static IHitbox getByEntity(Entity entity) {
        if (boxes.containsKey(entity.getType())) return boxes.get(entity.getType());

        try {
            String className = entity.getType().getEntityClass().getName();
            Class<?> cbClass = Class.forName("org.bukkit.craftbukkit." + version + ".entity.Craft" + className.substring
                    (className.lastIndexOf('.') + 1));
            Object bb = getBB.invoke(getHandle.invoke(cbClass.cast(entity)));
            Location l = entity.getLocation();
            Hitbox box = new Hitbox(
                    new Vector(
                            (double) coordinates[0].get(bb) - l.getX(),
                            (double) coordinates[1].get(bb) - l.getY(),
                            (double) coordinates[2].get(bb) - l.getZ()
                    ),
                    new Vector(
                            (double) coordinates[3].get(bb) - l.getX(),
                            (double) coordinates[4].get(bb) - l.getY(),
                            (double) coordinates[5].get(bb) - l.getZ()
                    )
            );
            boxes.put(entity.getType(), box);
            return box;
        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
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
