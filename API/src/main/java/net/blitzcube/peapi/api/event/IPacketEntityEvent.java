package net.blitzcube.peapi.api.event;

import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;
import org.bukkit.entity.Player;

/**
 * Created by iso2013 on 2/23/2018.
 */
public interface IPacketEntityEvent {
    IEntityIdentifier getIdentifier();

    boolean isCancelled();

    void setCancelled(boolean cancelled);

    Player getPlayer();
}
