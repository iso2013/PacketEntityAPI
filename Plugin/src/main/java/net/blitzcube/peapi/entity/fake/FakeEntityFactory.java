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
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class FakeEntityFactory implements IFakeEntityFactory {
    private PacketEntityAPI parent;
    private Field entityID;
    private int fallbackId = -1;

    public FakeEntityFactory(PacketEntityAPI parent) {
        this.parent = parent;

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
        if (lookupModifiers) {
            return new FakeEntity(
                    getNextID(),
                    UUID.randomUUID(),
                    type,
                    parent.getModifierRegistry().lookup(type).stream()
                            .collect(Collectors.toMap(IEntityModifier::getLabel, it -> it))
            );
        } else {
            return new FakeEntity(
                    getNextID(),
                    UUID.randomUUID(),
                    type,
                    new HashMap<>()
            );
        }
    }
}
