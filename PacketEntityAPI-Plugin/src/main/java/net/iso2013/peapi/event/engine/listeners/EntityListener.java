package net.iso2013.peapi.event.engine.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.injector.GamePhase;
import net.iso2013.peapi.PacketEntityAPIPlugin;
import net.iso2013.peapi.api.event.EntityPacketEvent;
import net.iso2013.peapi.event.EntityPacketEventImpl;
import net.iso2013.peapi.event.engine.PacketEventDispatcher;
import net.iso2013.peapi.packet.EntityPacketImpl;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Created by iso2013 on 2/23/2018.
 */
public class EntityListener implements PacketListener {
    private final PacketEntityAPIPlugin parent;
    private final PacketEventDispatcher dispatcher;
    private final ProtocolManager manager;
    private boolean sendForFake;

    public EntityListener(PacketEntityAPIPlugin parent, PacketEventDispatcher
            dispatcher, ProtocolManager manager) {
        this.parent = parent;
        this.dispatcher = dispatcher;
        this.manager = manager;
        this.sendForFake = false;
    }

    @Override
    public void onPacketSending(PacketEvent packetEvent) {
        PacketType t = packetEvent.getPacketType();
        PacketContainer p = packetEvent.getPacket();
        Player target = packetEvent.getPlayer();

        int entityID = p.getIntegers().read(0);
        if (!sendForFake && parent.isFakeID(entityID)) return;

        EntityPacketEvent e = null;
        if (PacketType.Play.Server.SPAWN_ENTITY_LIVING.equals(t) || PacketType.Play.Server.NAMED_ENTITY_SPAWN.equals
                (t)) {
            e = new EntityPacketEventImpl(manager,
                    EntityPacketImpl.unwrapFromType(entityID, EntityPacketEvent.EntityPacketType.ENTITY_SPAWN, p,
                            target),
                    EntityPacketEvent.EntityPacketType.ENTITY_SPAWN,
                    target
            );
        }
        if (e == null) return;
        dispatcher.dispatch(e, false);
        if (e.isCancelled()) {
            packetEvent.setCancelled(true);
        }
    }

    @Override
    public void onPacketReceiving(PacketEvent packetEvent) {

    }

    @Override
    public ListeningWhitelist getSendingWhitelist() {
        return ListeningWhitelist.newBuilder()
                .gamePhase(GamePhase.PLAYING).normal().types(
                        PacketType.Play.Server.SPAWN_ENTITY_LIVING,
                        PacketType.Play.Server.NAMED_ENTITY_SPAWN
                ).mergeOptions(ListenerOptions.SKIP_PLUGIN_VERIFIER).build();
    }

    @Override
    public ListeningWhitelist getReceivingWhitelist() {
        return ListeningWhitelist.newBuilder().mergeOptions(ListenerOptions.SKIP_PLUGIN_VERIFIER).build();
    }

    @Override
    public Plugin getPlugin() {
        return null;
    }

    public void setSendForFake(boolean sendForFake) {
        this.sendForFake = sendForFake;
    }
}
