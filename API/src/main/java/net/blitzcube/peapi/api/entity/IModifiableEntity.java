package net.blitzcube.peapi.api.entity;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;

import java.util.List;
import java.util.Map;

/**
 * Created by iso2013 on 4/18/2018.
 */
public interface IModifiableEntity {
    List<WrappedWatchableObject> getWatchableObjects();

    WrappedDataWatcher getDataWatcher();

    Map<Integer, Object> getRawObjects();

    Object read(int index);

    void write(int index, Object newValue, WrappedDataWatcher.Serializer serializer);

    void clear(int index);

    boolean contains(int index);
}
