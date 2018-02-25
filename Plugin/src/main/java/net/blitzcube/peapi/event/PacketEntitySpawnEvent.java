package net.blitzcube.peapi.event;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.base.Preconditions;
import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.api.event.IPacketEntitySpawnEvent;
import net.blitzcube.peapi.entity.EntityIdentifier;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.UUID;

/**
 * Created by iso2013 on 2/24/2018.
 */
public class PacketEntitySpawnEvent extends PacketEntityEvent implements IPacketEntitySpawnEvent {
    private EntityType type;
    private Location location;
    private Vector velocity;
    private float headPitch;

    private PacketEntitySpawnEvent(IEntityIdentifier identifier, Player player, EntityType type, Location location,
                                   float headPitch, Vector velocity, PacketContainer packet) {
        super(identifier, player, packet);
        this.type = type;
        this.location = location;
        this.headPitch = headPitch;
        this.velocity = velocity;
    }

    public static IPacketEntitySpawnEvent unwrapPacket(int id, PacketContainer p, Player target) {
        PacketType t = p.getType();
        UUID uuid = p.getUUIDs().read(0);
        Location location = new Location(
                target.getWorld(),
                p.getDoubles().read(0),
                p.getDoubles().read(1),
                p.getDoubles().read(2),
                p.getIntegers().read(2).floatValue() * (360.0F / 256.0F),
                p.getIntegers().read(3).floatValue() * (360.0F / 256.0F)
        );
        //TODO: Read metadata.
        IEntityIdentifier identifier = new EntityIdentifier(id, uuid, target);
        if (PacketType.Play.Server.NAMED_ENTITY_SPAWN.equals(t)) {
            return new PacketEntitySpawnEvent(identifier, target, EntityType.PLAYER, location, 0.0f, new Vector(0, 0,
                    0), p);
        } else if (PacketType.Play.Server.SPAWN_ENTITY_LIVING.equals(t)) {
            EntityType type = EntityType.fromId(p.getIntegers().read(1));
            float headPitch = p.getIntegers().read(4).floatValue() * (360.0F / 256.0F);
            Vector velocity = new Vector(
                    p.getBytes().read(0),
                    p.getBytes().read(1),
                    p.getBytes().read(2)
            );
            return new PacketEntitySpawnEvent(identifier, target, type, location, headPitch, velocity, p);
        }
        return null;
    }

    @Override
    public EntityType getEntityType() {
        return this.type;
    }

    @Override
    public void setEntityType(EntityType type) {
        this.type = type;
        super.packet.getIntegers().write(1, (int) type.getTypeId());
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
        super.packet.getDoubles().write(0, location.getX());
        super.packet.getDoubles().write(1, location.getY());
        super.packet.getDoubles().write(2, location.getZ());
        super.packet.getIntegers().write(2, (int) (location.getYaw() * (256.0F / 360.0F)));
        super.packet.getIntegers().write(3, (int) (location.getPitch() * (256.0F / 360.0F)));
    }

    @Override
    public float getHeadPitch() {
        return headPitch;
    }

    @Override
    public void setHeadPitch(float headPitch) {
        this.headPitch = headPitch;
        super.packet.getIntegers().write(4, (int) (headPitch * (256.0F / 360.0F)));
    }

    @Override
    public org.bukkit.util.Vector getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(org.bukkit.util.Vector velocity) {
        Preconditions.checkArgument(type != EntityType.PLAYER,
                "You cannot set a velocity for a player!");
        this.velocity = velocity;
        packet.getBytes().write(0, (byte) velocity.getX());
        packet.getBytes().write(1, (byte) velocity.getY());
        packet.getBytes().write(2, (byte) velocity.getZ());
    }
}
