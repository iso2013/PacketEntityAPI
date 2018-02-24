package net.blitzcube.peapi.api.event.spawn;

import net.blitzcube.peapi.api.entity.EntityIdentifier;
import net.blitzcube.peapi.api.event.PacketEntityEvent;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Created by iso2013 on 2/23/2018.
 */
public class PacketEntitySpawnEvent extends PacketEntityEvent {
    private Location location;
    private float headPitch;
    private Vector velocity;

    public PacketEntitySpawnEvent(EntityIdentifier identifier, Location location, float headPitch, Vector velocity) {
        super(identifier);
        this.location = location;
        this.headPitch = headPitch;
        this.velocity = velocity;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public float getHeadPitch() {
        return headPitch;
    }

    public void setHeadPitch(float headPitch) {
        this.headPitch = headPitch;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }
}
