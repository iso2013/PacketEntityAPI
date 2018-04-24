package net.blitzcube.peapi.packet;

import com.comphenix.protocol.events.PacketContainer;
import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;
import net.blitzcube.peapi.api.event.IEntityPacketEvent;
import net.blitzcube.peapi.api.packet.IEntityPacket;
import org.bukkit.entity.Player;

/**
 * Created by iso2013 on 4/21/2018.
 */
public abstract class EntityPacket implements IEntityPacket {
    final PacketContainer rawPacket;
    private final IEntityIdentifier identifier;

    EntityPacket(IEntityIdentifier identifier, PacketContainer rawPacket) {
        this.identifier = identifier;
        this.rawPacket = rawPacket;
    }

    public static EntityPacket unwrapFromType(int entityID, IEntityPacketEvent.EntityPacketType type, PacketContainer
            c, Player target) {
        switch (type) {
            case ANIMATION:
                return EntityAnimationPacket.unwrap(entityID, c, target);
            case CLICK:
                return EntityClickPacket.unwrap(entityID, c, target);
            case DATA:
                return EntityDataPacket.unwrap(entityID, c, target);
            case DESTROY:
                return EntityDestroyPacket.unwrap(c, target);
            case EQUIPMENT:
                return EntityEquipmentPacket.unwrap(entityID, c, target);
            case ENTITY_SPAWN:
                return EntitySpawnPacket.unwrap(entityID, c, target);
            case MOUNT:
                return EntityMountPacket.unwrap(entityID, c, target);
            case STATUS:
                return EntityStatusPacket.unwrap(entityID, c, target);
            case OBJECT_SPAWN:
                return ObjectSpawnPacket.unwrap(entityID, c, target);
        }
        return null;
    }

    @Override
    public IEntityIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public PacketContainer getRawPacket() {
        return rawPacket;
    }
}
