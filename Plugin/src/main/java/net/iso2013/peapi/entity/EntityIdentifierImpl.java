package net.iso2013.peapi.entity;

import net.iso2013.peapi.PacketEntityAPIPlugin;
import net.iso2013.peapi.api.entity.EntityIdentifier;
import net.iso2013.peapi.entity.fake.FakeEntityImpl;
import net.iso2013.peapi.util.ReflectUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;

/**
 * Created by iso2013 on 2/24/2018.
 */
public class EntityIdentifierImpl implements EntityIdentifier {
    // All versions
    private static final Class<?> cbWorld;
    private static final Method getHandle;
    private static final Method getBukkit;

    // 1.14
    private static final Field entityMap;

    // 1.13 and below
    private static final Field tracker;
    private static final Field trackedEntities;
    private static final Method lookup;
    private static final Field entryData;
    private static final Field target;

    static {
        cbWorld = ReflectUtil.getCBClass("CraftWorld");
        getHandle = ReflectUtil.getMethod(cbWorld, "getHandle");
        getBukkit = ReflectUtil.getMethod(ReflectUtil.getNMSClass("Entity"), "getBukkitEntity");

        Class<?> wsClazz = ReflectUtil.getNMSClass("WorldServer");

        entityMap = ReflectUtil.getField(wsClazz, "entitiesById");

        if (entityMap == null) {
            // We are running 1.13 or below
            Class<?> intMapClazz = ReflectUtil.getNMSClass("IntHashMap");

            tracker = ReflectUtil.getField(wsClazz, "tracker");
            trackedEntities = ReflectUtil.getField(ReflectUtil.getNMSClass("EntityTracker"), "trackedEntities");
            lookup = ReflectUtil.getMethod(intMapClazz, "c", int.class);
            entryData = ReflectUtil.getField(
                    ReflectUtil.getNMSClass("IntHashMap$IntHashMapEntry"),
                    Object.class
            );
            target = ReflectUtil.getField(ReflectUtil.getNMSClass("EntityTrackerEntry"), "tracker");
        } else {
            tracker = null;
            trackedEntities = null;
            entryData = null;
            target = null;
            lookup = null;
        }
    }

    protected EntityIdentifierImpl(int entityID, UUID uuid) {
        this.entityID = entityID;
        this.uuid = uuid;
    }

    public static EntityIdentifier produce(int entityID, UUID uuid, Player near) {
        FakeEntityImpl fakeEntity = PacketEntityAPIPlugin.getFakeEntity(entityID);

        if(fakeEntity != null) return fakeEntity;

        Entity realEntity = Bukkit.getEntity(uuid);
        if (realEntity != null) {
            return new RealEntityIdentifier(realEntity);
        }

        realEntity = getEntityByID(near, entityID);
        if (realEntity != null) {
            return new RealEntityIdentifier(realEntity);
        }

        return new EntityIdentifierImpl(entityID, uuid);
    }

    @SuppressWarnings("unchecked")
    private static Entity getEntityByID(Player near, int entityID) {
        try {
            Object worldServer = getHandle.invoke(cbWorld.cast(near.getWorld()));

            if (entityMap != null) {
                Object en = ((Map<Integer, Object>) entityMap.get(worldServer)).get(entityID);
                if (en != null) return (Entity) getBukkit.invoke(en);
                if (near.getEntityId() == entityID) {
                    return near;
                }
            } else {
                Object lookupResult = lookup.invoke(trackedEntities.get(tracker.get(worldServer)), entityID);
                if (lookupResult != null) {
                    Object en = target.get(entryData.get(lookupResult));
                    if (en != null) return (Entity) getBukkit.invoke(en);
                }
            }

            if (near.getEntityId() == entityID) {
                return near;
            } else return null;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private final int entityID;
    private final UUID uuid;

    public static EntityIdentifier produce(int entityID, Player near) {
        FakeEntityImpl fakeEntity = PacketEntityAPIPlugin.getFakeEntity(entityID);

        if (fakeEntity != null) return fakeEntity;

        Entity realEntity = getEntityByID(near, entityID);

        if (realEntity != null) {
            return new RealEntityIdentifier(realEntity);
        }

        return new EntityIdentifierImpl(entityID, null);
    }

    @Override
    public int getEntityID() {
        return entityID;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    public static class RealEntityIdentifier extends EntityIdentifierImpl implements net.iso2013.peapi.api.entity.RealEntityIdentifier {
        private final Entity entity;

        public RealEntityIdentifier(Entity entity) {
            super(entity.getEntityId(), entity.getUniqueId());
            this.entity = entity;
        }

        @Override
        public Entity getEntity() {
            return entity;
        }
    }
}