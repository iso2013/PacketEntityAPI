package net.blitzcube.peapi;

import net.blitzcube.peapi.api.event.IPacketEntityEvent;
import net.blitzcube.peapi.api.event.IPacketEntitySpawnEvent;
import net.blitzcube.peapi.api.listener.IListener;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

@SuppressWarnings("unused")
public final class PacketEntityAPIPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        PacketEntityAPI.initialize(this, iPacketEntityAPI -> {
            getLogger().info("Consumer fired!");
            iPacketEntityAPI.addListener(new IListener() {
                @Override
                public ListenerPriority getPriority() {
                    return ListenerPriority.HIGH;
                }

                @Override
                public void onEvent(IPacketEntityEvent e) {
                    if (e instanceof IPacketEntitySpawnEvent) {
                        getLogger().info("PACKET EVENT FIRED! HELLO WORLD FROM PacketEntityAPI! I am a " + (
                                (IPacketEntitySpawnEvent) e).getEntityType().name() + "!");
                        ((IPacketEntitySpawnEvent) e).setEntityType(EntityType.SILVERFISH);
                        ((IPacketEntitySpawnEvent) e).setVelocity(new Vector(0, 10, 0));
                    }
                }

                @Override
                public EntityType[] getTargets() {
                    return EntityType.values();
                }
            });
        });
        getLogger().info("LOADED SUCCESSFULLY!");
    }

    @Override
    public void onDisable() {

    }
}
