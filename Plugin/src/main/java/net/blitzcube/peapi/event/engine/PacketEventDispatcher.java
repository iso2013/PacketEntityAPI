package net.blitzcube.peapi.event.engine;

import com.comphenix.protocol.ProtocolManager;
import net.blitzcube.peapi.PacketEntityAPI;
import net.blitzcube.peapi.api.event.IEntityPacketEvent;
import net.blitzcube.peapi.api.listener.IListener;
import net.blitzcube.peapi.packet.EntitySpawnPacket;
import org.bukkit.entity.EntityType;

import java.util.*;

/**
 * Created by iso2013 on 2/23/2018.
 */
public class PacketEventDispatcher {
    private static final Comparator<IListener> LISTENER_COMPARATOR = (o1, o2) ->
            IListener.ListenerPriority.getComparator().compare(o1.getPriority(), o2.getPriority());

    private final SortedSet<IListener> allListeners;
    private final Set<IListener> objectListeners;
    private final Set<IListener> entityListeners;
    private final Map<EntityType, SortedSet<IListener>> listenerLookup;
    private final PacketEventEngine engine;
    private int sendingForFake = 0, requiresCollidable = 0, targetingObjects = 0, targetingEntities = 0;

    public PacketEventDispatcher(PacketEntityAPI parent, ProtocolManager manager) {
        this.allListeners = new TreeSet<>(LISTENER_COMPARATOR);
        this.objectListeners = new HashSet<>();
        this.entityListeners = new HashSet<>();
        this.listenerLookup = new HashMap<>();
        this.engine = new PacketEventEngine(parent, manager, this);
    }

    public void add(IListener l) {
        this.allListeners.add(l);
        if (l.shouldFireForFake()) {
            if (sendingForFake == 0) engine.setSendForFake(true);
            sendingForFake++;
        }
        if (l.requiresCollidable()) {
            if (requiresCollidable == 0) engine.setCollidable(true);
            requiresCollidable++;
        }
        boolean e = false, o = false;
        for (EntityType en : l.getTargets()) {
            listenerLookup.putIfAbsent(en, new TreeSet<>(LISTENER_COMPARATOR));
            listenerLookup.get(en).add(l);
            if (o && e) continue;
            if (PacketEntityAPI.OBJECTS.containsKey(en)) {
                o = true;
            } else {
                e = true;
            }
        }
        if (o) {
            if (targetingObjects == 0) engine.enableObjects();
            targetingObjects++;
            objectListeners.add(l);
        }
        if (e) {
            if (targetingEntities == 0) engine.enableEntities();
            targetingEntities++;
            entityListeners.add(l);
        }
    }

    public void dispatch(IEntityPacketEvent e, Boolean object) {
        if (e == null || e.getPacket() == null) return;
        if (e.getPacketType().equals(IEntityPacketEvent.EntityPacketType.ENTITY_SPAWN)
                && e.getPacket() instanceof EntitySpawnPacket) {
            EntityType t = ((EntitySpawnPacket) e.getPacket()).getEntityType();
            if (this.listenerLookup.get(t) != null)
                this.listenerLookup.get(t).forEach(i -> i.onEvent(e));
        } else {
            this.allListeners.stream().filter(iListener -> object == null
                    || (object && objectListeners.contains(iListener))
                    || (!object && entityListeners.contains(iListener))).forEach(i -> i.onEvent(e));
        }
        e.context().execute();
    }

    public void remove(IListener l) {
        this.allListeners.remove(l);
        if (l.shouldFireForFake()) {
            sendingForFake--;
            if (sendingForFake == 0) engine.setSendForFake(false);
        }
        if (l.requiresCollidable()) {
            requiresCollidable--;
            if (requiresCollidable == 0) engine.setCollidable(false);
        }
        boolean e = false, o = false;
        for (EntityType en : l.getTargets()) {
            if (PacketEntityAPI.OBJECTS.containsKey(en)) {
                o = true;
            } else {
                e = true;
            }
            if (o && e) break;
            listenerLookup.get(en).remove(l);
            if (listenerLookup.get(en).size() == 0) listenerLookup.remove(en);
        }
        if (o) {
            targetingObjects--;
            if (targetingObjects == 0) engine.disableObjects();
            objectListeners.remove(l);
        }
        if (e) {
            targetingEntities--;
            if (targetingEntities == 0) engine.disableEntities();
            entityListeners.remove(l);
        }
    }

    public boolean contains(IListener eventListener) {
        return this.allListeners.contains(eventListener);
    }
}
