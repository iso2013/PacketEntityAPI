package net.blitzcube.peapi.entity;

import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;
import java.util.UUID;

/**
 * Created by iso2013 on 2/24/2018.
 */
public class EntityIdentifier implements IEntityIdentifier {
    private final int entityID;
    private UUID uuid;
    private WeakReference<Player> near;
    private WeakReference<Entity> entity;
    private WeakReference<Object> fakeEntity; //TODO

    public EntityIdentifier(int entityID, UUID uuid, Player near) {
        this.entityID = entityID;
        this.uuid = uuid;
        this.near = new WeakReference<>(near);
    }

    public EntityIdentifier(int entityID, Player near) {
        this(entityID, null, near);
    }

    @Override
    public void moreSpecific() {

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
    public WeakReference<Player> getNear() {
        return near;
    }

    @Override
    public WeakReference<Entity> getEntity() {
        return entity;
    }

    @Override
    public WeakReference<Object> getFakeEntity() {
        return fakeEntity;
    }
}