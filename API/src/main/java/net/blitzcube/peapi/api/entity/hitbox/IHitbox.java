package net.blitzcube.peapi.api.entity.hitbox;

import org.bukkit.util.Vector;

/**
 * Created by iso2013 on 4/21/2018.
 */
public interface IHitbox {
    Vector getMin();

    void setMin(Vector vector);

    Vector getMax();

    void setMax(Vector vector);

    boolean intersects(Vector origin, Vector angle, Vector hitboxLocation);

    IHitbox deepClone();
}
