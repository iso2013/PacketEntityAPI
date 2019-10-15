package net.blitzcube.peapi.entity;

import net.blitzcube.peapi.PacketEntityAPI;
import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.api.entity.fake.IFakeEntity;
import net.blitzcube.peapi.entity.fake.FakeEntity;
import org.bukkit.Bukkit;
import org.bukkit.World;
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
    private static Class<?> cbWorld;
    private static Method getHandle;
    private static Field entityMap;
    private static Method getBukkit;

    static {
        try {
            String packageVer = Bukkit.getServer().getClass().getPackage().getName();
            packageVer = packageVer.substring(packageVer.lastIndexOf('.') + 1);

            cbWorld = Class.forName("org.bukkit.craftbukkit." + packageVer + ".CraftWorld");
            getHandle = cbWorld.getDeclaredMethod("getHandle");
            Class<?> wsClazz = Class.forName("net.minecraft.server." + packageVer + ".WorldServer");
            entityMap = wsClazz.getDeclaredField("entitiesById");
            Class<?> enClazz = Class.forName("net.minecraft.server." + packageVer + ".Entity");
            getBukkit = enClazz.getDeclaredMethod("getBukkitEntity");
        } catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static EntityIdentifier produce(int entityID, UUID uuid, Player near) {
        IFakeEntity fakeEntity = PacketEntityAPI.getFakeEntity(entityID);
        Entity realEntity = Bukkit.getEntity(uuid);

        if(realEntity != null) {
            return new EntityIdentifier(entityID, uuid, realEntity);
        } else if (fakeEntity != null) {
            return new EntityIdentifier(entityID, uuid, fakeEntity);
        }

        return null;
    }

    public static EntityIdentifier produce(int entityID, Player near) {
        IFakeEntity fakeEntity = PacketEntityAPI.getFakeEntity(entityID);
        Entity realEntity = getEntityByID(near.getWorld(), entityID);

        if(realEntity != null) {
            return new EntityIdentifier(entityID, realEntity.getUniqueId(), realEntity);
        } else if (fakeEntity != null) {
            return new EntityIdentifier(entityID, fakeEntity.getUUID(), fakeEntity);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private static Entity getEntityByID(World world, int entityID) {
        try {
            Object o = ((Map<Integer, Object>) entityMap.get(getHandle.invoke(cbWorld.cast(world)))).get(entityID);

            if(o == null) return null;

            return (Entity) getBukkit.invoke(o);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
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