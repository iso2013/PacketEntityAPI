package net.blitzcube.peapi.event.engine;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import net.blitzcube.peapi.PacketEntityAPI;
import net.blitzcube.peapi.event.engine.listeners.EntityListener;
import net.blitzcube.peapi.event.engine.listeners.ObjectListener;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iso2013 on 2/23/2018.
 */
public class PacketEventEngine {
    private final PacketEntityAPI parent;
    private final ProtocolManager manager;
    private ObjectListener object;
    private EntityListener entity;
    private Map<EntityType, Integer> preloading;
    private PacketEventDispatcher dispatcher;

    PacketEventEngine(PacketEntityAPI parent, PacketEventDispatcher dispatcher) {
        this.parent = parent;
        this.manager = ProtocolLibrary.getProtocolManager();
        this.preloading = new HashMap<>();
        this.dispatcher = dispatcher;
    }

    public void setSendForFake(boolean sendForFake) {
        object.setSendForFake(sendForFake);
        entity.setSendForFake(sendForFake);
    }

    public void enableObjects() {
        if (object == null) object = new ObjectListener(this, dispatcher);
        manager.addPacketListener(object);
    }

    public void enableEntities() {
        if (entity == null) entity = new EntityListener(parent, this, dispatcher);
        manager.addPacketListener(entity);
    }

    public void addPreloadTargets(EntityType[] targets) {
        for (EntityType t : targets)
            preloading.merge(t, 1, Integer::sum);
    }

    public void removePreloadTargets(EntityType[] targets) {
        for (EntityType t : targets) {
            int i = preloading.get(t);
            if (i == 1) {
                preloading.remove(t);
            } else {
                preloading.put(t, i - 1);
            }
        }
    }

    public boolean isPreloadTarget(EntityType type) {
        return preloading.containsKey(type);
    }

    public void disableObjects() {
        if (object != null) {
            manager.removePacketListener(object);
        }
    }

    public void disableEntites() {
        if (entity != null) {
            manager.removePacketListener(entity);
        }
    }
}
