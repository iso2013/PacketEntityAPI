package net.blitzcube.peapi.event.engine;

import com.comphenix.protocol.ProtocolManager;
import net.blitzcube.peapi.PacketEntityAPI;
import net.blitzcube.peapi.event.engine.listeners.EntityListener;
import net.blitzcube.peapi.event.engine.listeners.GenericListener;
import net.blitzcube.peapi.event.engine.listeners.ObjectListener;

/**
 * Created by iso2013 on 2/23/2018.
 */
class PacketEventEngine {
    private final PacketEntityAPI parent;
    private final ProtocolManager manager;
    private final PacketEventDispatcher dispatcher;
    private ObjectListener object;
    private GenericListener generic;
    private EntityListener entity;

    PacketEventEngine(PacketEntityAPI parent, ProtocolManager manager, PacketEventDispatcher dispatcher) {
        this.parent = parent;
        this.manager = manager;
        this.dispatcher = dispatcher;
    }

    void setSendForFake(boolean sendForFake) {
        if (entity != null) entity.setSendForFake(sendForFake);
        if (generic != null) generic.setSendForFake(sendForFake);
        if (object != null) object.setSendForFake(sendForFake);
    }

    void setCollidable(boolean collidable) {
        generic.setCollidable(collidable);
    }

    void enableObjects() {
        if (object == null && entity == null) {
            generic = new GenericListener(parent, dispatcher, manager);
            manager.addPacketListener(generic);
        }
        if (object == null) object = new ObjectListener(parent, dispatcher, manager);
        manager.addPacketListener(object);
    }

    void enableEntities() {
        if (object == null && entity == null) {
            generic = new GenericListener(parent, dispatcher, manager);
            manager.addPacketListener(generic);
        }
        if (entity == null) entity = new EntityListener(parent, dispatcher, manager);
        manager.addPacketListener(entity);
    }

    void disableObjects() {
        if (object != null) {
            manager.removePacketListener(object);
        }
        if (object == null && entity == null) {
            manager.removePacketListener(generic);
            generic = null;
        }
    }

    void disableEntities() {
        if (entity != null) {
            manager.removePacketListener(entity);
        }
        if (object == null && entity == null) {
            manager.removePacketListener(generic);
            generic = null;
        }
    }
}
