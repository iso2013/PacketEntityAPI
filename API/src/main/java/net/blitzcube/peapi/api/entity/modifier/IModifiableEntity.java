package net.blitzcube.peapi.api.entity.modifier;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;

import java.util.List;
import java.util.Map;

/**
 * @author iso2013
 * @version 0.1
 * @since 2018-04-18
 */
public interface IModifiableEntity {
    /**
     * Get a list of the watchable objects that represents the metadata contained by this modifiable entity object.
     *
     * @return a list of all of the wrapped watchable objects with the metadata values, or null if the implementation
     * does not support getting a list in its current state
     */
    List<WrappedWatchableObject> getWatchableObjects();

    /**
     * Get a {@link WrappedDataWatcher} containing the metadata contained by this modifiable entity object.
     * @return a wrapped data watcher containing the metadata, or null if the implementation does not support getting a
     * watcher
     */
    WrappedDataWatcher getDataWatcher();

    /**
     * Get an index-keyed map of the metadata and the values contained by the metadata at each index.
     * @return an map where the key is the index and the value is the metadata
     */
    Map<Integer, Object> getRawObjects();

    /**
     * Read an object at the given index
     * @param index the index to read
     * @return the object contained
     */
    Object read(int index);

    /**
     * Write an object at the given index, using a provided serializer
     * @param index the index to write at
     * @param newValue the object to write
     * @param serializer the serializer to use
     */
    void write(int index, Object newValue, WrappedDataWatcher.Serializer serializer);

    /**
     * Remove an object stored at the given index.
     * @param index the index to remove
     */
    void clear(int index);

    /**
     * Clears all data contained in this modifiable entity.
     */
    void clear();

    /**
     * Check if this entity contains an entry for the given index
     * @param index the index to check for
     * @return whether or not the index is present
     */
    boolean contains(int index);
}
