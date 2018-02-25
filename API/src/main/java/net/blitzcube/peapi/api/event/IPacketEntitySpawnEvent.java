package net.blitzcube.peapi.api.event;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

/**
 * Created by iso2013 on 2/23/2018.
 */
public interface IPacketEntitySpawnEvent extends IPacketEntityEvent {
    EntityType getEntityType();

    void setEntityType(EntityType type);

    Location getLocation();

    void setLocation(Location location);

    float getHeadPitch();

    void setHeadPitch(float headPitch);

    Vector getVelocity();

    void setVelocity(Vector velocity);
}
