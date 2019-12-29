package net.iso2013.peapi.event.engine;

import com.comphenix.protocol.ProtocolManager;
import net.iso2013.peapi.PacketEntityAPIPlugin;
import net.iso2013.peapi.api.event.EntityPacketEvent;
import net.iso2013.peapi.api.listener.Listener;
import net.iso2013.peapi.packet.EntitySpawnPacketImpl;
import net.iso2013.peapi.util.EntityTypeUtil;
import net.iso2013.peapi.util.SortedList;
import org.bukkit.entity.EntityType;

import java.util.*;

/**
 * Created by iso2013 on 2/23/2018.
 */
public class PacketEventDispatcher {
    private static final Comparator<Listener> LISTENER_COMPARATOR = (o1, o2) ->
            Listener.ListenerPriority.getComparator().compare(o1.getPriority(), o2.getPriority());

    private final SortedList<Listener> allListeners;
    private final Set<Listener> objectListeners;
    private final Set<Listener> entityListeners;
    private final Map<EntityType, SortedList<Listener>> listenerLookup;
    private final PacketEventEngine engine;
    private int sendingForFake = 0, requiresCollidable = 0, targetingObjects = 0, targetingEntities = 0;

    public PacketEventDispatcher(PacketEntityAPIPlugin parent, ProtocolManager manager) {
        this.allListeners = new SortedList<>(LISTENER_COMPARATOR);
        this.objectListeners = new HashSet<>();
        this.entityListeners = new HashSet<>();
        this.listenerLookup = new HashMap<>();
        this.engine = new PacketEventEngine(parent, manager, this);
    }

    public void add(Listener l) {
        this.allListeners.add(l);
        boolean e = false, o = false;
        for (EntityType en : l.getTargets()) {
            listenerLookup.putIfAbsent(en, new SortedList<>(LISTENER_COMPARATOR));
            listenerLookup.get(en).add(l);
            if (o && e) continue;
            if (EntityTypeUtil.isObject(en)) {
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
        if (l.shouldFireForFake()) {
            if (sendingForFake == 0) engine.setSendForFake(true);
            sendingForFake++;
        }
        if (l.requiresCollidable()) {
            if (requiresCollidable == 0) engine.setCollidable(true);
            requiresCollidable++;
        }
    }

    public void dispatch(EntityPacketEvent e, Boolean object) {
        if (e == null || e.getPacket() == null) return;
        if (e.getPacketType().equals(EntityPacketEvent.EntityPacketType.ENTITY_SPAWN)
                && e.getPacket() instanceof EntitySpawnPacketImpl) {
            EntityType t = ((EntitySpawnPacketImpl) e.getPacket()).getEntityType();
            if (this.listenerLookup.get(t) != null)
                this.listenerLookup.get(t).forEach(i -> i.onEvent(e));
        } else {
            this.allListeners.stream().filter(iListener -> object == null
                    || (object && objectListeners.contains(iListener))
                    || (!object && entityListeners.contains(iListener))).forEach(i -> i.onEvent(e));
        }
        e.context().execute();
    }

    public void remove(Listener l) {
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
            if (EntityTypeUtil.isObject(en)) {
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

    public boolean contains(Listener eventListener) {
        return this.allListeners.contains(eventListener);
    }
}
