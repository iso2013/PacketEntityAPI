package net.iso2013.peapi;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChainFactory;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.google.common.base.Preconditions;
import net.iso2013.peapi.api.PacketEntityAPI;
import net.iso2013.peapi.api.entity.EntityIdentifier;
import net.iso2013.peapi.api.entity.fake.FakeEntityFactory;
import net.iso2013.peapi.api.entity.modifier.EntityModifierRegistry;
import net.iso2013.peapi.api.entity.modifier.ModifiableEntity;
import net.iso2013.peapi.api.listener.Listener;
import net.iso2013.peapi.api.packet.EntityGroupPacket;
import net.iso2013.peapi.api.packet.EntityPacket;
import net.iso2013.peapi.api.packet.EntityPacketFactory;
import net.iso2013.peapi.database.DatabaseLoader;
import net.iso2013.peapi.entity.EntityIdentifierImpl;
import net.iso2013.peapi.entity.SightDistanceRegistry;
import net.iso2013.peapi.entity.fake.FakeEntityFactoryImpl;
import net.iso2013.peapi.entity.fake.FakeEntityImpl;
import net.iso2013.peapi.entity.hitbox.HitboxImpl;
import net.iso2013.peapi.entity.modifier.EntityModifierRegistryImpl;
import net.iso2013.peapi.entity.modifier.ModifiableEntityImpl;
import net.iso2013.peapi.event.engine.PacketEventDispatcher;
import net.iso2013.peapi.packet.EntityPacketFactoryImpl;
import net.iso2013.peapi.util.EntityTypeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by iso2013 on 2/13/2018.
 */
public class PacketEntityAPIPlugin extends JavaPlugin implements PacketEntityAPI {
    private static PacketEntityAPIPlugin instance;

    private static TaskChainFactory chainFactory;

    /*
     * Begin actual API implementation:
     */
    private EntityModifierRegistryImpl modifierRegistry;
    private ProtocolManager manager;
    private FakeEntityFactoryImpl fakeEntityFactory;
    private EntityPacketFactoryImpl packetFactory;
    private PacketEventDispatcher dispatcher;

    public static TaskChainFactory getChainFactory() {
        return chainFactory;
    }

    public static FakeEntityImpl getFakeEntity(int entityID) {
        return instance.getFakeByID(entityID);
    }

    @Override
    public void onEnable() {
        DatabaseLoader loader = new DatabaseLoader(this);
        loader.processHeader();

        EntityTypeUtil.initialize(loader.loadEntities(), loader.loadObjects());
        this.modifierRegistry = new EntityModifierRegistryImpl(loader.loadModifiers());
        HitboxImpl.initHitboxes(loader.loadHitboxes());

        loader.printReport();

        this.manager = ProtocolLibrary.getProtocolManager();
        this.fakeEntityFactory = new FakeEntityFactoryImpl(this);
        this.packetFactory = new EntityPacketFactoryImpl();
        this.dispatcher = new PacketEventDispatcher(this, manager);

        chainFactory = BukkitTaskChainFactory.create(this);

        if (instance == null) instance = this;
        saveDefaultConfig();
    }

    @Override
    public void addListener(Listener eventListener) {
        Preconditions.checkArgument(!isListenerRegistered(eventListener),
                "An event listener cannot be added multiple times!");
        this.dispatcher.add(eventListener);
    }

    @Override
    public void removeListener(Listener eventListener) {
        if (isListenerRegistered(eventListener)) {
            this.dispatcher.remove(eventListener);
        }
    }

    @Override
    public boolean isListenerRegistered(Listener eventListener) {
        return this.dispatcher.contains(eventListener);
    }

    @Override
    public ModifiableEntity wrap(List<WrappedWatchableObject> list) {
        return new ModifiableEntityImpl.ListBased(list);
    }

    @Override
    public ModifiableEntity wrap(Map<Integer, Object> map) {
        return new ModifiableEntityImpl.MapBased(map);
    }

    @Override
    public ModifiableEntity wrap(WrappedDataWatcher watcher) {
        return new ModifiableEntityImpl.WatcherBased(watcher);
    }

    @Override
    public EntityIdentifier wrap(Entity e) {
        return new EntityIdentifierImpl.RealEntityIdentifier(e);
    }

    @Override
    public boolean isFakeID(int entityID) {
        return fakeEntityFactory.isFakeEntity(entityID);
    }

    @Override
    public EntityModifierRegistry getModifierRegistry() {
        return modifierRegistry;
    }

    @Override
    public FakeEntityImpl getFakeByID(int entityID) {
        return fakeEntityFactory.getFakeByID(entityID);
    }

    @Override
    public boolean isVisible(Location location, Player target, double err) {
        return SightDistanceRegistry.isVisible(location, target, err, EntityType.UNKNOWN);
    }

    @Override
    public Stream<EntityIdentifier> getVisible(Player viewer, double err, boolean fake) {
        return SightDistanceRegistry.getNearby(viewer, err, fake ? fakeEntityFactory.getFakeEntities() : null);
    }

    @Override
    public Stream<Player> getViewers(EntityIdentifier object, double err) {
        return SightDistanceRegistry.getViewers(object, err);
    }

    @Override
    public FakeEntityFactory getEntityFactory() { return fakeEntityFactory; }

    @Override
    public EntityPacketFactory getPacketFactory() { return packetFactory; }

    @Override
    public void dispatchPacket(EntityPacket packet, Player target) {
        dispatchPacket(packet, target, 0);
    }

    @Override
    public void dispatchPacket(EntityPacket packet, Player target, int delay) {
        if (packet instanceof EntityGroupPacket) ((EntityGroupPacket) packet).apply();
        PacketContainer c = packet.getRawPacket();
        if (c == null) return;
        if (delay > 0) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
                if (c.getType().isClient()) {
                    safeReceive(target, c);
                } else {
                    safeSend(target, c);
                }
            }, delay);
        } else {
            if (c.getType().isClient()) {
                safeReceive(target, c);
            } else {
                safeSend(target, c);
            }
        }
    }

    private void safeSend(Player target, PacketContainer packet) {
        try {
            manager.sendServerPacket(target, packet, true);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void safeReceive(Player target, PacketContainer packet) {
        try {
            manager.recieveClientPacket(target, packet, true);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Collection<FakeEntityImpl> getFakeEntities() {
        return fakeEntityFactory.getFakeEntities();
    }
}
