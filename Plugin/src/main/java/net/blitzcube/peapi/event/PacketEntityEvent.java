package net.blitzcube.peapi.event;

import com.comphenix.protocol.events.PacketContainer;
import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;
import net.blitzcube.peapi.api.event.IPacketEntityEvent;
import org.bukkit.entity.Player;

/**
 * Created by iso2013 on 2/24/2018.
 */
public class PacketEntityEvent implements IPacketEntityEvent {
    final PacketContainer packet;
    private final IEntityIdentifier identifier;
    private final Player player;
    private boolean cancelled;

    PacketEntityEvent(IEntityIdentifier identifier, Player player, PacketContainer packet) {
        this.identifier = identifier;
        this.player = player;
        this.packet = packet;
        this.cancelled = false;
    }

    @Override
    public IEntityIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
