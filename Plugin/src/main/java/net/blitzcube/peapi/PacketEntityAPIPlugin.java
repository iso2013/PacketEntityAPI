package net.blitzcube.peapi;

import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.google.common.base.Optional;
import net.blitzcube.peapi.api.event.IPacketEntityDataEvent;
import net.blitzcube.peapi.api.event.IPacketEntityEvent;
import net.blitzcube.peapi.api.event.IPacketEntitySpawnEvent;
import net.blitzcube.peapi.api.listener.IListener;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

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
        PacketEntityAPI.initialize(this, iPacketEntityAPI -> {
            iPacketEntityAPI.addListener(new IListener() {
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
                        objs = ((IPacketEntityDataEvent) e).getDataWatcher();
                    } else {
                        Bukkit.getLogger().info("SPAWN)@");
                        objs = ((IPacketEntitySpawnEvent) e).getDataWatcher();
                    }
                    //EntityType t = ids.get(e.getIdentifier().getEntityID());
                    //if(t == null) return;
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
            });
        });
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        if (running) return;
        running = true;

        Player p = e.getPlayer();
        Location l = p.getLocation();
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            if (index == EntityType.values().length) {
                running = false;
                return;
            }
            EntityType t = EntityType.values()[index];
            try {
                if (last != null) {
                    last.remove();
                }
                last = l.getWorld().spawnEntity(l, t);
                last.setVelocity(new Vector(0, 3, 0));
                if (last instanceof LivingEntity) {
                    ((LivingEntity) last).setAI(false);
                }
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Spawned " + t.name()));
                ids.put(last.getEntityId(), t);
            } catch (Exception er) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Failed to spawn " +
                        t.name()));
                Bukkit.getLogger().info("Failed to spawn " + t.name());
            }
            index++;
        }, 0L, 2L);
    }

    @Override
    public void onDisable() {

    }
}
