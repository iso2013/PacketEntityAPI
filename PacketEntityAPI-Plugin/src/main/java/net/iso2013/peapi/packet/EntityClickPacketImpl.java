package net.iso2013.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import net.iso2013.peapi.api.entity.EntityIdentifier;
import net.iso2013.peapi.api.packet.EntityClickPacket;
import net.iso2013.peapi.entity.EntityIdentifierImpl;
import org.bukkit.entity.Player;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class EntityClickPacketImpl extends EntityPacketImpl implements EntityClickPacket {
    private static final PacketType TYPE = PacketType.Play.Client.USE_ENTITY;
    private ClickType type;

    public EntityClickPacketImpl(EntityIdentifier identifier) {
        super(identifier, new PacketContainer(TYPE), true);
    }

    private EntityClickPacketImpl(EntityIdentifier identifier, PacketContainer rawPacket, ClickType type) {
        super(identifier, rawPacket, false);
        this.type = type;
    }

    static EntityPacketImpl unwrap(int entityID, PacketContainer c, Player p) {
        return new EntityClickPacketImpl(
                EntityIdentifierImpl.produce(entityID, p),
                c,
                ClickType.getByProtocolLib(c.getEntityUseActions().read(0))
        );
    }

    @Override
    public ClickType getClickType() {
        return type;
    }

    @Override
    public void setClickType(ClickType type) {
        this.type = type;
        super.rawPacket.getEntityUseActions().write(0, type.getProtocolLibEquivalent());
    }

    @Override
    public PacketContainer getRawPacket() {
        assert type != null;
        return super.getRawPacket();
    }

    @Override
    public EntityPacketImpl clone() {
        EntityClickPacketImpl p = new EntityClickPacketImpl(getIdentifier());
        p.setClickType(type);
        return p;
    }
}
