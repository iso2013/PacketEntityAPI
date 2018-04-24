package net.blitzcube.peapi.api.packet;

import com.comphenix.protocol.events.PacketContainer;
import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;

/**
 * Created by iso2013 on 4/21/2018.
 */
public interface IEntityPacket {
    IEntityIdentifier getIdentifier();

    int getDelay();

    PacketContainer getRawPacket();
}
