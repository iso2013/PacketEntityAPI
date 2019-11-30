package net.iso2013.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import net.iso2013.peapi.api.entity.EntityIdentifier;
import net.iso2013.peapi.api.packet.EntityAnimationPacket;
import net.iso2013.peapi.entity.EntityIdentifierImpl;
import org.bukkit.entity.Player;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class EntityAnimationPacketImpl extends EntityPacketImpl implements EntityAnimationPacket {
    private static final PacketType TYPE = PacketType.Play.Server.ANIMATION;
    private AnimationType type;

    EntityAnimationPacketImpl(EntityIdentifier identifier) {
        super(identifier, new PacketContainer(TYPE), true);
    }

    private EntityAnimationPacketImpl(EntityIdentifier identifier, PacketContainer packet, AnimationType type) {
        super(identifier, packet, false);
        this.type = type;
    }

    static EntityPacketImpl unwrap(int entityID, PacketContainer c, Player p) {
        return new EntityAnimationPacketImpl(
                EntityIdentifierImpl.produce(entityID, p),
                c,
                AnimationType.values()[c.getIntegers().read(1)]
        );
    }

    @Override
    public AnimationType getAnimation() {
        return type;
    }

    @Override
    public void setAnimation(AnimationType type) {
        this.type = type;
        super.rawPacket.getBytes().write(0, (byte) type.ordinal());
    }

    @Override
    public PacketContainer getRawPacket() {
        assert type != null;
        return super.getRawPacket();
    }

    @Override
    public EntityPacketImpl clone() {
        EntityAnimationPacketImpl p = new EntityAnimationPacketImpl(getIdentifier());
        p.setAnimation(type);
        return p;
    }
}
