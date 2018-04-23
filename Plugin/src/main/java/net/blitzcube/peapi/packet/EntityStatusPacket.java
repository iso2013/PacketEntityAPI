package net.blitzcube.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;
import net.blitzcube.peapi.api.packet.IEntityPacketStatus;
import net.blitzcube.peapi.entity.modifier.EntityIdentifier;
import org.bukkit.entity.Player;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class EntityStatusPacket extends EntityPacket implements IEntityPacketStatus {
    private static final int TICK_DELAY = 1;
    private static final PacketType TYPE = PacketType.Play.Server.ENTITY_STATUS;
    private byte status;

    private EntityStatusPacket(IEntityIdentifier identifier) {
        super(identifier, new PacketContainer(TYPE));
        this.status = -1;
    }

    private EntityStatusPacket(IEntityIdentifier identifier, PacketContainer rawPacket, byte status) {
        super(identifier, rawPacket);
        this.status = status;
    }

    public static EntityPacket unwrap(int entityID, PacketContainer c, Player p) {
        return new EntityStatusPacket(new EntityIdentifier(entityID, p), c, c.getBytes().read(0));
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
}
