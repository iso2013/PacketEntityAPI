package net.iso2013.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import net.iso2013.peapi.api.entity.EntityIdentifier;
import net.iso2013.peapi.api.packet.EntityStatusPacket;
import net.iso2013.peapi.entity.EntityIdentifierImpl;
import org.bukkit.entity.Player;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class EntityStatusPacketImpl extends EntityPacketImpl implements EntityStatusPacket {
    private static final PacketType TYPE = PacketType.Play.Server.ENTITY_STATUS;
    private byte status;

    EntityStatusPacketImpl(EntityIdentifier identifier) {
        super(identifier, new PacketContainer(TYPE), true);
        this.status = -1;
    }

    private EntityStatusPacketImpl(EntityIdentifier identifier, PacketContainer rawPacket, byte status) {
        super(identifier, rawPacket, false);
        this.status = status;
    }

    public static EntityPacketImpl unwrap(int entityID, PacketContainer c, Player p) {
        return new EntityStatusPacketImpl(EntityIdentifierImpl.produce(entityID, p), c, c.getBytes().read(0));
    }

    @Override
    public byte getStatus() {
        return status;
    }

    @Override
    public void setStatus(byte status) {
        this.status = status;
        super.rawPacket.getBytes().write(0, status);
    }

    @Override
    public PacketContainer getRawPacket() {
        assert status > 0;
        return super.getRawPacket();
    }

    @Override
    public EntityPacketImpl clone() {
        EntityStatusPacketImpl p = new EntityStatusPacketImpl(getIdentifier());
        p.setStatus(status);
        return p;
    }
}
