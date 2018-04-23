package net.blitzcube.peapi.api.packet;

import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;
import org.bukkit.inventory.EntityEquipment;

/**
 * Created by iso2013 on 4/23/2018.
 */
public interface IEntityPacketFactory {
    IEntityPacket createAnimationPacket(IEntityIdentifier entity, IEntityPacketAnimation.AnimationType type);

    IEntityPacket createClickPacket(IEntityIdentifier entity);

    IEntityPacket createDataPacket(IEntityIdentifier entity);

    IEntityPacket createDestroyPacket(IEntityIdentifier... entities);

    IEntityPacket[] createEquipmentPacket(IEntityIdentifier entity, EntityEquipment equipment);

    IEntityPacket createMountPacket(IEntityIdentifier vehicle, IEntityIdentifier... passengers);

    IEntityPacket createEntitySpawnPacket(IEntityIdentifier identifier);

    IEntityPacket[] createObjectSpawnPacket(IEntityIdentifier identifier);

    IEntityPacket createStatusPacket(IEntityIdentifier entity, byte status);
}
