package net.blitzcube.peapi.api.packet;

import com.comphenix.protocol.events.PacketContainer;
import net.blitzcube.peapi.api.entity.IEntityIdentifier;

/**
 * @author iso2013
 * @version 0.1
 * @since 2018-04-21
 */
public interface IEntityPacket {
    /**
     * Get the identifier that this packet is acting on. This will be null for entity destroy packets
     *
     * @return the identifier of the entity being changed by this packet
     */
    IEntityIdentifier getIdentifier();

    /**
     * The minimum duration that must elapse between sending this packet and the original spawn packet - for most,
     * this is 1 tick.
     *
     * @return the number of ticks that should be waited in between sending the spawn packet and sending this packet.
     */
    int getDelay();

    /**
     * Gets the raw ProtocolLib packet container that is used by the engine to send this packet to the client.
     *
     * @return the raw packet with modifications
     */
    PacketContainer getRawPacket();
}
