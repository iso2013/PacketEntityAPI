package net.blitzcube.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.google.common.base.Preconditions;
import net.blitzcube.peapi.PacketEntityAPI;
import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.api.packet.IObjectSpawnPacket;
import net.blitzcube.peapi.entity.EntityIdentifier;
import org.bukkit.Art;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.UUID;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class ObjectSpawnPacket extends EntityPacket implements IObjectSpawnPacket {
    private EntityType type;
    private Location location;
    private Vector velocity;
    private Integer orbCount;
    private Art art;
    private BlockFace direction;
    private int data;

    ObjectSpawnPacket(IEntityIdentifier identifier, EntityType type) {
        super(identifier, new PacketContainer(entityTypeToPacket(type)), true);
        this.velocity = new Vector(0, 0, 0);
        data = 0;

        super.rawPacket.getUUIDs().write(0, identifier.getUUID());
    }

    private ObjectSpawnPacket(IEntityIdentifier identifier, PacketContainer packet, EntityType type, Location location,
                              Vector velocity, int data, UUID uuid) {
        super(identifier, packet, false);
        this.type = type;
        this.location = location;
        this.velocity = velocity;
        this.data = data;

        super.rawPacket.getUUIDs().write(0, uuid);
    }

    private ObjectSpawnPacket(IEntityIdentifier identifier, PacketContainer packet, EntityType type, Location location,
                              Art art, BlockFace direction, UUID uuid) {
        super(identifier, packet, false);
        this.type = type;
        this.location = location;
        this.art = art;
        this.direction = direction;

        super.rawPacket.getUUIDs().write(0, uuid);
    }

    private ObjectSpawnPacket(IEntityIdentifier identifier, PacketContainer packet, EntityType type, Location location,
                              Integer orbCount) {
        super(identifier, packet, false);
        this.type = type;
        this.location = location;
        this.orbCount = orbCount;
    }

    private ObjectSpawnPacket(IEntityIdentifier identifier, PacketContainer packet, EntityType type,
                              Location location) {
        super(identifier, packet, false);
        this.type = type;
        this.location = location;
    }

    public static ObjectSpawnPacket unwrap(int entityID, PacketContainer c, Player p) {
        return unwrap(EntityIdentifier.produce(entityID, p), c, p.getWorld());
    }

    private static ObjectSpawnPacket unwrap(IEntityIdentifier i, PacketContainer c, World w) {
        EntityType t = packetTypeToEntity(c.getType());
        float pitch = 0.0F, yaw = 0.0F;
        Vector velocity = new Vector();
        int data = 0;
        UUID uuid = null;
        Location location;
        if (t == null) return null;
        switch (t) {
            case UNKNOWN:
                yaw = c.getIntegers().read(5).floatValue() * (360.0F / 256.0F);
                pitch = c.getIntegers().read(4).floatValue() * (360.0F / 256.0F);
                t = PacketEntityAPI.lookupObject(c.getIntegers().read(6));
                velocity = new Vector(
                        c.getIntegers().read(1),
                        c.getIntegers().read(2),
                        c.getIntegers().read(3)
                );
                data = c.getIntegers().read(7);
            case PAINTING:
                uuid = c.getUUIDs().read(0);
                if (EntityType.PAINTING.equals(t)) {
                    location = c.getBlockPositionModifier().read(0).toLocation(w);
                    break;
                }
            default:
                location = new Location(
                        w,
                        c.getDoubles().read(0),
                        c.getDoubles().read(1),
                        c.getDoubles().read(2),
                        yaw,
                        pitch
                );
        }
        if (t == null) return null;
        switch (t) {
            case PAINTING:
                return new ObjectSpawnPacket(i, c, t, location, Art.values()[c
                        .getIntegers().read(1)], directionToBlockFace(c.getDirections().read(0)), uuid);
            case EXPERIENCE_ORB:
                return new ObjectSpawnPacket(i, c, t, location, c.getIntegers
                        ().read(1));
            case LIGHTNING:
                return new ObjectSpawnPacket(i, c, t, location);
            default:
                return new ObjectSpawnPacket(i, c, t, location, velocity, data, uuid);
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

    private static PacketType entityTypeToPacket(EntityType type) {
        switch (type) {
            case LIGHTNING:
                return PacketType.Play.Server.SPAWN_ENTITY_WEATHER;
            case EXPERIENCE_ORB:
                return PacketType.Play.Server.SPAWN_ENTITY_EXPERIENCE_ORB;
            case PAINTING:
                return PacketType.Play.Server.SPAWN_ENTITY_PAINTING;
        }
        return PacketType.Play.Server.SPAWN_ENTITY;
    }

    private static BlockFace directionToBlockFace(EnumWrappers.Direction d) {
        switch (d) {
            case DOWN:
                return BlockFace.DOWN;
            case UP:
                return BlockFace.UP;
            case NORTH:
                return BlockFace.NORTH;
            case SOUTH:
                return BlockFace.SOUTH;
            case WEST:
                return BlockFace.WEST;
            case EAST:
                return BlockFace.EAST;
        }
        return null;
    }

    private static EnumWrappers.Direction blockFaceToDirection(BlockFace f) {
        switch (f) {
            case DOWN:
                return EnumWrappers.Direction.DOWN;
            case UP:
                return EnumWrappers.Direction.UP;
            case NORTH:
                return EnumWrappers.Direction.NORTH;
            case SOUTH:
                return EnumWrappers.Direction.SOUTH;
            case WEST:
                return EnumWrappers.Direction.WEST;
            case EAST:
                return EnumWrappers.Direction.EAST;
        }
        return null;
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
        Preconditions.checkArgument(PacketEntityAPI.OBJECTS.containsKey(type),
                "You cannot spawn an entity with an object packet!");
        this.type = type;
        super.rawPacket.getIntegers().write(6, PacketEntityAPI.OBJECTS.get(type));
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
        Vector v;
        if (this.location == null) {
            v = new Vector(0, 0, 0);
        } else v = this.location.toVector();
        super.rawPacket.getDoubles().write(0, v.getX());
        super.rawPacket.getDoubles().write(1, v.getY());
        super.rawPacket.getDoubles().write(2, v.getZ());
        if (type != EntityType.LIGHTNING && type != EntityType.PAINTING && type != EntityType.EXPERIENCE_ORB && this
                .location != null) {
            super.rawPacket.getIntegers().write(5, (int) (location.getYaw() * (256.0F / 360.0F)));
            super.rawPacket.getIntegers().write(4, (int) (location.getPitch() * (256.0F / 360.0F)));
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
        super.rawPacket.getIntegers().write(7, data);
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
        //FixMe: This implementation is not correct.
        super.rawPacket.getIntegers().write(1, (int) velocity.getX());
        super.rawPacket.getIntegers().write(2, (int) velocity.getY());
        super.rawPacket.getIntegers().write(3, (int) velocity.getZ());
    }

    @Override
    public Integer getOrbCount() {
        Preconditions.checkArgument(type == EntityType.EXPERIENCE_ORB,
                type.name() + " is not an experience orb!");
        return orbCount;
    }

    @Override
    public void setOrbCount(Integer orbCount) {
        Preconditions.checkArgument(type == EntityType.EXPERIENCE_ORB,
                type.name() + " is not an experience orb!");
        this.orbCount = orbCount;
        super.rawPacket.getIntegers().write(1, orbCount);
    }

    @Override
    public Art getArt() {
        Preconditions.checkArgument(type == EntityType.PAINTING,
                type.name() + " is not a painting!");
        return art;
    }

    @Override
    public void setArt(Art art) {
        Preconditions.checkArgument(type == EntityType.PAINTING,
                type.name() + " is not a painting!");
        Preconditions.checkArgument(art != null,
                "Cannot set a painting to no art!");
        this.art = art;
        super.rawPacket.getIntegers().write(1, art.ordinal());
    }

    @Override
    public BlockFace getDirection() {
        Preconditions.checkArgument(type == EntityType.PAINTING,
                type.name() + " is not a painting!");
        return direction;
    }

    @Override
    public void setDirection(BlockFace direction) {
        Preconditions.checkArgument(type == EntityType.PAINTING,
                type.name() + " is not a painting!");
        this.direction = direction;
        super.rawPacket.getDirections().write(0, blockFaceToDirection(direction));
    }

    @Override
    public PacketContainer getRawPacket() {
        switch (type) {
            case EXPERIENCE_ORB:
                assert location != null && orbCount > 0;
                break;
            case PAINTING:
                assert art != null && direction != null;
            case LIGHTNING:
            default:
                assert location != null;
                break;
        }
        return super.getRawPacket();
    }

    @Override
    public EntityPacket clone() {
        return unwrap(getIdentifier(), super.getRawPacket(), location != null ? location.getWorld() : null);
    }
}
