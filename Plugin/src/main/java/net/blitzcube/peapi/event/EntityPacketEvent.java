package net.blitzcube.peapi.event;

import net.blitzcube.peapi.api.event.IEntityPacketEvent;
import net.blitzcube.peapi.api.packet.IEntityPacket;
import org.bukkit.entity.Player;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class EntityPacketEvent implements IEntityPacketEvent {
    private final IEntityPacket packet;
    private final EntityPacketType packetType;
    private final Player target;
    private boolean cancelled;

    public EntityPacketEvent(IEntityPacket packet, EntityPacketType packetType, Player target) {
        this.packet = packet;
        this.packetType = packetType;
        this.target = target;
    }


    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public Player getPlayer() {
        return target;
    }

    @Override
    public IEntityPacket getPacket() {
        return packet;
    }

    @Override
    public EntityPacketType getPacketType() {
        return packetType;
    }
}
