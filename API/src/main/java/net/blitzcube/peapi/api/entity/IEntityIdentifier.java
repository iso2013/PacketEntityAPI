package net.blitzcube.peapi.api.entity;

import net.blitzcube.peapi.api.entity.fake.IFakeEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;
import java.util.UUID;

/**
 * @author iso2013
 * @version 0.1
 * @since 2018-02-23
 */
public interface IEntityIdentifier {
    /**
     * Attempts to find more information about the entity that this identifies.
     * For example, if the identifier was created by a packet, it will contain the entity ID but not the entity or fake
     * entity object. Calling this method will lookup entities with a matching ID and update the references.
     */
    void moreSpecific();

    /**
     * Gets the entity ID that is associated with this identifier. This is never undefined.
     *
     * @return the entity ID associated with this identifier
     */
    int getEntityID();

    /**
     * Gets the {@link UUID} that is associated with this identifier. This may be null if the identifier was constructed by a
     * packet listener, in which case we have no information about the entity that the ID belongs to. We can attempt to
     * find this if it is not present by using {@link #moreSpecific()}
     * @return the {@link UUID} of the entity that this is associated with
     */
    UUID getUUID();

    /**
     * Gets the reference to the player that this identifier was constructed for. This will be null unless the
     * identifier was retrieved from a packet event, in which case it will be the player that the event is being fired
     * for.
     * @return the {@link Player} that this identifier was created for
     */
    WeakReference<Player> getNear();

    /**
     * Gets the reference to the entity that this identifier identifies. This may be null if the identifier was constructed by a
     * packet listener, in which case we have no information about the entity that the ID belongs to. We can attempt to
     * find this if it is not present by using {@link #moreSpecific()}
     * <br>
     * If this is a fake entity, this value will be null.
     * @return the entity that this identifier is referencing
     */
    WeakReference<Entity> getEntity();

    /**
     * Gets the reference to the fake entity that this identifier identifies. This may be null if the identifier was
     * constructed by a packet listener, in which case we have no information about the entity that the ID belongs to.
     * We can attempt to find this if it is not present by using {@link #moreSpecific()}
     * <br>
     * If this is a real entity, this value will be null.
     * @return the fake entity that this identifier is referencing.
     */
    WeakReference<IFakeEntity> getFakeEntity();

    /**
     * Returns whether or not this entity is a fake entity.
     * @return whether or not this is a fake entity
     */
    boolean isFakeEntity();
}