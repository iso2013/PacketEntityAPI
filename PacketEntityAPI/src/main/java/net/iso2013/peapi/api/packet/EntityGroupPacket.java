package net.iso2013.peapi.api.packet;

import com.google.common.collect.ImmutableList;
import net.iso2013.peapi.api.entity.EntityIdentifier;

/**
 * @author iso2013
 * @version 0.1
 * @since 2018-04-21
 */
public interface EntityGroupPacket extends EntityPacket {
    /**
     * Gets a list containing all of the entity identifiers that are contained in this packet.
     *
     * @return a list of entity identifiers
     */
    ImmutableList<EntityIdentifier> getGroup();

    /**
     * Removes a given entity identifier from the list.
     *
     * @param e the identifier to remove
     */
    void removeFromGroup(EntityIdentifier e);

    /**
     * Adds a given
     *
     * @param e the identifier to add
     */
    void addToGroup(EntityIdentifier e);

    /**
     * Applies any pending changes to the underlying packet container.
     * This <b>does not</b> need to be called manually - queueing the packet will do this automatically.
     */
    void apply();
}
