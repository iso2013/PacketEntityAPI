package net.iso2013.peapi.api.event;

import net.iso2013.peapi.api.packet.EntityPacket;

import java.util.Collection;
import java.util.Set;

/**
 * @author iso2013
 * @version 0.1
 * @since 2018-04-23
 */
public interface EntityPacketContext {
    /**
     * Queue a set of entity packets for sending after the event. Delays will be chained using the default delays for
     * each packet type.
     *
     * @param packets the packets to queue
     * @return This, to allow for chaining
     */
    EntityPacketContext queueDispatch(EntityPacket... packets);

    /**
     * See {@link #queueDispatch(EntityPacket...)} for documentation.
     *
     * @param packets the packets to be dispatched
     * @return This, to allow for chaining
     */
    EntityPacketContext queueDispatch(Set<EntityPacket> packets);

    /**
     * Queue a set of entity packets for sending after the event. Delays for each packet will be taken from the array
     * at the same index; however, if the delays array is not big enough, no delay will be used. If the delay
     * given by the array is negative, no delay will be used.
     *
     * @param packets the packets to queue
     * @param delays  the delays to use for these packets
     * @return This, to allow for chaining
     */
    EntityPacketContext queueDispatch(EntityPacket[] packets, int[] delays);

    /**
     * See {@link #queueDispatch(EntityPacket[], int[])} for documentation.
     *
     * @param packets the packets to queue
     * @param delays  the delays to use for these packets
     * @return This, to allow for chaining
     */
    EntityPacketContext queueDispatch(Collection<EntityPacket> packets, int[] delays);

    /**
     * Queue a set of entity packets for sending after the event. No delays for any packets beside the first.
     *
     * @param packets the packets to queue
     * @param delay   the delay to use before the first packet
     * @return This, to allow for chaining
     */
    EntityPacketContext queueDispatch(EntityPacket[] packets, int delay);

    /**
     * See {@link #queueDispatch(EntityPacket[], int)} for documentation.
     *
     * @param packets the packets to queue
     * @param delay   the delay to use before the first packet
     * @return This, to allow for chaining
     */
    EntityPacketContext queueDispatch(Set<EntityPacket> packets, int delay);

    /**
     * Queue a packet for sending after the event.
     *
     * @param packet the packet to queue
     * @param delay  the delay to use
     * @return This, to allow for chaining
     */
    EntityPacketContext queueDispatch(EntityPacket packet, int delay);

    /**
     * Queue a packet for sending after the event. The delay for the packet will be the default one.
     *
     * @param packet the packet to queue
     * @return This, to allow for chaining
     */
    EntityPacketContext queueDispatch(EntityPacket packet);

    /**
     * Send all of the queued packets.
     * <br>
     * This should not be called by third-party plugins; calling it is handled by the API.
     */
    void execute();
}
