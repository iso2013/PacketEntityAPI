package net.blitzcube.peapi.event.engine.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.injector.GamePhase;
import net.blitzcube.peapi.PacketEntityAPI;
import net.blitzcube.peapi.api.event.IEntityPacketEvent;
import net.blitzcube.peapi.api.packet.IPacketEntity;
import net.blitzcube.peapi.api.packet.IPacketGroupEntity;
import net.blitzcube.peapi.event.EntityPacketEvent;
import net.blitzcube.peapi.event.engine.PacketEventDispatcher;
import net.blitzcube.peapi.packet.EntityPacket;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iso2013 on 2/24/2018.
 */
public class GenericListener implements PacketListener {
    private final PacketEntityAPI parent;
    private final PacketEventDispatcher dispatcher;
    private boolean sendForFake;
    private static final Map<PacketType, IEntityPacketEvent.EntityPacketType> TYPES = new HashMap<>();

    static {
        TYPES.put(PacketType.Play.Server.ENTITY_METADATA, IEntityPacketEvent.EntityPacketType.DATA);
        TYPES.put(PacketType.Play.Server.ENTITY_EQUIPMENT, IEntityPacketEvent.EntityPacketType.EQUIPMENT);
        TYPES.put(PacketType.Play.Server.MOUNT, IEntityPacketEvent.EntityPacketType.MOUNT);
        TYPES.put(PacketType.Play.Server.ENTITY_DESTROY, IEntityPacketEvent.EntityPacketType.DESTROY);
        TYPES.put(PacketType.Play.Server.ENTITY_STATUS, IEntityPacketEvent.EntityPacketType.STATUS);
        TYPES.put(PacketType.Play.Server.ANIMATION, IEntityPacketEvent.EntityPacketType.ANIMATION);
    }

    public GenericListener(PacketEntityAPI parent, PacketEventDispatcher
            dispatcher) {
        this.parent = parent;
        this.dispatcher = dispatcher;
        this.sendForFake = false;
    }

    @Override
    public void onPacketSending(PacketEvent packetEvent) {
        PacketType type = packetEvent.getPacketType();
        PacketContainer c = packetEvent.getPacket();
        Player target = packetEvent.getPlayer();

        int entityID = 0;
        if (!PacketType.Play.Server.ENTITY_DESTROY.equals(type)) {
            entityID = c.getIntegers().read(0);
            if (!sendForFake && parent.isFakeID(entityID)) return;
        }

        IEntityPacketEvent.EntityPacketType eT = TYPES.get(type);
        IPacketEntity w = EntityPacket.unwrapFromType(entityID, eT, c, target);
        if (w == null) return;
        IEntityPacketEvent e = new EntityPacketEvent(
                w,
                eT,
                target
        );
        dispatcher.dispatch(e, null);
        if (e.getPacket() instanceof IPacketGroupEntity) {
            if (((IPacketGroupEntity) e.getPacket()).getGroup().size() == 0 || e.isCancelled()) {
                packetEvent.setCancelled(true);
                return;
            }
            ((IPacketGroupEntity) e.getPacket()).apply();
        } else if (e.isCancelled()) {
            packetEvent.setCancelled(true);
        }
    }

    @Override
    public void onPacketReceiving(PacketEvent packetEvent) {
        PacketContainer p = packetEvent.getPacket();
        Player target = packetEvent.getPlayer();

        int entityID = p.getIntegers().read(0);
        if (!sendForFake && parent.isFakeID(entityID)) return;

        IPacketEntity w = EntityPacket.unwrapFromType(entityID, IEntityPacketEvent.EntityPacketType.CLICK, p, target);
        IEntityPacketEvent e = new EntityPacketEvent(w, IEntityPacketEvent.EntityPacketType.CLICK, target);
        dispatcher.dispatch(e, null);
        if (e.isCancelled()) {
            packetEvent.setCancelled(true);
        }
    }

    @Override
    public ListeningWhitelist getSendingWhitelist() {
        return ListeningWhitelist.newBuilder()
                .gamePhase(GamePhase.PLAYING).normal().types(
                        PacketType.Play.Server.MOUNT,
                        PacketType.Play.Server.ENTITY_DESTROY,
                        PacketType.Play.Server.ENTITY_METADATA,
                        PacketType.Play.Server.ENTITY_EQUIPMENT,
                        PacketType.Play.Server.ENTITY_STATUS,
                        PacketType.Play.Server.ANIMATION
                ).mergeOptions(ListenerOptions.SKIP_PLUGIN_VERIFIER).build();
    }

    @Override
    public ListeningWhitelist getReceivingWhitelist() {
        return ListeningWhitelist.newBuilder()
                .gamePhase(GamePhase.PLAYING).normal().types(
                        PacketType.Play.Client.USE_ENTITY
                ).mergeOptions(ListenerOptions.SKIP_PLUGIN_VERIFIER).build();
    }

    @Override
    public Plugin getPlugin() {
        return null;
    }

    public void setSendForFake(boolean sendForFake) {
        this.sendForFake = sendForFake;
    }
}
