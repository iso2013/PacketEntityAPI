package net.blitzcube.peapi.entity;

import net.blitzcube.peapi.api.entity.fake.IFakeEntity;
import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;
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
    private final UUID uuid;
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
        //TODO
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