package net.blitzcube.peapi.api.packet;

import com.google.common.collect.ImmutableList;
import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;

/**
 * Created by iso2013 on 4/21/2018.
 */
public interface IEntityGroupPacket extends IEntityPacket {
    /**
     * Gets a list containing all of the entity identifiers that are contained in this packet.
     *
     * @return a list of entity identifiers
     */
    ImmutableList<IEntityIdentifier> getGroup();

    /**
     * Removes a given entity identifier from the list.
     *
     * @param e the identifier to remove
     */
    void removeFromGroup(IEntityIdentifier e);

    /**
     * Adds a given
     *
     * @param e the identifier to add
     */
    void addToGroup(IEntityIdentifier e);

    /**
     * Applies any pending changes to the underlying packet container.
     * This <b>does not</b> need to be called manually - queueing the packet will do this automatically.
     */
    void apply();
}
