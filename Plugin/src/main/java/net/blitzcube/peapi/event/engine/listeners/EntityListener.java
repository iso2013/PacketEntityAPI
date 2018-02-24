package net.blitzcube.peapi.event.engine.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.injector.GamePhase;
import net.blitzcube.peapi.PacketEntityAPI;
import net.blitzcube.peapi.api.entity.EntityIdentifier;
import net.blitzcube.peapi.api.event.spawn.PacketEntitySpawnEvent;
import net.blitzcube.peapi.event.engine.PacketEventDispatcher;
import net.blitzcube.peapi.event.engine.PacketEventEngine;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.UUID;

/**
 * Created by iso2013 on 2/23/2018.
 */
public class EntityListener implements PacketListener {
    private final PacketEntityAPI parent;
    private final PacketEventEngine packetEventEngine;
    private final PacketEventDispatcher dispatcher;
    private boolean sendForFake;

    public EntityListener(PacketEntityAPI parent, PacketEventEngine packetEventEngine, PacketEventDispatcher
            dispatcher) {
        this.parent = parent;
        this.packetEventEngine = packetEventEngine;
        this.dispatcher = dispatcher;
        this.sendForFake = false;
    }

    @Override
    public void onPacketSending(PacketEvent packetEvent) {
        //TODO: Metadata.
        PacketType t = packetEvent.getPacketType();
        PacketContainer p = packetEvent.getPacket();
        Player target = packetEvent.getPlayer();
        if (PacketType.Play.Server.SPAWN_ENTITY_LIVING.equals(t) || PacketType.Play.Server.NAMED_ENTITY_SPAWN.equals
                (t)) {
            int entityID = p.getIntegers().read(0);
            if (!sendForFake && parent.isFakeID(entityID)) return;
            UUID uuid = p.getUUIDs().read(0);
            EntityType type = EntityType.fromId(p.getIntegers().read(1));
            Location location = new Location(
                    target.getWorld(),
                    p.getDoubles().read(0),
                    p.getDoubles().read(1),
                    p.getDoubles().read(2),
                    p.getIntegers().read(0).floatValue() * (360.0F / 256.0F),
                    p.getIntegers().read(1).floatValue() * (360.0F / 256.0F)
            );
            float headPitch = p.getIntegers().read(2).floatValue() * (360.0F / 256.0F);
            Vector velocity = new Vector(
                    p.getBytes().read(0),
                    p.getBytes().read(1),
                    p.getBytes().read(2)
            );
            //TODO: Metadata
            dispatcher.dispatch(
                    new PacketEntitySpawnEvent(
                            new EntityIdentifier(entityID, uuid, target, type),
                            location,
                            headPitch,
                            velocity
                    )
            );
        } else if (PacketType.Play.Server.MOUNT.equals(t)) {

        } else if (PacketType.Play.Server.ENTITY_DESTROY.equals(t)) {

        } else if (PacketType.Play.Server.ENTITY_METADATA.equals(t)) {

        } else if (PacketType.Play.Server.ENTITY_EQUIPMENT.equals(t)) {

        }

    }

    @Override
    public void onPacketReceiving(PacketEvent packetEvent) {

    }

    @Override
    public ListeningWhitelist getSendingWhitelist() {
        return ListeningWhitelist.newBuilder()
                .gamePhase(GamePhase.PLAYING).normal().types(
                        PacketType.Play.Server.SPAWN_ENTITY_LIVING,
                        PacketType.Play.Server.NAMED_ENTITY_SPAWN,
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
