package net.blitzcube.peapi.event;

import com.comphenix.protocol.events.PacketContainer;
import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.api.event.IPacketEntityDataEvent;
import net.blitzcube.peapi.entity.EntityIdentifier;
import org.bukkit.entity.Player;

/**
 * Created by iso2013 on 2/24/2018.
 */
public class PacketEntityDataEvent extends PacketEntityEvent implements IPacketEntityDataEvent {

    private PacketEntityDataEvent(IEntityIdentifier identifier, Player target, PacketContainer packet) {
        super(identifier, target, packet);
    }

    public static IPacketEntityDataEvent unwrapPacket(int id, PacketContainer p, Player target) {
        //TODO: Metadata
        return new PacketEntityDataEvent(new EntityIdentifier(id, target), target, p);
    }
}
