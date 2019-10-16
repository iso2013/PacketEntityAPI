package net.blitzcube.peapi.entity.fake;

import net.blitzcube.peapi.PacketEntityAPI;
import net.blitzcube.peapi.api.entity.fake.IFakeEntity;
import net.blitzcube.peapi.api.entity.fake.IFakeEntityFactory;
import net.blitzcube.peapi.api.entity.hitbox.IHitbox;
import net.blitzcube.peapi.api.entity.modifier.IEntityModifier;
import net.blitzcube.peapi.entity.hitbox.Hitbox;
import net.blitzcube.peapi.util.ReflectUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class FakeEntityFactory implements IFakeEntityFactory {
    private static Field entityId = ReflectUtil.getField(ReflectUtil.getNMSClass("Entity"), "entityCount");

    private final PacketEntityAPI parent;
    private final Map<Integer, FakeEntity> fakeEntities;

    private int fallbackId = -1;


    public FakeEntityFactory(PacketEntityAPI parent) {
        this.parent = parent;
        this.fakeEntities = new HashMap<>();
    }

    private int getNextID() {
        if (entityId == null) return fallbackId--;
        try {
            int id = entityId.getInt(null);
            entityId.setInt(null, id + 1);
            return id;
        } catch (IllegalAccessException e) {
            Bukkit.getLogger().severe("PacketEntityAPI switching to negative-based IDs - unable to access " +
                    "entity ID field.");
            entityId = null;
            return fallbackId--;
        }
    }

    @Override
    public IHitbox createHitboxFromType(EntityType type) {
        IHitbox h = Hitbox.getByType(type);
        if (h == null) return null;
        return h.deepClone();
    }

    @Override
    public IHitbox createHitboxFromEntity(Entity entity) {
        IHitbox h = Hitbox.getByEntity(entity);
        if (h == null) return null;
        return h.deepClone();
    }

    @Override
    public IHitbox createHitbox(Vector min, Vector max) {
        return new Hitbox(min, max);
    }

    @Override
    public IFakeEntity createFakeEntity(EntityType type) {
        return createFakeEntity(type, false);
    }

    @Override
    public IFakeEntity createFakeEntity(EntityType type, boolean lookupModifiers) {
        Map<String, IEntityModifier> modifiers;
        if (lookupModifiers) {
            modifiers = parent.getModifierRegistry().lookup(type).stream()
                    .collect(Collectors.toMap(IEntityModifier::getLabel, it -> it));
        } else modifiers = new HashMap<>();

        FakeEntity entity = new FakeEntity(getNextID(), UUID.randomUUID(), type, modifiers);
        fakeEntities.put(entity.getEntityID(), entity);
        return entity;
    }

    @Override
    public boolean isFakeEntity(int entityID) {
        return fakeEntities.containsKey(entityID);
    }

    @Override
    public IFakeEntity getFakeByID(int entityID) {
        return fakeEntities.get(entityID);
    }

    public Collection<FakeEntity> getFakeEntities() {
        return fakeEntities.values();
    }
}
