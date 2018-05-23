package net.blitzcube.peapi;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public final class PacketEntityAPIPlugin extends JavaPlugin implements Listener {
    @Override
    @SuppressWarnings("unchecked")
    public void onEnable() {
        PacketEntityAPI.initialize(this, null);
    }

    @Override
    public void onDisable() {

    }
}