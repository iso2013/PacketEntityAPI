package net.blitzcube.peapi.entity;

import net.blitzcube.peapi.PacketEntityAPI;
import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.api.entity.fake.IFakeEntity;
import net.blitzcube.peapi.entity.fake.FakeEntity;
import net.blitzcube.peapi.util.ReflectUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by iso2013 on 2/24/2018.
 */
public class EntityIdentifier implements IEntityIdentifier {
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

    private final int entityID;
    private final UUID uuid;
    private final Entity entity;
    private final IFakeEntity fakeEntity;

    public EntityIdentifier(Entity entity) {
        this.entityID = entity.getEntityId();
        this.uuid = entity.getUniqueId();
        this.entity = entity;
        this.fakeEntity = null;
    }

    public EntityIdentifier(FakeEntity fakeEntity) {
        this.entityID = fakeEntity.getEntityID();
        this.uuid = fakeEntity.getUUID();
        this.entity = null;
        this.fakeEntity = fakeEntity;
    }

    private EntityIdentifier(int entityID, UUID uuid, Entity realEntity) {
        this.entityID = entityID;
        this.uuid = uuid;
        this.entity = realEntity;
        this.fakeEntity = null;
    }

    private EntityIdentifier(int entityID, UUID uuid, IFakeEntity fakeEntity) {
        this.entityID = entityID;
        this.uuid = uuid;
        this.entity = null;
        this.fakeEntity = fakeEntity;
    }

    public static EntityIdentifier produce(int entityID, UUID uuid, Player near) {
        IFakeEntity fakeEntity = PacketEntityAPI.getFakeEntity(entityID);
        Entity realEntity = Bukkit.getEntity(uuid);

        if (realEntity != null) {
            return new EntityIdentifier(entityID, uuid, realEntity);
        } else if (fakeEntity != null) {
            return new EntityIdentifier(entityID, uuid, fakeEntity);
        }

        return produce(entityID, near, false);
    }

    public static EntityIdentifier produce(int entityID, Player near, boolean isDestroy) {
        IFakeEntity fakeEntity = PacketEntityAPI.getFakeEntity(entityID);
        Entity realEntity = getEntityByID(near, entityID, isDestroy);

        if (realEntity != null) {
            return new EntityIdentifier(entityID, realEntity.getUniqueId(), realEntity);
        } else if (fakeEntity != null) {
            return new EntityIdentifier(entityID, fakeEntity.getUUID(), fakeEntity);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private static Entity getEntityByID(Player near, int entityID, boolean isDestroy) {
        try {
            Object worldServer = getHandle.invoke(cbWorld.cast(near.getWorld()));

            if (entityMap != null) {
                Object en = ((Map<Integer, Object>) entityMap.get(worldServer)).get(entityID);
                if (en != null) return (Entity) getBukkit.invoke(en);
                if (near.getEntityId() == entityID) {
                    return near;
                } else return null;
            } else {
                Object lookupResult = lookup.invoke(trackedEntities.get(tracker.get(worldServer)), entityID);
                if (lookupResult != null) {
                    Object en = target.get(entryData.get(lookupResult));
                    if (en != null) return (Entity) getBukkit.invoke(en);
                }
            }

            if (isDestroy) {
                return SightDistanceRegistry.getNearby(near, 1.03)
                        .filter(entity -> entity.getEntityId() == entityID).findAny().orElse(null);
            } else if (near.getEntityId() == entityID) {
                return near;
            } else return null;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getEntityID() {
        return entityID;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public IFakeEntity getFakeEntity() {
        return fakeEntity;
    }

    @Override
    public boolean isFakeEntity() {
        return fakeEntity != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityIdentifier that = (EntityIdentifier) o;

        if (entityID != that.entityID) return false;
        if (!uuid.equals(that.uuid)) return false;
        if (!Objects.equals(entity, that.entity)) return false;
        return Objects.equals(fakeEntity, that.fakeEntity);
    }

    @Override
    public int hashCode() {
        int result = entityID;
        result = 31 * result + uuid.hashCode();
        result = 31 * result + (entity != null ? entity.hashCode() : 0);
        result = 31 * result + (fakeEntity != null ? fakeEntity.hashCode() : 0);
        return result;
    }
}