package net.blitzcube.peapi;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChainFactory;
import com.google.common.base.Preconditions;
import net.blitzcube.peapi.api.IPacketEntityAPI;
import net.blitzcube.peapi.api.entity.fake.IFakeEntity;
import net.blitzcube.peapi.api.entity.fake.IFakeEntityFactory;
import net.blitzcube.peapi.api.entity.modifier.IEntityModifierRegistry;
import net.blitzcube.peapi.api.listener.IListener;
import net.blitzcube.peapi.api.packet.IEntityPacketFactory;
import net.blitzcube.peapi.entity.SightDistanceRegistry;
import net.blitzcube.peapi.entity.fake.FakeEntity;
import net.blitzcube.peapi.entity.fake.FakeEntityFactory;
import net.blitzcube.peapi.entity.modifier.EntityModifierRegistry;
import net.blitzcube.peapi.event.engine.PacketEventDispatcher;
import net.blitzcube.peapi.packet.EntityPacketFactory;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by iso2013 on 2/13/2018.
 */
public class PacketEntityAPI implements IPacketEntityAPI {
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

    private static boolean compareVersions(String fullVersion) {
        String[][] input = new String[][]{fullVersion.split("\\."), ProviderStub.FULL_VERSION.split("\\.")};
        int size = Math.max(input[0].length, input[1].length);
        for (int s = 0; s < size; s++) {
            if (input[0].length - 1 < s) return true;
            if (input[1].length - 1 < s) return false;
            if (input[0][s].equals(input[1][s])) continue;
            return Integer.valueOf(input[0][s]) < Integer.valueOf(input[1][s]);
        }
        return false;
    }

    private static JavaPlugin parent;

    private static TaskChainFactory chainFactory;
    /*
     * Begin actual API implementation:
     */
    private final EntityModifierRegistry modifierRegistry;
    private FakeEntityFactory fakeEntityFactory;
    private EntityPacketFactory packetFactory;
    private PacketEventDispatcher dispatcher;
    private PacketEntityAPI() {
        this.modifierRegistry = new EntityModifierRegistry();
        this.fakeEntityFactory = new FakeEntityFactory(this);
        this.packetFactory = new EntityPacketFactory();
        this.dispatcher = new PacketEventDispatcher(this);

        chainFactory = BukkitTaskChainFactory.create(parent);
    }

    static void initialize(JavaPlugin parent, Consumer<IPacketEntityAPI> onLoad) {
        ServicesManager m = Bukkit.getServicesManager();

        Collection<RegisteredServiceProvider<IPacketEntityAPI.ProviderStub>> r = m.getRegistrations(IPacketEntityAPI
                .ProviderStub.class);
        Optional<RegisteredServiceProvider<IPacketEntityAPI.ProviderStub>> top = r.stream()
                .filter(p -> ProviderStub.MAJOR_VERSION.equals(p.getProvider().getMajorVersion())).findFirst();
        if (top.isPresent()) {
            if (compareVersions(top.get().getProvider().getFullVersion())) {
                m.unregister(IPacketEntityAPI.ProviderStub.class, top.get());
            } else return;
        }
        m.register(IPacketEntityAPI.ProviderStub.class, new PacketEntityAPI.ProviderStub(), parent, ServicePriority
                .Normal);

        if (PacketEntityAPI.parent == null || parent.getName().equals("PacketEntityAPI")) {
            PacketEntityAPI.parent = parent;
        }

        if (onLoad != null)
            Bukkit.getScheduler().runTask(parent, () -> onLoad.accept(getLatestCompatibleVersion()));
    }

    public static TaskChainFactory getChainFactory() {
        return chainFactory;
    }

    private static IPacketEntityAPI getLatestCompatibleVersion() {
        RegisteredServiceProvider<IPacketEntityAPI.ProviderStub> match = Bukkit.getServicesManager()
                .getRegistrations(IPacketEntityAPI.ProviderStub.class).stream()
                .filter(p -> ProviderStub.MAJOR_VERSION.equals("0") &&
                        ProviderStub.FULL_VERSION.equals(p.getProvider().getFullVersion()) ||
                        ProviderStub.MAJOR_VERSION.equals(p.getProvider().getMajorVersion())).findFirst()
                .orElse(null);
        if (match == null) {
            throw new IllegalStateException("No compatible API version found! List of current versions:");
        }
        return match.getProvider().getInstance();
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
    public IFakeEntityFactory getEntityFactory() { return fakeEntityFactory; }

    @Override
    public IEntityPacketFactory getPacketFactory() { return packetFactory; }

    public Collection<FakeEntity> getFakeEntities() {
        return fakeEntityFactory.getFakeEntities();
    }

    private static class ProviderStub implements IPacketEntityAPI.ProviderStub {
        static final String FULL_VERSION = "${project.version}";
        static final String MAJOR_VERSION = FULL_VERSION.substring(0, FULL_VERSION.indexOf("."));
        private static PacketEntityAPI inst;

        @Override
        public String getMajorVersion() {
            return MAJOR_VERSION;
        }

        @Override
        public String getFullVersion() {
            return FULL_VERSION;
        }

        @Override
        public IPacketEntityAPI getInstance() {
            return (inst == null ? (inst = new PacketEntityAPI()) : inst);
        }
    }
}
