package net.blitzcube.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.google.common.base.Preconditions;
import net.blitzcube.peapi.PacketEntityAPI;
import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;
import net.blitzcube.peapi.api.packet.IPacketObjectSpawn;
import net.blitzcube.peapi.entity.modifier.EntityIdentifier;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.UUID;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class ObjectSpawnPacket extends EntityPacket implements IPacketObjectSpawn {
    private static final int TICK_DELAY = 0;
    private EntityType type;
    private Location location;
    private Vector velocity;
    private Integer orbCount;
    private String title;
    private BlockFace direction;
    private int data;

    private ObjectSpawnPacket(IEntityIdentifier identifier, EntityType type) {
        super(identifier, new PacketContainer(entityTypeToPacket(type)));
        this.velocity = new Vector(0, 0, 0);
        data = 0;
    }

    private ObjectSpawnPacket(IEntityIdentifier identifier, PacketContainer packet, EntityType type, Location location,
                              Vector velocity, int data) {
        super(identifier, packet);
        this.type = type;
        this.location = location;
        this.velocity = velocity;
        this.data = data;
    }

    private ObjectSpawnPacket(IEntityIdentifier identifier, PacketContainer packet, EntityType type, Location location,
                              String title, BlockFace direction) {
        super(identifier, packet);
        this.type = type;
        this.location = location;
        this.title = title;
        this.direction = direction;
    }

    private ObjectSpawnPacket(IEntityIdentifier identifier, PacketContainer packet, EntityType type, Location location,
                              Integer orbCount) {
        super(identifier, packet);
        this.type = type;
        this.location = location;
        this.orbCount = orbCount;
    }

    private ObjectSpawnPacket(IEntityIdentifier identifier, PacketContainer packet, EntityType type,
                              Location location) {
        super(identifier, packet);
        this.type = type;
        this.location = location;
    }

    public static ObjectSpawnPacket unwrap(int entityID, PacketContainer c, Player p) {
        EntityType t = packetTypeToEntity(c.getType());
        float pitch = 0.0F, yaw = 0.0F;
        Vector velocity = new Vector();
        int data = 0;
        UUID uuid = null;
        Location location;
        if (t == null) return null;
        switch (t) {
            case UNKNOWN:
                yaw = c.getIntegers().read(1).floatValue() * (360.0F / 256.0F);
                pitch = c.getIntegers().read(2).floatValue() * (360.0F / 256.0F);
                t = PacketEntityAPI.lookupObject(c.getIntegers().read(6).byteValue());
                velocity = new Vector(
                        c.getIntegers().read(2),
                        c.getIntegers().read(3),
                        c.getIntegers().read(4)
                );
                data = c.getIntegers().read(4);
            case PAINTING:
                uuid = c.getUUIDs().read(0);
                if (EntityType.PAINTING.equals(t)) {
                    location = c.getBlockPositionModifier().read(0).toLocation(p.getWorld());
                    break;
                }
            default:
                location = new Location(
                        p.getWorld(),
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
                return new ObjectSpawnPacket(new EntityIdentifier(entityID, uuid, p), c, t, location, c
                        .getStrings().read(0), directionToBlockFace(c.getDirections().read(0)));
            case EXPERIENCE_ORB:
                return new ObjectSpawnPacket(new EntityIdentifier(entityID, p), c, t, location, c.getIntegers
                        ().read(1));
            case LIGHTNING:
                return new ObjectSpawnPacket(new EntityIdentifier(entityID, p), c, t, location);
            default:
                return new ObjectSpawnPacket(new EntityIdentifier(entityID, uuid, p), c, t, location,
                        velocity, data);
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
        super.rawPacket.getBytes().write(0, PacketEntityAPI.OBJECTS.get(type).byteValue());
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
        super.rawPacket.getDoubles().write(0, location.getX());
        super.rawPacket.getDoubles().write(1, location.getY());
        super.rawPacket.getDoubles().write(2, location.getZ());
        if (type != EntityType.LIGHTNING && type != EntityType.PAINTING && type != EntityType.EXPERIENCE_ORB) {
            super.rawPacket.getIntegers().write(1, (int) (location.getYaw() * (256.0F / 360.0F)));
            super.rawPacket.getIntegers().write(2, (int) (location.getPitch() * (256.0F / 360.0F)));
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
        //TODO: This is wrong.
        super.rawPacket.getIntegers().write(3, (int) velocity.getX());
        super.rawPacket.getIntegers().write(4, (int) velocity.getY());
        super.rawPacket.getIntegers().write(5, (int) velocity.getZ());
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
        super.rawPacket.getStrings().write(0, title);
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
                assert title != null && !title.isEmpty() && direction != null;
            case LIGHTNING:
            default:
                assert location != null;
                break;
        }
        return super.getRawPacket();
    }
}
