package net.blitzcube.peapi.api.event;

import net.blitzcube.peapi.api.packet.IEntityPacket;

/**
 * @author iso2013
 * @version 0.1
 * @since 2018-04-23
 */
public interface IEntityPacketContext {
    /**
     * Queue a set of entity packets for sending after the event. Delays will be chained using the default delays for
     * each packet type.
     *
     * @param packets the packets to queue
     * @return this to allow for chaining
     */
    IEntityPacketContext queueDispatch(IEntityPacket... packets);

    /**
     * Queue a set of entity packets for sending after the event. Delays for each packet will be taken from the array
     * at the same index; however, if the delays array is not big enough, the default delay will be used. If the delay
     * given by the array is negative, the default delay will also be used.
     * @param packets the packets to queue
     * @param delays the delays to use for these packets
     * @return this to allow for chaining
     */
    IEntityPacketContext queueDispatch(IEntityPacket[] packets, int[] delays);

    /**
     * Queue a set of entity packets for sending after the event. No delays for any packets beside the first.
     * @param packets the packets to queue
     * @param delay the delay to use before the first packet
     * @return this to allow for chaining
     */
    IEntityPacketContext queueDispatch(IEntityPacket[] packets, int delay);

    /**
     * Queue a packet for sending after the event. The delay for the packet will be taken from the parameter
     * @param packet the packet to queue
     * @param delay the delay to use
     * @return
     */
    IEntityPacketContext queueDispatch(IEntityPacket packet, int delay);

    /**
     * Queue a packet for sending after the event. The delay for the packet will be the default one.
     * @param packet the packet to queue
     * @return
     */
    IEntityPacketContext queueDispatch(IEntityPacket packet);

    /**
     * Send all of the queued packets.
     * <br>
     * This should not be called by third-party plugins; calling it is handled by the API.
     */
    void execute();
}
