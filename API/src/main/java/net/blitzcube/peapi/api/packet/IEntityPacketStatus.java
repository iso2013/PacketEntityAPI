package net.blitzcube.peapi.api.packet;

/**
 * Created by iso2013 on 4/21/2018.
 */
public interface IEntityPacketStatus extends IEntityPacket {
    /**
     * Gets the status that will be sent
     *
     * @return the status
     */
    byte getStatus();

    /**
     * Set the status that will be sent
     *
     * @param value the new status
     */
    void setStatus(byte value);
}
