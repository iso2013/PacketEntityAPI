package net.iso2013.peapi.packet;

import com.comphenix.protocol.events.PacketContainer;
import net.iso2013.peapi.api.entity.EntityIdentifier;
import net.iso2013.peapi.api.event.EntityPacketEvent;
import net.iso2013.peapi.api.packet.EntityPacket;
import org.bukkit.entity.Player;

/**
 * Created by iso2013 on 4/21/2018.
 */
public abstract class EntityPacketImpl implements EntityPacket {
    private final EntityIdentifier identifier;
    PacketContainer rawPacket;

    EntityPacketImpl(EntityIdentifier identifier, PacketContainer rawPacket, boolean writeDefaults) {
        this.identifier = identifier;
        this.rawPacket = rawPacket;
        if (writeDefaults)
            this.rawPacket.getModifier().writeDefaults();

        if (identifier != null)
            this.rawPacket.getIntegers().write(0, identifier.getEntityID());
    }

    public static EntityPacketImpl unwrapFromType(int entityID, EntityPacketEvent.EntityPacketType type, PacketContainer
            c, Player target) {
        switch (type) {
            case ANIMATION:
                return EntityAnimationPacketImpl.unwrap(entityID, c, target);
            case CLICK:
                return EntityClickPacketImpl.unwrap(entityID, c, target);
            case DATA:
                return EntityDataPacketImpl.unwrap(entityID, c, target);
            case DESTROY:
                return EntityDestroyPacketImpl.unwrap(c, target);
            case EQUIPMENT:
                return EntityEquipmentPacketImpl.unwrap(entityID, c, target);
            case ENTITY_SPAWN:
                return EntitySpawnPacketImpl.unwrap(entityID, c, target);
            case MOUNT:
                return EntityMountPacketImpl.unwrap(entityID, c, target);
            case STATUS:
                return EntityStatusPacketImpl.unwrap(entityID, c, target);
            case OBJECT_SPAWN:
                return ObjectSpawnPacketImpl.unwrap(entityID, c, target);
            case ADD_EFFECT:
                return EntityPotionAddPacketImpl.unwrap(entityID, c, target);
            case REMOVE_EFFECT:
                return EntityPotionRemovePacketImpl.unwrap(entityID, c, target);
            case MOVE:
                return EntityMovePacketImpl.unwrap(entityID, c, target);
        }
        return null;
    }

    @Override
    public EntityIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(EntityIdentifier identifier) {
        if (identifier != null)
            this.rawPacket.getIntegers().write(0, identifier.getEntityID());
    }

    @Override
    public PacketContainer getRawPacket() {
        return rawPacket;
    }

    @Override
    public abstract EntityPacketImpl clone();
}
