package net.blitzcube.peapi.event;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;
import net.blitzcube.peapi.api.event.IPacketEntityDataEvent;
import net.blitzcube.peapi.entity.modifier.EntityIdentifier;
import org.bukkit.entity.Player;

import java.util.List;

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

    @Override
    public List<WrappedWatchableObject> getMetadata() {
        return super.packet.getWatchableCollectionModifier().read(0);
    }

    @Override
    public void setMetadata(List<WrappedWatchableObject> data) {
        super.packet.getWatchableCollectionModifier().write(0, data);
    }
}
