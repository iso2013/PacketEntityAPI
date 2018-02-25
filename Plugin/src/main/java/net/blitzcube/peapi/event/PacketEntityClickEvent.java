package net.blitzcube.peapi.event;

import com.comphenix.protocol.events.PacketContainer;
import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.api.event.IPacketEntityClickEvent;
import net.blitzcube.peapi.entity.EntityIdentifier;
import org.bukkit.entity.Player;

/**
 * Created by iso2013 on 2/24/2018.
 */
public class PacketEntityClickEvent extends PacketEntityEvent implements IPacketEntityClickEvent {
    private ClickType type;

    private PacketEntityClickEvent(IEntityIdentifier identifier, Player player, ClickType type, PacketContainer
            packet) {
        super(identifier, player, packet);
        this.type = type;
    }

    public static IPacketEntityClickEvent unwrapPacket(int entityID, PacketContainer p, Player target) {
        return new PacketEntityClickEvent(new EntityIdentifier(entityID, target), target,
                ClickType.getByProtocolLib(p.getEntityUseActions().read(0)), p);
    }

    @Override
    public ClickType getClickType() {
        return type;
    }

    @Override
    public void setClickType(ClickType type) {
        this.type = type;
        super.packet.getEntityUseActions().write(0, type.getProtocolLibEquivalent());
    }
}
