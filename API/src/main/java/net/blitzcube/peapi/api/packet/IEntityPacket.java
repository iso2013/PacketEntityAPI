package net.blitzcube.peapi.api.packet;

import com.comphenix.protocol.events.PacketContainer;
import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;

/**
 * Created by iso2013 on 4/21/2018.
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
