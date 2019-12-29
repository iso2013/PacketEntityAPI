package net.iso2013.peapi.entity.fake;

import net.iso2013.peapi.PacketEntityAPIPlugin;
import net.iso2013.peapi.api.entity.fake.FakeEntity;
import net.iso2013.peapi.api.entity.fake.FakeEntityFactory;
import net.iso2013.peapi.api.entity.hitbox.Hitbox;
import net.iso2013.peapi.api.entity.modifier.EntityModifier;
import net.iso2013.peapi.entity.hitbox.HitboxImpl;
import net.iso2013.peapi.util.ReflectUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class FakeEntityFactoryImpl implements FakeEntityFactory {
    private static Field entityId = ReflectUtil.getField(ReflectUtil.getNMSClass("Entity"), "entityCount");

    private final PacketEntityAPIPlugin parent;
    private final Map<Integer, FakeEntityImpl> fakeEntities;

    private int fallbackId = -1;


    public FakeEntityFactoryImpl(PacketEntityAPIPlugin parent) {
        this.parent = parent;
        this.fakeEntities = new HashMap<>();
    }

    private int getNextID() {
        if (entityId == null) return fallbackId--;
        try {
            Object o = entityId.get(null);
            if(o instanceof Integer) {
                int id = (int) o;
                entityId.setInt(null, id + 1);
                return id;
            } else if(o instanceof AtomicInteger) {
                return ((AtomicInteger) o).incrementAndGet();
            } throw new IllegalAccessException("Was not an int or an atomic int.");
        } catch (IllegalAccessException e) {
            Bukkit.getLogger().severe("PacketEntityAPI switching to negative-based IDs - unable to access " +
                    "entity ID field.");
            entityId = null;
            return fallbackId--;
        }
    }

    @Override
    public Hitbox createHitboxFromType(EntityType type) {
        Hitbox h = HitboxImpl.getByType(type);
        if (h == null) return null;
        return h.deepClone();
    }

    @Override
    public Hitbox createHitboxFromEntity(Entity entity) {
        Hitbox h = HitboxImpl.getByEntity(entity);
        if (h == null) return null;
        return h.deepClone();
    }

    @Override
    public Hitbox createHitbox(Vector min, Vector max) {
        return new HitboxImpl(min, max);
    }

    @Override
    public FakeEntity createFakeEntity(EntityType type) {
        return createFakeEntity(type, false);
    }

    @Override
    public FakeEntity createFakeEntity(EntityType type, boolean lookupModifiers) {
        Map<String, EntityModifier> modifiers;
        if (lookupModifiers) {
            modifiers = parent.getModifierRegistry().lookup(type).stream()
                    .collect(Collectors.toMap(EntityModifier::getLabel, it -> it));
        } else modifiers = new HashMap<>();

        FakeEntityImpl entity = new FakeEntityImpl(getNextID(), UUID.randomUUID(), type, modifiers);
        fakeEntities.put(entity.getEntityID(), entity);
        return entity;
    }

    @Override
    public boolean isFakeEntity(int entityID) {
        return fakeEntities.containsKey(entityID);
    }

    @Override
    public FakeEntityImpl getFakeByID(int entityID) {
        return fakeEntities.get(entityID);
    }

    public Collection<FakeEntityImpl> getFakeEntities() {
        return fakeEntities.values();
    }
}
