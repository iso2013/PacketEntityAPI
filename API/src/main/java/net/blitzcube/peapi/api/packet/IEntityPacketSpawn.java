package net.blitzcube.peapi.api.packet;

import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Created by iso2013 on 4/21/2018.
 */
public interface IEntityPacketSpawn extends IEntityPacket {
    EntityType getEntityType();

    void setEntityType(EntityType type);

    Location getLocation();

    void setLocation(Location location);

    float getHeadPitch();

    void setHeadPitch(float headPitch);

    Vector getVelocity();

    void setVelocity(Vector velocity);

    List<WrappedWatchableObject> getMetadata();

    void setMetadata(List<WrappedWatchableObject> data);

    void rewriteMetadata();
}
