package net.blitzcube.peapi.api;

import net.blitzcube.peapi.api.entity.fake.IFakeEntity;
import net.blitzcube.peapi.api.entity.fake.IFakeEntityFactory;
import net.blitzcube.peapi.api.entity.modifier.IEntityModifierRegistry;
import net.blitzcube.peapi.api.listener.IListener;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by iso2013 on 2/13/2018.
 */
public interface IPacketEntityAPI {
    void addListener(IListener eventListener);

    void removeListener(IListener eventListener);

    boolean isListenerRegistered(IListener eventListener);

    boolean isFakeID(int entityID);

    IEntityModifierRegistry getModifierRegistry();

    IFakeEntityFactory getEntityFactory();

    IFakeEntity getFakeByID(int entityID);

    boolean isVisible(Location location, Player target, double v);

    interface ProviderStub {
        String getMajorVersion();

        String getFullVersion();

        IPacketEntityAPI getInstance();
    }
}
