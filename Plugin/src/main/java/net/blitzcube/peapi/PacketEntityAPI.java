package net.blitzcube.peapi;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChainFactory;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.google.common.base.Preconditions;
import net.blitzcube.peapi.api.IPacketEntityAPI;
import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.api.entity.fake.IFakeEntity;
import net.blitzcube.peapi.api.entity.fake.IFakeEntityFactory;
import net.blitzcube.peapi.api.entity.modifier.IEntityModifierRegistry;
import net.blitzcube.peapi.api.entity.modifier.IModifiableEntity;
import net.blitzcube.peapi.api.listener.IListener;
import net.blitzcube.peapi.api.packet.IEntityGroupPacket;
import net.blitzcube.peapi.api.packet.IEntityPacket;
import net.blitzcube.peapi.api.packet.IEntityPacketFactory;
import net.blitzcube.peapi.database.DatabaseLoader;
import net.blitzcube.peapi.entity.EntityIdentifier;
import net.blitzcube.peapi.entity.SightDistanceRegistry;
import net.blitzcube.peapi.entity.fake.FakeEntity;
import net.blitzcube.peapi.entity.fake.FakeEntityFactory;
import net.blitzcube.peapi.entity.modifier.EntityModifierRegistry;
import net.blitzcube.peapi.entity.modifier.ModifiableEntity;
import net.blitzcube.peapi.event.engine.PacketEventDispatcher;
import net.blitzcube.peapi.packet.EntityPacketFactory;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by iso2013 on 2/13/2018.
 */
public class PacketEntityAPI extends JavaPlugin implements IPacketEntityAPI {
    public static final Map<EntityType, Integer> OBJECTS = new EnumMap<>(EntityType.class);

    private static IPacketEntityAPI instance;

    private static TaskChainFactory chainFactory;

    /*
     * Begin actual API implementation:
     */
    private EntityModifierRegistry modifierRegistry;
    private ProtocolManager manager;
    private FakeEntityFactory fakeEntityFactory;
    private EntityPacketFactory packetFactory;
    private PacketEventDispatcher dispatcher;

    public static TaskChainFactory getChainFactory() {
        return chainFactory;
    }

    public static IFakeEntity getFakeEntity(int entityID) {
        return instance.getFakeByID(entityID);
    }

    public static EntityType lookupObject(int read) {
        for (Map.Entry<EntityType, Integer> e : OBJECTS.entrySet()) {
            if (e.getValue() == null) continue;
            if (e.getValue() == read) return e.getKey();
        }
        return null;
    }

    @Override
    public void onEnable() {
        DatabaseLoader loader = new DatabaseLoader(this);
        OBJECTS.putAll(loader.loadObjects());
        this.modifierRegistry = new EntityModifierRegistry(loader.loadModifiers());

        this.manager = ProtocolLibrary.getProtocolManager();
        this.fakeEntityFactory = new FakeEntityFactory(this);
        this.packetFactory = new EntityPacketFactory();
        this.dispatcher = new PacketEventDispatcher(this, manager);

        chainFactory = BukkitTaskChainFactory.create(this);

        if (instance == null) instance = this;
    }

    @Override
    public void addListener(IListener eventListener) {
        Preconditions.checkArgument(!isListenerRegistered(eventListener),
                "An event listener cannot be added multiple times!");
        this.dispatcher.add(eventListener);
    }

    @Override
    public void removeListener(IListener eventListener) {
        if (isListenerRegistered(eventListener)) {
            this.dispatcher.remove(eventListener);
        }
    }

    @Override
    public boolean isListenerRegistered(IListener eventListener) {
        return this.dispatcher.contains(eventListener);
    }

    @Override
    public IModifiableEntity wrap(List<WrappedWatchableObject> list) {
        return new ModifiableEntity.ListBased(list);
    }

    @Override
    public IModifiableEntity wrap(Map<Integer, Object> map) {
        return new ModifiableEntity.MapBased(map);
    }

    @Override
    public IModifiableEntity wrap(WrappedDataWatcher watcher) {
        return new ModifiableEntity.WatcherBased(watcher);
    }

    @Override
    public IEntityIdentifier wrap(Entity e) {
        return new EntityIdentifier(e);
    }

    @Override
    public boolean isFakeID(int entityID) {
        return fakeEntityFactory.isFakeEntity(entityID);
    }

    @Override
    public IEntityModifierRegistry getModifierRegistry() {
        return modifierRegistry;
    }

    @Override
    public IFakeEntity getFakeByID(int entityID) {
        return fakeEntityFactory.getFakeByID(entityID);
    }

    @Override
    public boolean isVisible(Location location, Player target, double err) {
        return SightDistanceRegistry.isVisible(location, target, err, EntityType.UNKNOWN);
    }

    @Override
    public Stream<IEntityIdentifier> getVisible(Player viewer, double err, boolean fake) {
        return SightDistanceRegistry.getNearby(viewer, err, fake ? fakeEntityFactory.getFakeEntities() : null);
    }

    @Override
    public Stream<Player> getViewers(IEntityIdentifier object, double err) {
        return SightDistanceRegistry.getViewers(object, err);
    }

    @Override
    public IFakeEntityFactory getEntityFactory() { return fakeEntityFactory; }

    @Override
    public IEntityPacketFactory getPacketFactory() { return packetFactory; }

    @Override
    public void dispatchPacket(IEntityPacket packet, Player target) {
        dispatchPacket(packet, target, 0);
    }

    @Override
    public void dispatchPacket(IEntityPacket packet, Player target, int delay) {
        if (packet instanceof IEntityGroupPacket) ((IEntityGroupPacket) packet).apply();
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

    public Collection<FakeEntity> getFakeEntities() {
        return fakeEntityFactory.getFakeEntities();
    }
}
