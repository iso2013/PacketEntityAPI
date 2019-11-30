package net.iso2013.peapi.event.engine.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.injector.GamePhase;
import net.iso2013.peapi.PacketEntityAPIPlugin;
import net.iso2013.peapi.api.event.EntityPacketEvent;
import net.iso2013.peapi.api.packet.*;
import net.iso2013.peapi.entity.fake.FakeEntityImpl;
import net.iso2013.peapi.event.EntityPacketEventImpl;
import net.iso2013.peapi.event.engine.PacketEventDispatcher;
import net.iso2013.peapi.packet.EntityClickPacketImpl;
import net.iso2013.peapi.packet.EntityPacketImpl;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iso2013 on 2/24/2018.
 */
public class GenericListener implements PacketListener {
    private static final Map<PacketType, EntityPacketEvent.EntityPacketType> TYPES = new HashMap<>();

    static {
        TYPES.put(PacketType.Play.Server.ENTITY_METADATA, EntityPacketEvent.EntityPacketType.DATA);
        TYPES.put(PacketType.Play.Server.ENTITY_EQUIPMENT, EntityPacketEvent.EntityPacketType.EQUIPMENT);
        TYPES.put(PacketType.Play.Server.MOUNT, EntityPacketEvent.EntityPacketType.MOUNT);
        TYPES.put(PacketType.Play.Server.ENTITY_DESTROY, EntityPacketEvent.EntityPacketType.DESTROY);
        TYPES.put(PacketType.Play.Server.ENTITY_STATUS, EntityPacketEvent.EntityPacketType.STATUS);
        TYPES.put(PacketType.Play.Server.ANIMATION, EntityPacketEvent.EntityPacketType.ANIMATION);
        TYPES.put(PacketType.Play.Server.ENTITY_EFFECT, EntityPacketEvent.EntityPacketType.ADD_EFFECT);
        TYPES.put(PacketType.Play.Server.REMOVE_ENTITY_EFFECT, EntityPacketEvent.EntityPacketType.REMOVE_EFFECT);
        TYPES.put(PacketType.Play.Server.REL_ENTITY_MOVE, EntityPacketEvent.EntityPacketType.MOVE);
        TYPES.put(PacketType.Play.Server.REL_ENTITY_MOVE_LOOK, EntityPacketEvent.EntityPacketType.MOVE);
        TYPES.put(PacketType.Play.Server.ENTITY_LOOK, EntityPacketEvent.EntityPacketType.MOVE);
        TYPES.put(PacketType.Play.Server.ENTITY_TELEPORT, EntityPacketEvent.EntityPacketType.MOVE);
    }

    private final PacketEntityAPIPlugin parent;
    private final PacketEventDispatcher dispatcher;
    private final ProtocolManager manager;
    private boolean sendForFake;
    private boolean collidable;

    public GenericListener(PacketEntityAPIPlugin parent, PacketEventDispatcher
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

        EntityPacketEvent.EntityPacketType eT = TYPES.get(type);
        EntityPacket w = EntityPacketImpl.unwrapFromType(entityID, eT, c, target);
        if (w == null) return;
        EntityPacketEvent e = new EntityPacketEventImpl(manager, w, eT, target);
        dispatcher.dispatch(e, null);
        if (w instanceof EntityGroupPacket) {
            if (w instanceof EntityDestroyPacket && ((EntityDestroyPacket) w).getGroup().size() == 0) {
                packetEvent.setCancelled(true);
                return;
            }
            ((EntityGroupPacket) e.getPacket()).apply();
        } else if (w instanceof EntityDataPacket) {
            if (((EntityDataPacket) w).getMetadata().size() == 0) {
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

        EntityPacket w = null;
        if (type.equals(PacketType.Play.Client.USE_ENTITY)) {
            int entityID = c.getIntegers().read(0);
            boolean fake = parent.isFakeID(entityID);
            if (fake) {
                if (!sendForFake) return;
            }

            w = EntityPacketImpl.unwrapFromType(entityID, EntityPacketEvent.EntityPacketType.CLICK, c, target);
        } else if (type.equals(PacketType.Play.Client.ARM_ANIMATION)) {
            if (!collidable) return;
            FakeEntityImpl lookingAt = parent.getFakeEntities().stream()
                    .filter(e -> parent.isVisible(e.getLocation(), target, 1) &&
                            e.checkIntersect(target))
                    .findAny().orElse(null);
            if (lookingAt == null) return;
            packetEvent.setCancelled(true);

            w = new EntityClickPacketImpl(lookingAt);
            ((EntityClickPacketImpl) w).setClickType(EntityClickPacket.ClickType.ATTACK);
        }
        EntityPacketEvent e = new EntityPacketEventImpl(manager, w, EntityPacketEvent.EntityPacketType.CLICK, target);
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
