package net.blitzcube.peapi.api.event;

import net.blitzcube.peapi.api.packet.IEntityPacket;
import org.bukkit.entity.Player;

/**
 * @author iso2013
 * @version 0.1
 * @since 2018-04-21
 */
public interface IEntityPacketEvent {
    /**
     * Gets whether or not this event has been cancelled.
     *
     * @return the cancellation state
     */
    boolean isCancelled();

    /**
     * Sets whether or not this event has been cancelled. If the event is cancelled, it will still be sent to other
     * plugins, but not the client - unless another plugin un-cancels it.
     *
     * @param cancelled the cancellation state
     */
    void setCancelled(boolean cancelled);

    /**
     * Gets the player that this event is firing for
     *
     * @return the player involved in this event
     */
    Player getPlayer();

    /**
     * Gets the packet that this event is firing for.
     *
     * @return the packet
     */
    IEntityPacket getPacket();

    /**
     * Gets the packet type that this event is firing for.
     *
     * @return the packet type
     */
    EntityPacketType getPacketType();

    /**
     * Gets the packet context of this event. Use this to queue packets to be sent afterwards.
     *
     * @return the packet context
     */
    IEntityPacketContext context();

    /**
     * The types of packets that these events can fire for.
     */
    enum EntityPacketType {
        ANIMATION,
        CLICK,
        DATA,
        DESTROY,
        EQUIPMENT,
        ENTITY_SPAWN,
        MOUNT,
        OBJECT_SPAWN,
        STATUS,
        ADD_EFFECT,
        REMOVE_EFFECT,
        MOVE
    }
}
