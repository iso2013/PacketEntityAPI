package net.blitzcube.peapi.event.engine;

import net.blitzcube.peapi.PacketEntityAPI;
import net.blitzcube.peapi.api.event.PacketEntityEvent;
import net.blitzcube.peapi.api.listener.IListener;
import org.bukkit.entity.EntityType;

import java.util.*;

/**
 * Created by iso2013 on 2/23/2018.
 */
public class PacketEventDispatcher {
    private static final Comparator<IListener> LISTENER_COMPARATOR = (o1, o2) ->
            IListener.ListenerPriority.getComparator().compare(o1.getPriority(), o2.getPriority());

    private final SortedSet<IListener> allListeners;
    private final Map<EntityType, SortedSet<IListener>> listenerLookup;
    private final PacketEventEngine engine;
    private int sendingForFake = 0, targetingObjects = 0, targetingEntities = 0;

    public PacketEventDispatcher(PacketEntityAPI parent) {
        this.allListeners = new TreeSet<>(LISTENER_COMPARATOR);
        this.listenerLookup = new HashMap<>();
        this.engine = new PacketEventEngine(parent, this);
    }

    public void add(IListener l) {
        this.allListeners.add(l);
        if (l.shouldPreload()) {
            engine.addPreloadTargets(l.getTargets());
        }
        if (l.shouldFireForFake()) {
            if (sendingForFake == 0) engine.setSendForFake(true);
            sendingForFake++;
        }
        boolean e = false, o = false;
        for (EntityType en : l.getTargets()) {
            if (PacketEntityAPI.OBJECTS.containsKey(en)) {
                o = true;
            } else {
                e = true;
            }
            if (o && e) break;
            listenerLookup.putIfAbsent(en, new TreeSet<>(LISTENER_COMPARATOR));
            listenerLookup.get(en).add(l);
        }
        if (o) {
            if (targetingObjects == 0) engine.enableObjects();
            targetingObjects++;
        }
        if (e) {
            if (targetingEntities == 0) engine.enableEntities();
            targetingEntities++;
        }
    }

    public void dispatch(PacketEntityEvent e) {
        EntityType t;
        if ((t = e.getIdentifier().getEntityType()) != null) {
            this.listenerLookup.get(t).forEach(iListener -> iListener.onEvent(e));
        } else {
            this.allListeners.forEach(iListener -> iListener.onEvent(e));
        }
    }

    public void remove(IListener l) {
        this.allListeners.remove(l);
        if (l.shouldPreload()) {
            engine.removePreloadTargets(l.getTargets());
        }
        if (l.shouldFireForFake()) {
            sendingForFake--;
            if (sendingForFake == 0) engine.setSendForFake(false);
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
        }
        if (e) {
            targetingEntities--;
            if (targetingEntities == 0) engine.disableEntites();
        }
    }

    public boolean contains(IListener eventListener) {
        return this.allListeners.contains(eventListener);
    }
}
