package net.blitzcube.peapi.api.event;

import com.comphenix.protocol.wrappers.WrappedWatchableObject;

import java.util.List;

/**
 * Created by iso2013 on 2/24/2018.
 */
public interface IPacketEntityDataEvent extends IPacketEntityEvent {
    List<WrappedWatchableObject> getMetadata();

    void setMetadata(List<WrappedWatchableObject> data);
}
