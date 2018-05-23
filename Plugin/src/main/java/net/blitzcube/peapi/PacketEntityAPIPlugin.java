package net.blitzcube.peapi;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public final class PacketEntityAPIPlugin extends JavaPlugin {
    @Override
    @SuppressWarnings("unchecked")
    public void onEnable() {
        PacketEntityAPI.initialize(this, null);
    }

    @Override
    public void onDisable() {

    }
}