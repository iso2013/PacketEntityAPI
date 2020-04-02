package net.iso2013.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.google.common.base.Preconditions;
import net.iso2013.peapi.api.entity.EntityIdentifier;
import net.iso2013.peapi.api.packet.EntitySpawnPacket;
import net.iso2013.peapi.entity.EntityIdentifierImpl;
import net.iso2013.peapi.util.EntityTypeUtil;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class EntitySpawnPacketImpl extends EntityPacketImpl implements EntitySpawnPacket {
    private static final PacketType TYPE = PacketType.Play.Server.SPAWN_ENTITY_LIVING;

    private EntityType type;
    private Location location;
    private Vector velocity;
    private float headPitch;
    private List<WrappedWatchableObject> metadata;

    EntitySpawnPacketImpl(EntityIdentifier identifier) {
        super(identifier, new PacketContainer(TYPE), true);
        this.velocity = new Vector(0, 0, 0);
        this.headPitch = 0.0f;
        this.metadata = new ArrayList<>();

        super.rawPacket.getUUIDs().write(0, identifier.getUUID());
    }

    private EntitySpawnPacketImpl(EntityIdentifier identifier, PacketContainer rawPacket, EntityType type,
                                  Location location, Vector velocity, float headPitch,
                                  List<WrappedWatchableObject> metadata) {
        super(identifier, rawPacket, false);
        this.type = type;
        this.location = location;
        this.velocity = velocity;
        this.headPitch = headPitch;
        this.metadata = metadata;
    }

    public static EntityPacketImpl unwrap(int entityID, PacketContainer c, Player p) {
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
        EntityIdentifier identifier = EntityIdentifierImpl.produce(entityID, uuid, p);
        if (PacketType.Play.Server.NAMED_ENTITY_SPAWN.equals(t)) {
            List<WrappedWatchableObject> data = null;
            if (c.getWatchableCollectionModifier().size() > 0) {
                data = c.getWatchableCollectionModifier().read(0);
            }
            return new EntitySpawnPacketImpl(identifier, c, EntityType.PLAYER, location, new Vector(0, 0,
                    0), 0.0f, data);
        } else if (PacketType.Play.Server.SPAWN_ENTITY_LIVING.equals(t)) {
            EntityType type = EntityTypeUtil.read(c, -1, 1, false);
            //EntityType type = typeFromID(c.getIntegers().read(1));
            float headPitch = c.getIntegers().read(4).floatValue() * (360.0F / 256.0F);
            Vector velocity = new Vector(
                    c.getBytes().read(0),
                    c.getBytes().read(1),
                    c.getBytes().read(2)
            );

            List<WrappedWatchableObject> data = null;
            if (c.getWatchableCollectionModifier().size() > 0) {
                data = c.getWatchableCollectionModifier().read(0);
            }

            return new EntitySpawnPacketImpl(identifier, c, type, location, velocity, headPitch, data);
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
    public List<WrappedWatchableObject> getMetadata() throws IllegalStateException {
        if (metadata == null)
            throw new IllegalStateException("Spawn packet cannot contain data");
        return metadata;
    }

    @Override
    public void setMetadata(List<WrappedWatchableObject> metadata) throws IllegalStateException {
        if (super.rawPacket.getWatchableCollectionModifier().size() > 0) {
            this.metadata = metadata;
            super.rawPacket.getWatchableCollectionModifier().write(0, metadata);
        } else {
            throw new IllegalStateException("Spawn packet cannot contain data");
        }
    }

    @Override
    public void rewriteMetadata() {
        if (super.rawPacket.getWatchableCollectionModifier().size() > 0)
            super.rawPacket.getWatchableCollectionModifier().write(0, metadata);
    }

    @Override
    public PacketContainer getRawPacket() {
        assert type != null && location != null;
        if (super.rawPacket.getDataWatcherModifier().size() > 0)
            super.rawPacket.getDataWatcherModifier().write(0, new WrappedDataWatcher(metadata));
        return super.getRawPacket();
    }

    @Override
    public EntityPacketImpl clone() {
        return new EntitySpawnPacketImpl(getIdentifier(), new PacketContainer(TYPE), type, location, velocity,
                headPitch,
                metadata);
    }
}
