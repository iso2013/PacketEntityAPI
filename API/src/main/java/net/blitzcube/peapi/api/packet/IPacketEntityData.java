package net.blitzcube.peapi.api.packet;

import com.comphenix.protocol.wrappers.WrappedWatchableObject;

import java.util.List;

/**
 * Created by iso2013 on 4/21/2018.
 */
public interface IPacketEntityData extends IPacketEntity {
    List<WrappedWatchableObject> getMetadata();

    void setMetadata(List<WrappedWatchableObject> data);

    void rewriteMetadata();
}
