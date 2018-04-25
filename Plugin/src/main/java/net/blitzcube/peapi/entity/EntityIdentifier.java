package net.blitzcube.peapi.entity;

import net.blitzcube.peapi.PacketEntityAPI;
import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.api.entity.fake.IFakeEntity;
import net.blitzcube.peapi.entity.fake.FakeEntity;
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
    private WeakReference<IFakeEntity> fakeEntity;

    public EntityIdentifier(int entityID, UUID uuid, Player near) {
        this.entityID = entityID;
        this.uuid = uuid;
        this.near = new WeakReference<>(near);
    }

    public EntityIdentifier(int entityID, Player near) {
        this(entityID, null, near);
    }

    public EntityIdentifier(FakeEntity fakeEntity) {
        this.entityID = fakeEntity.getEntityID();
        this.uuid = fakeEntity.getUUID();
        this.fakeEntity = new WeakReference<>(fakeEntity);
    }

    public EntityIdentifier(Entity entity) {
        this.entityID = entity.getEntityId();
        this.uuid = entity.getUniqueId();
        this.entity = new WeakReference<>(entity);
    }

    @Override
    public void moreSpecific() {
        if (this.near == null) return;
        if (PacketEntityAPI.isFakeEntity(this.entityID)) {
            this.fakeEntity = new WeakReference<>(PacketEntityAPI.getFakeEntity(this.entityID));
        } else {
            if (this.near.get() == null) return;
            Entity e = SightDistanceRegistry.getNearby(near.get(), 1.03).filter(entity ->
                    entity.getEntityId() == entityID && (uuid == null || entity.getUniqueId().equals(uuid))).findAny()
                    .orElse(null);
            if (e == null) return;
            this.entity = new WeakReference<>(e);
            if (this.uuid == null) this.uuid = e.getUniqueId();
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
    public WeakReference<Player> getNear() {
        return near;
    }

    @Override
    public WeakReference<Entity> getEntity() {
        return entity;
    }

    @Override
    public WeakReference<IFakeEntity> getFakeEntity() {
        return fakeEntity;
    }

    @Override
    public boolean isFakeEntity() {
        return fakeEntity != null;
    }
}