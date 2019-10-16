package net.blitzcube.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.api.packet.IEntityAnimationPacket;
import net.blitzcube.peapi.entity.EntityIdentifier;
import org.bukkit.entity.Player;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class EntityAnimationPacket extends EntityPacket implements IEntityAnimationPacket {
    private static final PacketType TYPE = PacketType.Play.Server.ANIMATION;
    private AnimationType type;

    EntityAnimationPacket(IEntityIdentifier identifier) {
        super(identifier, new PacketContainer(TYPE), true);
    }

    private EntityAnimationPacket(IEntityIdentifier identifier, PacketContainer packet, AnimationType type) {
        super(identifier, packet, false);
        this.type = type;
    }

    static EntityPacket unwrap(int entityID, PacketContainer c, Player p) {
        return new EntityAnimationPacket(
                EntityIdentifier.produce(entityID, p, false),
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
    public EntityPacket clone() {
        EntityAnimationPacket p = new EntityAnimationPacket(getIdentifier());
        p.setAnimation(type);
        return p;
    }
}
