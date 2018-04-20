package net.blitzcube.peapi;

import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.google.common.base.Optional;
import net.blitzcube.peapi.api.event.IPacketEntityDataEvent;
import net.blitzcube.peapi.api.event.IPacketEntityEvent;
import net.blitzcube.peapi.api.event.IPacketEntitySpawnEvent;
import net.blitzcube.peapi.api.listener.IListener;
import net.blitzcube.peapi.entity.loader.EntityModifierLoader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public final class PacketEntityAPIPlugin extends JavaPlugin implements Listener {
    private boolean running = false;
    private int index = 0;
    private Entity last;
    private Map<Integer, EntityType> ids;

    @Override
    public void onEnable() {
        ids = new HashMap<>();

        EntityModifierLoader.getModifiers(this.getResource("EntityDataJSON.json"));

        PacketEntityAPI.initialize(this, iPacketEntityAPI -> iPacketEntityAPI.addListener(new IListener() {
            @Override
            public ListenerPriority getPriority() {
                return ListenerPriority.HIGH;
            }

            @Override
            public void onEvent(IPacketEntityEvent e) {
                if (!((e instanceof IPacketEntityDataEvent) || (e instanceof IPacketEntitySpawnEvent))) return;
                List<WrappedWatchableObject> objs;
                if (e instanceof IPacketEntityDataEvent) {
                    Bukkit.getLogger().info("DATA)@");
                    objs = ((IPacketEntityDataEvent) e).getMetadata();
                } else {
                    Bukkit.getLogger().info("SPAWN)@");
                    objs = ((IPacketEntitySpawnEvent) e).getMetadata();
                    ids.put(e.getIdentifier().getEntityID(), ((IPacketEntitySpawnEvent) e).getEntityType());
                }

                EntityType t = ids.get(e.getIdentifier().getEntityID());
                if (t == null) return;
                if (objs == null) objs = new ArrayList<>();

                //                    Bukkit.getLogger().info("------------");
                Bukkit.getLogger().info("-------MANUAL------");
                //Bukkit.getLogger().info("TYPE: " + t);
                for (WrappedWatchableObject wwo : objs) {
                    if (e instanceof IPacketEntitySpawnEvent) {
                        Bukkit.getLogger().info("TYPE: " + ((IPacketEntitySpawnEvent) e).getEntityType().name());
                    }
                    Bukkit.getLogger().info(wwo.getIndex() + ": " + wwo.getValue().getClass().getName());
                    if (wwo.getValue() instanceof Optional) {
                        Optional o = (Optional) wwo.getValue();
                        if (o.isPresent()) {
                            Bukkit.getLogger().info(o.get().getClass().getName());
                        } else {
                            Bukkit.getLogger().info("ABSENT");
                        }
                    }
                }
                Bukkit.getLogger().info("------------");
            }

            @Override
            public EntityType[] getTargets() {
                return EntityType.values();
            }
        }));
    }

    @Override
    public void onDisable() {

    }
}
