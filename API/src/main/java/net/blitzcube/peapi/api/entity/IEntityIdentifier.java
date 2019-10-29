package net.blitzcube.peapi.api.entity;

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
     *
     * @return the {@link UUID} of the entity that this is associated with
     */
    UUID getUUID();
}