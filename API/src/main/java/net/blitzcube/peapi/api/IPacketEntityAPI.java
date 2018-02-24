package net.blitzcube.peapi.api;

import net.blitzcube.peapi.api.listener.IListener;

/**
 * Created by iso2013 on 2/13/2018.
 */
public interface IPacketEntityAPI {
    void addListener(IListener eventListener);

    void removeListener(IListener eventListener);

    boolean isListenerRegistered(IListener eventListener);

    boolean isFakeID(int entityID);

    interface ProviderStub {
        String getMajorVersion();

        String getFullVersion();

        IPacketEntityAPI getInstance();
    }
}
