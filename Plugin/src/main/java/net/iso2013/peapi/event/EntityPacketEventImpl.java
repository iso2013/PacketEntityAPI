package net.iso2013.peapi.event;

import com.comphenix.protocol.ProtocolManager;
import net.iso2013.peapi.PacketEntityAPIPlugin;
import net.iso2013.peapi.api.event.EntityPacketContext;
import net.iso2013.peapi.api.event.EntityPacketEvent;
import net.iso2013.peapi.api.packet.EntityPacket;
import org.bukkit.entity.Player;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class EntityPacketEventImpl implements EntityPacketEvent {
    private final EntityPacket packet;
    private final EntityPacketType packetType;
    private final Player target;
    private final EntityPacketContext context;
    private boolean cancelled;

    public EntityPacketEventImpl(ProtocolManager manager, EntityPacket packet, EntityPacketType packetType, Player
            target) {
        this.packet = packet;
        this.packetType = packetType;
        this.target = target;
        this.context = new EntityPacketContextImpl(manager, PacketEntityAPIPlugin.getChainFactory(), target);
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
    public EntityPacket getPacket() {
        return packet;
    }

    @Override
    public EntityPacketType getPacketType() {
        return packetType;
    }

    @Override
    public EntityPacketContext context() { return context; }
}
