package net.iso2013.peapi.api.packet;

import com.comphenix.protocol.events.PacketContainer;
import net.iso2013.peapi.api.entity.EntityIdentifier;

/**
 * @author iso2013
 * @version 0.1
 * @since 2018-04-21
 */
public interface EntityPacket {
    /**
     * Get the identifier that this packet is acting on. This will be null for entity destroy packets
     *
     * @return the identifier of the entity being changed by this packet
     */
    EntityIdentifier getIdentifier();

    /**
     * Set the identifier that this packet is acting on. Null values will be ignored, and the underlying packet will
     * not change.
     *
     * @param identifier the identifier of the entity being changed by this packet.
     */
    void setIdentifier(EntityIdentifier identifier);

    /**
     * Gets the raw ProtocolLib packet container that is used by the engine to send this packet to the client.
     *
     * @return the raw packet with modifications
     */
    PacketContainer getRawPacket();

    /**
     * Creates a copy of this packet
     *
     * @return The new copy of this packet
     */
    EntityPacket clone();
}
