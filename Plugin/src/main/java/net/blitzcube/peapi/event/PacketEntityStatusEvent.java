package net.blitzcube.peapi.event;

import com.comphenix.protocol.events.PacketContainer;
import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.api.event.IPacketEntityStatusEvent;
import net.blitzcube.peapi.entity.EntityIdentifier;
import org.bukkit.entity.Player;

/**
 * Created by iso2013 on 2/24/2018.
 */
public class PacketEntityStatusEvent extends PacketEntityEvent implements IPacketEntityStatusEvent {
    private byte type;

    private PacketEntityStatusEvent(IEntityIdentifier identifier, Player player, byte type, PacketContainer packet) {
        super(identifier, player, packet);
        this.type = type;
    }

    public static IPacketEntityStatusEvent unwrapPacket(int entityID, PacketContainer p, Player target) {
        return new PacketEntityStatusEvent(new EntityIdentifier(entityID, target), target, p.getBytes().read(0), p);
    }

    @Override
    public byte getStatus() {
        return type;
    }

    @Override
    public void setStatus(byte type) {
        this.type = type;
        super.packet.getBytes().write(0, type);
    }
}
