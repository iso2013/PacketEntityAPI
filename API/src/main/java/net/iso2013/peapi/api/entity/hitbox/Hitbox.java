package net.iso2013.peapi.api.entity.hitbox;

import org.bukkit.util.Vector;

/**
 * @author iso2013
 * @version 0.1
 * @since 2018-04-23
 */
public interface Hitbox {
    /**
     * Gets the minimum corner of this hitbox.
     *
     * @return the {@link Vector} representing the minimum corner of this hitbox
     */
    Vector getMin();

    /**
     * Sets the minimum corner of this hitbox.
     *
     * @param vector the {@link Vector} that should be used as the minimum corner of this hitbox
     */
    void setMin(Vector vector);

    /**
     * Gets the maximum corner of this hitbox.
     *
     * @return the {@link Vector} representing the maximum corner of this hitbox
     */
    Vector getMax();

    /**
     * Sets the maximum corner of this hitbox.
     *
     * @param vector the {@link Vector} that should be used as the maximum corner of this hitbox
     */
    void setMax(Vector vector);

    /**
     * Checks whether or not a given line of sight is intersecting the hitbox. Uses an algorithm adapted from
     *
     * @param origin         the location that the line of sight starts at
     * @param angle          the direction the line of sight is facing
     * @param hitboxLocation the location that the hitbox's origin is at
     * @return whether or not the line of sight intersects
     */
    boolean intersects(Vector origin, Vector angle, Vector hitboxLocation);

    /**
     * Creates a copy of this hitbox, cloning the stored vectors as well.
     *
     * @return a new hitbox instance.
     */
    Hitbox deepClone();
}
