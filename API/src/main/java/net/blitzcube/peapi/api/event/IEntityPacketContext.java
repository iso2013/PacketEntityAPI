package net.blitzcube.peapi.api.event;

import net.blitzcube.peapi.api.packet.IEntityPacket;

/**
 * Created by iso2013 on 4/23/2018.
 */
public interface IEntityPacketContext {
    IEntityPacketContext queueDispatch(IEntityPacket... packets);

    IEntityPacketContext queueDispatch(IEntityPacket[] packets, int[] delays);

    IEntityPacketContext queueDispatch(IEntityPacket packet, int delay);

    IEntityPacketContext queueDispatch(IEntityPacket packet);
}
