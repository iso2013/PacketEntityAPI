package net.blitzcube.peapi.api.packet;

import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * @author iso2013
 * @version 0.1
 * @since 2018-04-21
 */
public interface IEntitySpawnPacket extends IEntityPacket {
    /**
     * Gets the entity type that will be created by this packet
     *
     * @return the entity type
     */
    EntityType getEntityType();

    /**
     * Sets the entity type that will be created by this packet
     *
     * @param type the entity type
     */
    void setEntityType(EntityType type);

    /**
     * Gets the location that the entity will spawn at
     *
     * @return the location to spawn at
     */
    Location getLocation();

    /**
     * Sets the location that the entity will spawn at
     *
     * @param location the new location
     */
    void setLocation(Location location);

    /**
     * Get the entity's head pitch
     *
     * @return the head pitch
     */
    float getHeadPitch();

    /**
     * Set the entity's head pitch
     *
     * @param headPitch the new head pitch
     */
    void setHeadPitch(float headPitch);

    /**
     * Get the entity's velocity
     *
     * @return the velocity
     */
    Vector getVelocity();

    /**
     * Sets the entity's velocity
     *
     * @param velocity the new velocity
     */
    void setVelocity(Vector velocity);

    /**
     * Gets a list containing all of the metadata stored in this packet
     *
     * @return the list of wrapped watchable objects
     */
    List<WrappedWatchableObject> getMetadata();

    /**
     * Sets the metadata contained by this packet to the data contained in the list given
     *
     * @param data the new list of data to send
     */
    void setMetadata(List<WrappedWatchableObject> data);

    /**
     * Rewrites the currently stored metadata to the packet. This is useful if you pull the list instance using
     * {@link #getMetadata()} and modify it directly; it will cause the changes to be written to the underlying packet
     * container.
     */
    void rewriteMetadata();
}
