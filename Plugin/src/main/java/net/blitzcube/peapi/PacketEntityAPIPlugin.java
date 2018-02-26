package net.blitzcube.peapi;

import net.blitzcube.peapi.api.event.IPacketEntityEvent;
import net.blitzcube.peapi.api.listener.IListener;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public final class PacketEntityAPIPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        PacketEntityAPI.initialize(this, iPacketEntityAPI -> {
            iPacketEntityAPI.addListener(new IListener() {
                @Override
                public ListenerPriority getPriority() {
                    return ListenerPriority.HIGH;
                }

                @Override
                public void onEvent(IPacketEntityEvent e) {
                }

                @Override
                public EntityType[] getTargets() {
                    return EntityType.values();
                }
            });
        });
    }

    @Override
    public void onDisable() {

    }
}
