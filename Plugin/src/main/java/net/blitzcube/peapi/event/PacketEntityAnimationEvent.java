package net.blitzcube.peapi.event;

import com.comphenix.protocol.events.PacketContainer;
import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;
import net.blitzcube.peapi.api.event.IPacketEntityAnimationEvent;
import net.blitzcube.peapi.entity.modifier.EntityIdentifier;
import org.bukkit.entity.Player;

/**
 * Created by iso2013 on 2/24/2018.
 */
public class PacketEntityAnimationEvent extends PacketEntityEvent implements IPacketEntityAnimationEvent {
    private AnimationType type;

    private PacketEntityAnimationEvent(IEntityIdentifier identifier, Player player, AnimationType type,
                                       PacketContainer packet) {
        super(identifier, player, packet);
        this.type = type;
    }

    public static IPacketEntityAnimationEvent unwrapPacket(int entityID, PacketContainer p, Player target) {
        return new PacketEntityAnimationEvent(new EntityIdentifier(entityID, target), target, AnimationType.values()
                [p.getIntegers().read(1)], p);
    }

    @Override
    public AnimationType getAnimation() {
        return type;
    }

    @Override
    public void setAnimation(AnimationType type) {
        this.type = type;
        super.packet.getBytes().write(0, (byte) type.ordinal());
    }
}
