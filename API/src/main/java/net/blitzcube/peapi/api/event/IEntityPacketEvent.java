package net.blitzcube.peapi.api.event;

import net.blitzcube.peapi.api.packet.IPacketEntity;
import org.bukkit.entity.Player;

/**
 * Created by iso2013 on 4/21/2018.
 */
public interface IEntityPacketEvent {
    boolean isCancelled();

    void setCancelled(boolean cancelled);

    Player getPlayer();

    IPacketEntity getPacket();

    EntityPacketType getPacketType();

    enum EntityPacketType {
        ANIMATION,
        CLICK,
        DATA,
        DESTROY,
        EQUIPMENT,
        ENTITY_SPAWN,
        MOUNT,
        OBJECT_SPAWN,
        STATUS,
        UNKNOWN
    }
}
