package net.blitzcube.peapi.event;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.base.Preconditions;
import net.blitzcube.peapi.PacketEntityAPI;
import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.api.event.IPacketObjectSpawnEvent;
import net.blitzcube.peapi.entity.EntityIdentifier;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.UUID;

/**
 * Created by iso2013 on 2/24/2018.
 */
public class PacketObjectSpawnEvent extends PacketEntityEvent implements IPacketObjectSpawnEvent {
    private EntityType type;
    private Location location;
    private Vector velocity;
    private short orbCount;
    private String title;
    private byte direction;
    private int data;

    private PacketObjectSpawnEvent(IEntityIdentifier identifier, Player target, EntityType type, Location location,
                                   Vector velocity, int data, PacketContainer packet) {
        super(identifier, target, packet);
        this.type = type;
        this.location = location;
        this.velocity = velocity;
        this.data = data;
    }

    private PacketObjectSpawnEvent(IEntityIdentifier identifier, Player target, EntityType type, Location location,
                                   String title, byte direction, PacketContainer packet) {
        super(identifier, target, packet);
        this.type = type;
        this.location = location;
        this.title = title;
        this.direction = direction;
    }

    private PacketObjectSpawnEvent(IEntityIdentifier identifier, Player target, EntityType type, Location location,
                                   short orbCount, PacketContainer packet) {
        super(identifier, target, packet);
        this.type = type;
        this.location = location;
        this.orbCount = orbCount;
    }

    private PacketObjectSpawnEvent(IEntityIdentifier identifier, Player target, EntityType type, Location location,
                                   PacketContainer packet) {
        super(identifier, target, packet);
        this.type = type;
        this.location = location;
    }

    public static IPacketObjectSpawnEvent unwrapPacket(int id, PacketContainer p, Player target) {
        EntityType t = packetTypeToEntity(p.getType());
        float pitch = 0.0F, yaw = 0.0F;
        Vector velocity = new Vector();
        int data = 0;
        UUID uuid = null;
        Location location;
        if (t == null) return null;
        switch (t) {
            case UNKNOWN:
                yaw = p.getIntegers().read(1).floatValue() * (360.0F / 256.0F);
                pitch = p.getIntegers().read(2).floatValue() * (360.0F / 256.0F);
                t = PacketEntityAPI.lookupObject(p.getBytes().read(0));
                velocity = new Vector(
                        p.getBytes().read(1),
                        p.getBytes().read(2),
                        p.getBytes().read(3)
                );
                data = p.getIntegers().read(4);
            case PAINTING:
                uuid = p.getUUIDs().read(0);
            default:
                location = new Location(
                        target.getWorld(),
                        p.getDoubles().read(0),
                        p.getDoubles().read(1),
                        p.getDoubles().read(2),
                        yaw,
                        pitch
                );
        }
        if (t == null) return null;
        switch (t) {
            case PAINTING:
                return new PacketObjectSpawnEvent(new EntityIdentifier(id, uuid, target), target, t, location, p
                        .getStrings().read(0), p.getBytes().read(0), p);
            case EXPERIENCE_ORB:
                return new PacketObjectSpawnEvent(new EntityIdentifier(id, target), target, t, location, p.getShorts
                        ().read(0), p);
            case LIGHTNING:
                return new PacketObjectSpawnEvent(new EntityIdentifier(id, target), target, t, location, p);
            default:
                return new PacketObjectSpawnEvent(new EntityIdentifier(id, uuid, target), target, t, location,
                        velocity, data, p);
        }
    }

    private static EntityType packetTypeToEntity(PacketType p) {
        if (PacketType.Play.Server.SPAWN_ENTITY_WEATHER.equals(p)) {
            return EntityType.LIGHTNING;
        } else if (PacketType.Play.Server.SPAWN_ENTITY_EXPERIENCE_ORB.equals(p)) {
            return EntityType.EXPERIENCE_ORB;
        } else if (PacketType.Play.Server.SPAWN_ENTITY_PAINTING.equals(p)) {
            return EntityType.PAINTING;
        }
        return EntityType.UNKNOWN;
    }

    @Override
    public EntityType getEntityType() {
        return type;
    }

    @Override
    public void setEntityType(EntityType type) {
        Preconditions.checkArgument(type != EntityType.LIGHTNING && type != EntityType.PAINTING &&
                        type != EntityType.EXPERIENCE_ORB,
                "You cannot override the type of a " + type.name() + " packet!");
        this.type = type;
        super.packet.getBytes().write(0, PacketEntityAPI.OBJECTS.get(type).byteValue());
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
        if (type != EntityType.LIGHTNING && type != EntityType.PAINTING && type != EntityType.EXPERIENCE_ORB) {
            super.packet.getIntegers().write(2, (int) (location.getYaw() * (256.0F / 360.0F)));
            super.packet.getIntegers().write(3, (int) (location.getPitch() * (256.0F / 360.0F)));
        }
    }

    @Override
    public int getData() {
        Preconditions.checkArgument(type != EntityType.LIGHTNING && type != EntityType.PAINTING &&
                        type != EntityType.EXPERIENCE_ORB,
                type.name() + " does not have any associated data!");
        return data;
    }

    @Override
    public void setData(int data) {
        Preconditions.checkArgument(type != EntityType.LIGHTNING && type != EntityType.PAINTING &&
                        type != EntityType.EXPERIENCE_ORB,
                "You cannot set data for a " + type.name() + "!");
        this.data = data;
        super.packet.getIntegers().write(4, data);
    }

    @Override
    public Vector getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(Vector velocity) {
        Preconditions.checkArgument(type != EntityType.LIGHTNING && type != EntityType.PAINTING &&
                        type != EntityType.EXPERIENCE_ORB,
                "You cannot set a velocity for a " + type.name() + "!");
        this.velocity = velocity;
        super.packet.getBytes().write(0, (byte) velocity.getX());
        super.packet.getBytes().write(1, (byte) velocity.getY());
        super.packet.getBytes().write(2, (byte) velocity.getZ());
    }

    @Override
    public short getOrbCount() {
        Preconditions.checkArgument(type == EntityType.EXPERIENCE_ORB,
                type.name() + " is not an experience orb!");
        return orbCount;
    }

    @Override
    public void setOrbCount(short orbCount) {
        Preconditions.checkArgument(type == EntityType.EXPERIENCE_ORB,
                type.name() + " is not an experience orb!");
        this.orbCount = orbCount;
        super.packet.getShorts().write(0, orbCount);
    }

    @Override
    public String getTitle() {
        Preconditions.checkArgument(type == EntityType.PAINTING,
                type.name() + " is not a painting!");
        return title;
    }

    @Override
    public void setTitle(String title) {
        Preconditions.checkArgument(type == EntityType.PAINTING,
                type.name() + " is not a painting!");
        Preconditions.checkArgument(title.length() > 13,
                "That title is too long! Maximum 13 characters.");
        this.title = title;
        super.packet.getStrings().write(0, title);
    }

    @Override
    public byte getDirection() {
        Preconditions.checkArgument(type == EntityType.PAINTING,
                type.name() + " is not a painting!");
        return direction;
    }

    @Override
    public void setDirection(byte direction) {
        Preconditions.checkArgument(type == EntityType.PAINTING,
                type.name() + " is not a painting!");
        this.direction = direction;
        super.packet.getBytes().write(0, direction);
    }
}
