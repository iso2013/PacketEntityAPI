package net.blitzcube.peapi.api.entity;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;
import java.util.UUID;

/**
 * Created by iso2013 on 2/23/2018.
 */
public class EntityIdentifier {
    private int entityId;
    private UUID uuid;
    private World world;
    private WeakReference<Player> near;
    private EntityType type;
    private WeakReference<Entity> entity;
    private WeakReference<Object> fakeEntity; //TODO

    public EntityIdentifier(int entityID, UUID uuid, Player near, EntityType type) {
        this.entityId = entityID;
        this.uuid = uuid;
        this.near = new WeakReference<>(near);
        this.type = type;
    }

    public EntityType getEntityType() {
        return type;
    }
}
