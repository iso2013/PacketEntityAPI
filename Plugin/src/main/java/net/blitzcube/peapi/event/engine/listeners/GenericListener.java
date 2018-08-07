package net.blitzcube.peapi.event.engine.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.injector.GamePhase;
import net.blitzcube.peapi.PacketEntityAPI;
import net.blitzcube.peapi.api.event.IEntityPacketEvent;
import net.blitzcube.peapi.api.packet.*;
import net.blitzcube.peapi.entity.fake.FakeEntity;
import net.blitzcube.peapi.event.EntityPacketEvent;
import net.blitzcube.peapi.event.engine.PacketEventDispatcher;
import net.blitzcube.peapi.packet.EntityClickPacket;
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
    private final ProtocolManager manager;
    private boolean sendForFake;
    private boolean collidable;
    private static final Map<PacketType, IEntityPacketEvent.EntityPacketType> TYPES = new HashMap<>();

    static {
        TYPES.put(PacketType.Play.Server.ENTITY_METADATA, IEntityPacketEvent.EntityPacketType.DATA);
        TYPES.put(PacketType.Play.Server.ENTITY_EQUIPMENT, IEntityPacketEvent.EntityPacketType.EQUIPMENT);
        TYPES.put(PacketType.Play.Server.MOUNT, IEntityPacketEvent.EntityPacketType.MOUNT);
        TYPES.put(PacketType.Play.Server.ENTITY_DESTROY, IEntityPacketEvent.EntityPacketType.DESTROY);
        TYPES.put(PacketType.Play.Server.ENTITY_STATUS, IEntityPacketEvent.EntityPacketType.STATUS);
        TYPES.put(PacketType.Play.Server.ANIMATION, IEntityPacketEvent.EntityPacketType.ANIMATION);
        TYPES.put(PacketType.Play.Server.ENTITY_EFFECT, IEntityPacketEvent.EntityPacketType.ADD_EFFECT);
        TYPES.put(PacketType.Play.Server.REMOVE_ENTITY_EFFECT, IEntityPacketEvent.EntityPacketType.REMOVE_EFFECT);
        TYPES.put(PacketType.Play.Server.REL_ENTITY_MOVE, IEntityPacketEvent.EntityPacketType.MOVE);
        TYPES.put(PacketType.Play.Server.REL_ENTITY_MOVE_LOOK, IEntityPacketEvent.EntityPacketType.MOVE);
        TYPES.put(PacketType.Play.Server.ENTITY_LOOK, IEntityPacketEvent.EntityPacketType.MOVE);
        TYPES.put(PacketType.Play.Server.ENTITY_TELEPORT, IEntityPacketEvent.EntityPacketType.MOVE);
    }

    public GenericListener(PacketEntityAPI parent, PacketEventDispatcher
            dispatcher, ProtocolManager manager) {
        this.parent = parent;
        this.dispatcher = dispatcher;
        this.manager = manager;
        this.sendForFake = false;
        this.collidable = false;
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
        IEntityPacket w = EntityPacket.unwrapFromType(entityID, eT, c, target);
        if (w == null) return;
        IEntityPacketEvent e = new EntityPacketEvent(manager, w, eT, target);
        dispatcher.dispatch(e, null);
        if (w instanceof IEntityGroupPacket) {
            if (w instanceof IEntityDestroyPacket && ((IEntityDestroyPacket) w).getGroup().size() == 0) {
                packetEvent.setCancelled(true);
                return;
            }
            ((IEntityGroupPacket) e.getPacket()).apply();
        } else if (w instanceof IEntityDataPacket) {
            if (((IEntityDataPacket) w).getMetadata().size() == 0) {
                packetEvent.setCancelled(true);
                return;
            }
        }
        if (e.isCancelled()) {
            packetEvent.setCancelled(true);
        }
        if (w.getRawPacket() != c) {
            packetEvent.setPacket(w.getRawPacket());
        }
    }

    @Override
    public void onPacketReceiving(PacketEvent packetEvent) {
        PacketType type = packetEvent.getPacketType();
        PacketContainer c = packetEvent.getPacket();
        Player target = packetEvent.getPlayer();

        IEntityPacket w = null;
        if (type.equals(PacketType.Play.Client.USE_ENTITY)) {
            int entityID = c.getIntegers().read(0);
            boolean fake = parent.isFakeID(entityID);
            if (fake) {
                if (!sendForFake) return;
            }

            w = EntityPacket.unwrapFromType(entityID, IEntityPacketEvent.EntityPacketType.CLICK, c, target);
        } else if (type.equals(PacketType.Play.Client.ARM_ANIMATION)) {
            if (!collidable) return;
            FakeEntity lookingAt = parent.getFakeEntities().stream()
                    .filter(e -> parent.isVisible(e.getLocation(), target, 1) &&
                            e.checkIntersect(target))
                    .findAny().orElse(null);
            if (lookingAt == null) return;
            packetEvent.setCancelled(true);

            w = new EntityClickPacket(lookingAt.getIdentifier());
            ((EntityClickPacket) w).setClickType(IEntityClickPacket.ClickType.ATTACK);
        }
        IEntityPacketEvent e = new EntityPacketEvent(manager, w, IEntityPacketEvent.EntityPacketType.CLICK, target);
        dispatcher.dispatch(e, null);
        if (e.isCancelled()) {
            packetEvent.setCancelled(true);
        }
    }

    @Override
    public ListeningWhitelist getSendingWhitelist() {
        return ListeningWhitelist.newBuilder()
                .gamePhase(GamePhase.PLAYING).normal().types(TYPES.keySet())
                .mergeOptions(ListenerOptions.SKIP_PLUGIN_VERIFIER).build();
    }

    @Override
    public ListeningWhitelist getReceivingWhitelist() {
        return ListeningWhitelist.newBuilder()
                .gamePhase(GamePhase.PLAYING).normal().types(
                        PacketType.Play.Client.USE_ENTITY,
                        PacketType.Play.Client.ARM_ANIMATION
                ).mergeOptions(ListenerOptions.SKIP_PLUGIN_VERIFIER).build();
    }

    @Override
    public Plugin getPlugin() {
        return null;
    }

    public void setSendForFake(boolean sendForFake) {
        this.sendForFake = sendForFake;
    }

    public void setCollidable(boolean collidable) {
        this.collidable = collidable;
    }
}
