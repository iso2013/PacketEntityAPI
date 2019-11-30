package net.iso2013.peapi.event.engine.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.injector.GamePhase;
import net.iso2013.peapi.PacketEntityAPIPlugin;
import net.iso2013.peapi.api.event.EntityPacketEvent;
import net.iso2013.peapi.api.packet.EntityPacket;
import net.iso2013.peapi.event.EntityPacketEventImpl;
import net.iso2013.peapi.event.engine.PacketEventDispatcher;
import net.iso2013.peapi.packet.EntityPacketImpl;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Created by iso2013 on 2/23/2018.
 */
public class ObjectListener implements PacketListener {
    private final PacketEntityAPIPlugin parent;
    private final PacketEventDispatcher dispatcher;
    private final ProtocolManager manager;
    private boolean sendForFake;

    public ObjectListener(PacketEntityAPIPlugin parent, PacketEventDispatcher
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

        int entityID = 0;
        if (!PacketType.Play.Server.ENTITY_DESTROY.equals(t)) {
            entityID = p.getIntegers().read(0);
            if (!sendForFake && parent.isFakeID(entityID)) return;
        }

        EntityPacket w = EntityPacketImpl.unwrapFromType(entityID, EntityPacketEvent.EntityPacketType.OBJECT_SPAWN,
                p, target);
        if (w == null) return;
        EntityPacketEvent e = new EntityPacketEventImpl(manager, w, EntityPacketEvent.EntityPacketType.OBJECT_SPAWN,
                target);
        dispatcher.dispatch(e, true);
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
                        PacketType.Play.Server.SPAWN_ENTITY,
                        PacketType.Play.Server.SPAWN_ENTITY_PAINTING,
                        PacketType.Play.Server.SPAWN_ENTITY_EXPERIENCE_ORB,
                        PacketType.Play.Server.SPAWN_ENTITY_WEATHER
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
