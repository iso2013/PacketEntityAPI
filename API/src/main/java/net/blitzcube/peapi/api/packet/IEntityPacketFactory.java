package net.blitzcube.peapi.api.packet;

import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;
import org.bukkit.inventory.EntityEquipment;

/**
 * @author iso2013
 * @version 0.1
 * @since 2018-04-23
 */
public interface IEntityPacketFactory {
    /**
     * Create an animation packet using an entity identifier and an animation type.
     *
     * @param entity the entity to control in the packet
     * @param type   the type of animation to send
     * @return the constructed packet
     */
    IEntityPacket createAnimationPacket(IEntityIdentifier entity, IEntityPacketAnimation.AnimationType type);

    /**
     * Create an inbound click packet using an entity identifier.
     *
     * @param entity the entity to control in the packet
     * @return the constructed packet
     */
    IEntityPacket createClickPacket(IEntityIdentifier entity);

    /**
     * Create a data packet using the entity identifier. Data will be pulled from the identifier by default <b>only if
     * the identifier targets a fake entity.</b> Otherwise, you must modify the data manually.
     *
     * @param entity the entity to control in the packet
     * @return the constructed packet
     */
    IEntityPacket createDataPacket(IEntityIdentifier entity);

    /**
     * Creates a packet that destroys all of the entites given by the entity identifiers.
     *
     * @param entities the entities to be removed
     * @return the constructed packet
     */
    IEntityPacket createDestroyPacket(IEntityIdentifier... entities);

    /**
     * Creates a set of packets that will send out a set of equipment for an entity
     *
     * @param entity    the entity to control in the packet
     * @param equipment the equipment to be sent
     * @return the constructed packet
     */
    IEntityPacket[] createEquipmentPacket(IEntityIdentifier entity, EntityEquipment equipment);

    /**
     * Creates a mount packet that will update a vehicle's passengers
     *
     * @param vehicle    the vehicle whose passengers will be set
     * @param passengers the passengers to put onto the vehicle
     * @return the constructed packet
     */
    IEntityPacket createMountPacket(IEntityIdentifier vehicle, IEntityIdentifier... passengers);

    /**
     * Creates an entity spawn packet that will spawn an entity for a client.
     * <br>
     * <b>Do not use this method to spawn objects - use {@link #createObjectSpawnPacket(IEntityIdentifier)} instead.</b>
     * This one will just throw an error.
     *
     * @param identifier the entity to be created
     * @return the constructed packet
     */
    IEntityPacket createEntitySpawnPacket(IEntityIdentifier identifier);

    /**
     * Creates an object spawn packet that will spawn an object for a client.
     * <br>
     * <b>Do not use this method to spawn entities - use {@link #createEntitySpawnPacket(IEntityIdentifier)} instead
     * .</b>
     * This one will just throw an error.
     *
     * @param identifier the object to be created
     * @return the constructed packet
     */
    IEntityPacket[] createObjectSpawnPacket(IEntityIdentifier identifier);

    /**
     * Creates a packet that will update a given entity's status for a client.
     *
     * @param entity the entity whose status should be updated
     * @param status the new status to be set
     * @return the constructed packet
     */
    IEntityPacket createStatusPacket(IEntityIdentifier entity, byte status);
}
