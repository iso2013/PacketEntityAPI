package net.iso2013.peapi.api.entity;

import org.bukkit.entity.Entity;

/**
 * Created by iso2013 on 10/29/19.
 */
public interface RealEntityIdentifier extends EntityIdentifier {
    /**
     * Gets the reference to the entity that this identifier identifies.
     * <br>
     * If this is a fake entity, this value will be null.
     *
     * @return the entity that this identifier is referencing
     */
    Entity getEntity();
}
