package net.iso2013.peapi.api;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import net.iso2013.peapi.api.entity.EntityIdentifier;
import net.iso2013.peapi.api.entity.fake.FakeEntity;
import net.iso2013.peapi.api.entity.fake.FakeEntityFactory;
import net.iso2013.peapi.api.entity.modifier.EntityModifierRegistry;
import net.iso2013.peapi.api.entity.modifier.ModifiableEntity;
import net.iso2013.peapi.api.listener.Listener;
import net.iso2013.peapi.api.packet.EntityPacket;
import net.iso2013.peapi.api.packet.EntityPacketFactory;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author iso2013
 * @version 0.1
 * @since 2018-02-13
 */
public interface PacketEntityAPI {
    /**
     * Add a new listener to be called when a packet event is fired.
     *
     * @param eventListener the new listener to add
     */
    void addListener(Listener eventListener);

    /**
     * Remove a listener from the queue of listeners to be fired
     *
     * @param eventListener the listener to remove
     */
    void removeListener(Listener eventListener);

    /**
     * Wrap a given list of watchable objects into a ModifiableEntity.
     *
     * @param list The list of watchable objects to wrap
     * @return the modifiable entity
     */
    ModifiableEntity wrap(List<WrappedWatchableObject> list);

    /**
     * Wrap a given map of values into a ModifiableEntity.
     *
     * @param map The map of values to wrap
     * @return the modifiable entity
     */
    ModifiableEntity wrap(Map<Integer, Object> map);

    /**
     * Wrap a given data watcher into a ModifiableEntity.
     *
     * @param watcher The watcher to wrap
     * @return the modifiable entity
     */
    ModifiableEntity wrap(WrappedDataWatcher watcher);

    /**
     * Creates a new entity identifier object for the given entity
     *
     * @param e the entity that should be identified
     * @return the new identifier
     */
    EntityIdentifier wrap(Entity e);

    /**
     * Check if a listener has already been registered
     *
     * @param eventListener the event listener to check
     * @return whether or not it was found in the queue
     */
    boolean isListenerRegistered(Listener eventListener);

    /**
     * Check if the entity ID given belongs to a fake entity that has been created by this API.
     *
     * @param entityID the entity ID to check
     * @return whether or not it is a fake entity
     */
    boolean isFakeID(int entityID);

    /**
     * Gets the modifier registry for this instance of the API.
     *
     * @return the modifier registry
     */
    EntityModifierRegistry getModifierRegistry();

    /**
     * Gets the entity factory for this instance of the API.
     *
     * @return the entity factory
     */
    FakeEntityFactory getEntityFactory();

    /**
     * Get a fake entity by its entity ID.
     *
     * @param entityID the entity ID to retrieve
     * @return the fake entity object, or null if the ID does not belong to a fake entity created by this API.
     */
    FakeEntity getFakeByID(int entityID);

    /**
     * Checks whether or not an entity at the given location would be visible to a player, based on the server's entity
     * render distance settings.
     *
     * @param location the location of the entity
     * @param target   the player who is viewing the entity
     * @param err      the multiplier to multiply the max distance by before performing the calculation, use this to
     *                 ensure a
     *                 value is obtained even if the server is lagging slightly
     * @return whether or not the player would be able to see an entity at that location
     */
    boolean isVisible(Location location, Player target, double err);

    /**
     * Get a stream representing all of the entities that the given player can see.
     *
     * @param viewer the player who is viewing the entities
     * @param err    the error factor to include in distance calculations. Should be 1 ~ 1.03.
     * @param fake   whether or not to include fake entities
     * @return the stream of entity identifiers
     */
    Stream<EntityIdentifier> getVisible(Player viewer, double err, boolean fake);

    /**
     * Get a stream representing all of the players that can see a given entity.
     *
     * @param object the entity to calculate for
     * @param err    the error factor to include in distance calculations. Should be 1 ~ 1.03.
     * @return the stream of players
     */
    Stream<Player> getViewers(EntityIdentifier object, double err);

    /**
     * Gets the packet factory for this instance of the API.
     *
     * @return the packet factory
     */
    EntityPacketFactory getPacketFactory();

    /**
     * Sends a packet to the specified player or as the specified player.
     *
     * @param packet the packet to send
     * @param target the player to send it to
     */
    void dispatchPacket(EntityPacket packet, Player target);

    /**
     * Sends a packet to the specified player or as the specified player at a specified delay.
     *
     * @param packet the packet to send
     * @param target the player to send it to
     * @param delay  the number of ticks to wait
     */
    void dispatchPacket(EntityPacket packet, Player target, int delay);
}
