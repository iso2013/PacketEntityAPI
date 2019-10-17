package net.blitzcube.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.google.common.base.Preconditions;
import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.api.packet.IEntitySpawnPacket;
import net.blitzcube.peapi.entity.EntityIdentifier;
import net.blitzcube.peapi.util.EntityTypeUtil;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class EntitySpawnPacket extends EntityPacket implements IEntitySpawnPacket {
    private static final PacketType TYPE = PacketType.Play.Server.SPAWN_ENTITY_LIVING;

    private EntityType type;
    private Location location;
    private Vector velocity;
    private float headPitch;
    private List<WrappedWatchableObject> metadata;

    EntitySpawnPacket(IEntityIdentifier identifier) {
        super(identifier, new PacketContainer(TYPE), true);
        this.velocity = new Vector(0, 0, 0);
        this.headPitch = 0.0f;
        this.metadata = new ArrayList<>();

        super.rawPacket.getUUIDs().write(0, identifier.getUUID());
    }

    private EntitySpawnPacket(IEntityIdentifier identifier, PacketContainer rawPacket, EntityType type,
                              Location location, Vector velocity, float headPitch,
                              List<WrappedWatchableObject> metadata) {
        super(identifier, rawPacket, false);
        this.type = type;
        this.location = location;
        this.velocity = velocity;
        this.headPitch = headPitch;
        this.metadata = metadata;
    }

    public static EntityPacket unwrap(int entityID, PacketContainer c, Player p) {
        PacketType t = c.getType();
        UUID uuid = c.getUUIDs().read(0);
        Location location;
        if (t.equals(PacketType.Play.Server.NAMED_ENTITY_SPAWN)) {
            location = new Location(
                    p.getWorld(),
                    c.getDoubles().read(0),
                    c.getDoubles().read(1),
                    c.getDoubles().read(2)
            );
        } else {
            location = new Location(
                    p.getWorld(),
                    c.getDoubles().read(0),
                    c.getDoubles().read(1),
                    c.getDoubles().read(2),
                    c.getIntegers().read(2).floatValue() * (360.0F / 256.0F),
                    c.getIntegers().read(3).floatValue() * (360.0F / 256.0F)
            );
        }
        IEntityIdentifier identifier = EntityIdentifier.produce(entityID, uuid, p);
        if (PacketType.Play.Server.NAMED_ENTITY_SPAWN.equals(t)) {
            return new EntitySpawnPacket(identifier, c, EntityType.PLAYER, location, new Vector(0, 0,
                    0), 0.0f, c.getWatchableCollectionModifier().read(0));
        } else if (PacketType.Play.Server.SPAWN_ENTITY_LIVING.equals(t)) {
            EntityType type = EntityTypeUtil.read(c, -1, 1, false);
            //EntityType type = typeFromID(c.getIntegers().read(1));
            float headPitch = c.getIntegers().read(4).floatValue() * (360.0F / 256.0F);
            Vector velocity = new Vector(
                    c.getBytes().read(0),
                    c.getBytes().read(1),
                    c.getBytes().read(2)
            );
            return new EntitySpawnPacket(identifier, c, type, location, velocity, headPitch,
                    c.getWatchableCollectionModifier().read(0));
        }
        return null;
    }

    @Override
    public EntityType getEntityType() {
        return type;
    }

    @Override
    public void setEntityType(EntityType type) {
        Preconditions.checkArgument(this.type != EntityType.PLAYER, "You cannot modify the " +
                "type of a player spawn packet!");
        this.type = type;
        EntityTypeUtil.write(type, super.rawPacket, -1, 1, false);
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
        if (this.location != null) {
            super.rawPacket.getIntegers().write(2, (int) (location.getYaw() * (256.0F / 360.0F)));
            super.rawPacket.getIntegers().write(3, (int) (location.getPitch() * (256.0F / 360.0F)));
        }
    }

    @Override
    public float getHeadPitch() {
        return headPitch;
    }

    @Override
    public void setHeadPitch(float headPitch) {
        this.headPitch = headPitch;
        super.rawPacket.getIntegers().write(4, (int) (headPitch * (256.0F / 360.0F)));
    }

    @Override
    public Vector getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(Vector velocity) {
        Preconditions.checkArgument(type != EntityType.PLAYER,
                "You cannot set the velocity of a player!");
        this.velocity = velocity;
        super.rawPacket.getBytes().write(0, (byte) velocity.getX());
        super.rawPacket.getBytes().write(1, (byte) velocity.getY());
        super.rawPacket.getBytes().write(2, (byte) velocity.getZ());
    }

    @Override
    public List<WrappedWatchableObject> getMetadata() {
        return metadata;
    }

    @Override
    public void setMetadata(List<WrappedWatchableObject> metadata) {
        this.metadata = metadata;
        super.rawPacket.getWatchableCollectionModifier().write(0, metadata);
    }

    @Override
    public void rewriteMetadata() {
        super.rawPacket.getWatchableCollectionModifier().write(0, metadata);
    }

    @Override
    public PacketContainer getRawPacket() {
        assert type != null && location != null;
        super.rawPacket.getDataWatcherModifier().write(0, new WrappedDataWatcher(metadata));
        return super.getRawPacket();
    }

    @Override
    public EntityPacket clone() {
        return new EntitySpawnPacket(getIdentifier(), new PacketContainer(TYPE), type, location, velocity, headPitch,
                metadata);
    }
}
