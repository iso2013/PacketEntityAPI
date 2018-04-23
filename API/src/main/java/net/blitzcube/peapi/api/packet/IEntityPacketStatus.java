package net.blitzcube.peapi.api.packet;

/**
 * Created by iso2013 on 4/21/2018.
 */
public interface IEntityPacketStatus extends IEntityPacket {
    byte getStatus();

    void setStatus(byte value);
}
