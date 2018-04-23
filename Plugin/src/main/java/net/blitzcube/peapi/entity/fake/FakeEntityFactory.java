package net.blitzcube.peapi.entity.fake;

import net.blitzcube.peapi.PacketEntityAPI;
import net.blitzcube.peapi.api.entity.fake.IFakeEntity;
import net.blitzcube.peapi.api.entity.fake.IFakeEntityFactory;
import net.blitzcube.peapi.api.entity.hitbox.IHitbox;
import net.blitzcube.peapi.api.entity.modifier.IEntityModifier;
import net.blitzcube.peapi.entity.hitbox.Hitbox;
import org.bukkit.Bukkit;
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
    private PacketEntityAPI parent;
    private Map<Integer, FakeEntity> fakeEntities;

    private Field entityID;
    private int fallbackId = -1;


    public FakeEntityFactory(PacketEntityAPI parent) {
        this.parent = parent;
        this.fakeEntities = new HashMap<>();

        String version = Bukkit.getServer().getClass().getPackage().getName();
        version = version.substring(version.lastIndexOf('.') + 1);

        try {
            entityID = Class.forName("net.minecraft.server." + version + ".Entity")
                    .getDeclaredField("entityCount");
            if (!entityID.isAccessible()) entityID.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            entityID = null;
        }
    }

    private int getNextID() {
        if (entityID == null) return fallbackId--;
        try {
            int id = entityID.getInt(null);
            entityID.setInt(null, id + 1);
            return id;
        } catch (IllegalAccessException e) {
            Bukkit.getLogger().severe("PacketEntityAPI switching to negative-based IDs - unable to access " +
                    "entity ID field.");
            entityID = null;
            return fallbackId--;
        }
    }

    @Override
    public IHitbox createHitboxFromType(EntityType type) {
        return Hitbox.getByType(type);
    }

    @Override
    public IHitbox createHitbox(Vector min, Vector max) {
        return new Hitbox(min, max);
    }

    @Override
    public IFakeEntity createFakeEntity(EntityType type) {
        return createFakeEntity(type, true);
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
