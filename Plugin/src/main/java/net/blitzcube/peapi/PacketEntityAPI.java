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
import net.blitzcube.peapi.api.packet.IEntityPacket;
import net.blitzcube.peapi.api.packet.IEntityPacketFactory;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by iso2013 on 2/13/2018.
 */
public class PacketEntityAPI extends JavaPlugin implements IPacketEntityAPI {
    public static final Map<EntityType, Integer> OBJECTS = new HashMap<>();

    static {
        OBJECTS.put(EntityType.BOAT, 1);
        OBJECTS.put(EntityType.DROPPED_ITEM, 2);
        OBJECTS.put(EntityType.AREA_EFFECT_CLOUD, 3);
        OBJECTS.put(EntityType.MINECART, 10);
        OBJECTS.put(EntityType.PRIMED_TNT, 50);
        OBJECTS.put(EntityType.ENDER_CRYSTAL, 51);
        OBJECTS.put(EntityType.TIPPED_ARROW, 60);
        OBJECTS.put(EntityType.SNOWBALL, 61);
        OBJECTS.put(EntityType.EGG, 62);
        OBJECTS.put(EntityType.FIREBALL, 63);
        OBJECTS.put(EntityType.SMALL_FIREBALL, 64);
        OBJECTS.put(EntityType.ENDER_PEARL, 65);
        OBJECTS.put(EntityType.WITHER_SKULL, 66);
        OBJECTS.put(EntityType.SHULKER_BULLET, 67);
        OBJECTS.put(EntityType.LLAMA_SPIT, 68);
        OBJECTS.put(EntityType.FALLING_BLOCK, 70);
        OBJECTS.put(EntityType.ITEM_FRAME, 71);
        OBJECTS.put(EntityType.ENDER_SIGNAL, 72);
        OBJECTS.put(EntityType.SPLASH_POTION, 73);
        OBJECTS.put(EntityType.THROWN_EXP_BOTTLE, 75);
        OBJECTS.put(EntityType.FIREWORK, 76);
        OBJECTS.put(EntityType.LEASH_HITCH, 77);
        OBJECTS.put(EntityType.ARMOR_STAND, 78);
        OBJECTS.put(EntityType.FISHING_HOOK, 90);
        OBJECTS.put(EntityType.SPECTRAL_ARROW, 91);
        OBJECTS.put(EntityType.DRAGON_FIREBALL, 93);
        OBJECTS.put(EntityType.EXPERIENCE_ORB, -1);
        OBJECTS.put(EntityType.PAINTING, -1);
        OBJECTS.put(EntityType.LIGHTNING, -1);
    }

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

    @Override
    public void onEnable() {
        this.modifierRegistry = new EntityModifierRegistry();
        this.manager = ProtocolLibrary.getProtocolManager();
        this.fakeEntityFactory = new FakeEntityFactory(this);
        this.packetFactory = new EntityPacketFactory();
        this.dispatcher = new PacketEventDispatcher(this, manager);

        chainFactory = BukkitTaskChainFactory.create(this);

        if (instance == null) instance = this;

    }

    @Override
    public void onDisable() {

    }

    public static TaskChainFactory getChainFactory() {
        return chainFactory;
    }

    public static IFakeEntity getFakeEntity(int entityID) {
        return instance.getFakeByID(entityID);
    }

    public static boolean isFakeEntity(int entityID) {
        return instance.isFakeID(entityID);
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

    public static EntityType lookupObject(int read) {
        for (Map.Entry<EntityType, Integer> e : OBJECTS.entrySet()) {
            if (e.getValue() == null) continue;
            if (e.getValue() == read) return e.getKey();
        }
        return null;
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
        return SightDistanceRegistry.isVisible(location, target, err);
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
        dispatchPacket(packet, target, packet.getDelay());
    }

    @Override
    public void dispatchPacket(IEntityPacket packet, Player target, int delay) {
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
