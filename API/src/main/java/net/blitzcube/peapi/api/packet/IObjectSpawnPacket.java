package net.blitzcube.peapi.api.packet;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

/**
 * Created by iso2013 on 4/21/2018.
 */
public interface IObjectSpawnPacket extends IEntityPacket {
    /**
     * Gets the entity type that will be created by this packet
     *
     * @return the entity type
     */
    EntityType getEntityType();

    /**
     * Sets the entity type that will be created by this packet
     * <br>
     * This cannot change across packet types - consult http://wiki.vg/Protocol or try it for more information (errors
     * are generated that explain the issue)
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
     * Get the data that corresponds to the spawned object. Consult http://wiki.vg/Object_Data for more information.
     *
     * @return the data for the spawned object
     */
    int getData();

    /**
     * Set the data that corresponds to the spawned object. Consult http://wiki.vg/Object_Data for more information.
     *
     * @param data the new data for the spawned object
     */
    void setData(int data);

    /**
     * Get the entity's velocity
     * <br>
     * This cannot be done for some object types, such as paintings or lightning.
     *
     * @return the velociity
     */
    Vector getVelocity();

    /**
     * Sets the entity's velocity
     *
     * @param velocity the new velocity
     */
    void setVelocity(Vector velocity);

    /**
     * Get the value of the experience orb being spawned by this packet, or null if it is not spawning an experience orb
     *
     * @return the value
     */
    Integer getOrbCount();

    /**
     * Set the value of the experience orb being spawned by this packet, or null if it is not spawning an experience orb
     *
     * @param count the new value
     */
    void setOrbCount(Integer count);

    /**
     * Get the title of the painting being spawned by this packet, or null if it is not spawning a painting
     *
     * @return the title
     */
    String getTitle();

    /**
     * Set the title of the painting being spawned by this packet, or null if it is not spawning a painting
     * @param title the new title
     */
    void setTitle(String title);

    /**
     * Get the direction that the item frame or painting is facing, or null if it is not an item frame or painting.
     * @return the direction
     */
    BlockFace getDirection();

    /**
     * Set the direction that the item frame or painting is facing, or null if it is not an item frame or painting.
     * @param direction the new direction
     */
    void setDirection(BlockFace direction);
}
