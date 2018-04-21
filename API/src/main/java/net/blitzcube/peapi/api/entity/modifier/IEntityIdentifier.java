package net.blitzcube.peapi.api.entity.modifier;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;
import java.util.UUID;

/**
 * Created by iso2013 on 2/23/2018.
 */
public interface IEntityIdentifier {

    void moreSpecific();

    int getEntityID();

    UUID getUUID();

    WeakReference<Player> getNear();

    WeakReference<Entity> getEntity();

    WeakReference<Object> getFakeEntity();
}