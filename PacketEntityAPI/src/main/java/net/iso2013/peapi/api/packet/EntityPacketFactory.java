package net.iso2013.peapi.api.packet;

import net.iso2013.peapi.api.entity.EntityIdentifier;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

/**
 * @author iso2013
 * @version 0.1
 * @since 2018-04-23
 */
public interface EntityPacketFactory {
    /**
     * Create an animation packet using an entity identifier and an animation type.
     *
     * @param entity the entity to control in the packet
     * @param type   the type of animation to send
     * @return the constructed packet
     */
    EntityAnimationPacket createAnimationPacket(EntityIdentifier entity, EntityAnimationPacket.AnimationType type);

    /**
     * Create an inbound click packet using an entity identifier.
     *
     * @param entity the entity to control in the packet
     * @param type   the click type of the packet to be constructed
     * @return the constructed packet
     */
    EntityClickPacket createClickPacket(EntityIdentifier entity, EntityClickPacket.ClickType type);

    /**
     * Create a data packet using the entity identifier. Data will be pulled from the identifier by default <b>only if
     * the identifier targets a fake entity.</b> Otherwise, you must modify the data manually.
     *
     * @param entity the entity to control in the packet
     * @return the constructed packet
     */
    EntityDataPacket createDataPacket(EntityIdentifier entity);

    /**
     * Creates a packet that destroys all of the entities given by the entity identifiers.
     *
     * @param entities the entities to be removed
     * @return the constructed packet
     */
    EntityDestroyPacket createDestroyPacket(EntityIdentifier... entities);

    /**
     * Creates a set of packets that will send out a set of equipment for an entity
     *
     * @param entity    the entity to control in the packet
     * @param equipment the equipment to be sent
     * @return the constructed packet
     */
    EntityEquipmentPacket[] createEquipmentPacket(EntityIdentifier entity, EntityEquipment equipment);

    /**
     * Creates a packet that will modify one equipment slot of an entity.
     *
     * @param entity the entity to modify
     * @param slot   the slot to change
     * @param item   the new item to put in the slot
     * @return the constructed packet
     */
    EntityEquipmentPacket createEquipmentPacket(EntityIdentifier entity, EquipmentSlot slot, ItemStack item);

    /**
     * Creates a mount packet that will update a vehicle's passengers
     *
     * @param vehicle    the vehicle whose passengers will be set
     * @param passengers the passengers to put onto the vehicle
     * @return the constructed packet
     */
    EntityMountPacket createMountPacket(EntityIdentifier vehicle, EntityIdentifier... passengers);

    /**
     * Creates an array of packets that will spawn an entity and send the data (if applicable) for a client.
     * <br>
     * <b>Do not use this method to spawn objects - use {@link #createObjectSpawnPacket(EntityIdentifier)} instead.</b>
     * This one will just throw an error.
     *
     * @param identifier the entity to be created
     * @return the constructed packets
     */
    EntityPacket[] createEntitySpawnPacket(EntityIdentifier identifier);

    /**
     * Creates an array of packets that will spawn an object and send the data (if applicable) for a client.
     * <br>
     * <b>Do not use this method to spawn entities - use {@link #createEntitySpawnPacket(EntityIdentifier)} instead
     * .</b>
     * This one will just throw an error.
     *
     * @param identifier the object to be created
     * @return the constructed packets
     */
    EntityPacket[] createObjectSpawnPacket(EntityIdentifier identifier);

    /**
     * Creates a packet that will update a given entity's status for a client.
     *
     * @param entity the entity whose status should be updated
     * @param status the new status to be set
     * @return the constructed packet
     */
    EntityStatusPacket createStatusPacket(EntityIdentifier entity, byte status);

    /**
     * Creates a packet that will add a potion to a given entity for a client.
     *
     * @param identifier the entity who should gain a potion effect
     * @param effect     the effect to be added
     * @return the constructed packet
     */
    EntityPotionAddPacket createEffectAddPacket(EntityIdentifier identifier, PotionEffect effect);

    /**
     * Creates a packet that will remove a given potion type for a given entity for a client.
     *
     * @param identifier the entity who should lose the potion effect
     * @param type       the type to be removed
     * @return the constructed packet
     */
    EntityPotionRemovePacket createEffectRemovePacket(EntityIdentifier identifier, PotionEffectType type);

    /**
     * Creates a packet that will move the given entity to a given location and direction for a client.
     *
     * @param identifier the entity who should be moved
     * @param location   the location to move the entity to
     * @param direction  the direction the entity should be facing
     * @param onGround   whether or not the entity is on the ground
     * @param type       the type of movement to perform
     * @return the constructed packet
     */
    EntityMovePacket createMovePacket(EntityIdentifier identifier, Vector location, Vector direction, boolean
            onGround, EntityMovePacket.MoveType type);
}
