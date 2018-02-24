package net.blitzcube.peapi.event.engine.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListeningWhitelist;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.injector.GamePhase;
import net.blitzcube.peapi.event.engine.PacketEventDispatcher;
import net.blitzcube.peapi.event.engine.PacketEventEngine;
import org.bukkit.plugin.Plugin;

/**
 * Created by iso2013 on 2/23/2018.
 */
public class ObjectListener implements PacketListener {
    private boolean sendForFake;

    public ObjectListener(PacketEventEngine packetEventEngine, PacketEventDispatcher dispatcher) {

    }

    @Override
    public void onPacketSending(PacketEvent packetEvent) {

    }

    @Override
    public void onPacketReceiving(PacketEvent packetEvent) {

    }

    @Override
    public ListeningWhitelist getSendingWhitelist() {
        return ListeningWhitelist.newBuilder()
                .gamePhase(GamePhase.PLAYING).normal().types(
                        PacketType.Play.Server.SPAWN_ENTITY,
                        PacketType.Play.Server.SPAWN_ENTITY_PAINTING,
                        PacketType.Play.Server.SPAWN_ENTITY_EXPERIENCE_ORB,
                        PacketType.Play.Server.SPAWN_ENTITY_WEATHER,
                        PacketType.Play.Server.MOUNT,
                        PacketType.Play.Server.ENTITY_DESTROY,
                        PacketType.Play.Server.ENTITY_METADATA,
                        PacketType.Play.Server.ENTITY_EQUIPMENT
                ).mergeOptions(ListenerOptions.SKIP_PLUGIN_VERIFIER).build();
    }

    @Override
    public ListeningWhitelist getReceivingWhitelist() {
        return ListeningWhitelist.newBuilder()
                .gamePhase(GamePhase.PLAYING).normal().types(
                        PacketType.Play.Client.USE_ENTITY
                ).mergeOptions(ListenerOptions.SKIP_PLUGIN_VERIFIER).build();
    }

    @Override
    public Plugin getPlugin() {
        return null;
    }

    public void setSendForFake(boolean sendForFake) {
        this.sendForFake = sendForFake;
    }
}
