package net.blitzcube.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.api.packet.IEntityStatusPacket;
import net.blitzcube.peapi.entity.EntityIdentifier;
import org.bukkit.entity.Player;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class EntityStatusPacket extends EntityPacket implements IEntityStatusPacket {
    private static final PacketType TYPE = PacketType.Play.Server.ENTITY_STATUS;
    private byte status;

    EntityStatusPacket(IEntityIdentifier identifier) {
        super(identifier, new PacketContainer(TYPE), true);
        this.status = -1;
    }

    private EntityStatusPacket(IEntityIdentifier identifier, PacketContainer rawPacket, byte status) {
        super(identifier, rawPacket, false);
        this.status = status;
    }

    public static EntityPacket unwrap(int entityID, PacketContainer c, Player p) {
        return new EntityStatusPacket(EntityIdentifier.produce(entityID, p, false), c, c.getBytes().read(0));
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
    public EntityPacket clone() {
        EntityStatusPacket p = new EntityStatusPacket(getIdentifier());
        p.setStatus(status);
        return p;
    }
}
