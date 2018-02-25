package net.blitzcube.peapi.api.event;

/**
 * Created by iso2013 on 2/24/2018.
 */
public interface IPacketEntityStatusEvent extends IPacketEntityEvent {
    byte getStatus();

    void setStatus(byte value);
}
