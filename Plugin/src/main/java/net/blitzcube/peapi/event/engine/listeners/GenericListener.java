package net.blitzcube.peapi.event.engine.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.injector.GamePhase;
import net.blitzcube.peapi.PacketEntityAPI;
import net.blitzcube.peapi.api.event.IPacketEntityEvent;
import net.blitzcube.peapi.api.event.IPacketGroupEntityEvent;
import net.blitzcube.peapi.event.*;
import net.blitzcube.peapi.event.engine.PacketEventDispatcher;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Created by iso2013 on 2/24/2018.
 */
public class GenericListener implements PacketListener {
    private final PacketEntityAPI parent;
    private final PacketEventDispatcher dispatcher;
    private boolean sendForFake;

    public GenericListener(PacketEntityAPI parent, PacketEventDispatcher
            dispatcher) {
        this.parent = parent;
        this.dispatcher = dispatcher;
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

        IPacketEntityEvent e = null;
        if (PacketType.Play.Server.ENTITY_METADATA.equals(t)) {
            e = PacketEntityDataEvent.unwrapPacket(entityID, p, target);
        } else if (PacketType.Play.Server.ENTITY_EQUIPMENT.equals(t)) {
            e = PacketEntityEquipmentEvent.unwrapPacket(entityID, p, target);
        } else if (PacketType.Play.Server.MOUNT.equals(t)) {
            e = PacketEntityMountEvent.unwrapPacket(entityID, p, target);
        } else if (PacketType.Play.Server.ENTITY_DESTROY.equals(t)) {
            e = PacketEntityDestroyEvent.unwrapPacket(p, target);
        } else if (PacketType.Play.Server.ENTITY_STATUS.equals(t)) {
            e = PacketEntityStatusEvent.unwrapPacket(entityID, p, target);
        } else if (PacketType.Play.Server.ANIMATION.equals(t)) {
            e = PacketEntityAnimationEvent.unwrapPacket(entityID, p, target);
        }
        if (e == null) return;
        dispatcher.dispatch(e, null);
        if (e instanceof IPacketGroupEntityEvent) {
            if (((IPacketGroupEntityEvent) e).getGroup().size() == 0 || e.isCancelled()) {
                packetEvent.setCancelled(true);
                return;
            }
            ((IPacketGroupEntityEvent) e).apply();
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

        IPacketEntityEvent e = PacketEntityClickEvent.unwrapPacket(entityID, p, target);
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
