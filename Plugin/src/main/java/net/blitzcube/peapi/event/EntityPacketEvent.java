package net.blitzcube.peapi.event;

import com.comphenix.protocol.ProtocolManager;
import net.blitzcube.peapi.PacketEntityAPI;
import net.blitzcube.peapi.api.event.IEntityPacketContext;
import net.blitzcube.peapi.api.event.IEntityPacketEvent;
import net.blitzcube.peapi.api.packet.IEntityPacket;
import net.blitzcube.peapi.entity.EntityPacketContext;
import org.bukkit.entity.Player;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class EntityPacketEvent implements IEntityPacketEvent {
    private final IEntityPacket packet;
    private final EntityPacketType packetType;
    private final Player target;
    private final IEntityPacketContext context;
    private boolean cancelled;

    public EntityPacketEvent(ProtocolManager manager, IEntityPacket packet, EntityPacketType packetType, Player
            target) {
        this.packet = packet;
        this.packetType = packetType;
        this.target = target;
        this.context = new EntityPacketContext(manager, PacketEntityAPI.getChainFactory(), target);
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

    @Override
    public IEntityPacketContext context() { return context; }
}
