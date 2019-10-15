package net.blitzcube.peapi.api.entity;

import net.blitzcube.peapi.api.entity.fake.IFakeEntity;
import org.bukkit.entity.Entity;

import java.util.UUID;

/**
 * @author iso2013
 * @version 0.1
 * @since 2018-02-23
 */
public interface IEntityIdentifier {
    /**
     * Gets the entity ID that is associated with this identifier.
     *
     * @return the entity ID associated with this identifier
     */
    int getEntityID();

    /**
     * Gets the {@link UUID} that is associated with this identifier.
     * @return the {@link UUID} of the entity that this is associated with
     */
    UUID getUUID();

    /**
     * Gets the reference to the entity that this identifier identifies.
     * <br>
     * If this is a fake entity, this value will be null.
     * @return the entity that this identifier is referencing
     */
    Entity getEntity();

    /**
     * Gets the reference to the fake entity that this identifier identifies.
     * <br>
     * If this is a real entity, this value will be null.
     * @return the fake entity that this identifier is referencing.
     */
    IFakeEntity getFakeEntity();

    /**
     * Returns whether or not this entity is a fake entity.
     * @return whether or not this is a fake entity
     */
    boolean isFakeEntity();
}