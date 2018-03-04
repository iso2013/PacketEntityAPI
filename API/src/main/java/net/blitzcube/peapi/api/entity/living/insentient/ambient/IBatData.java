package net.blitzcube.peapi.api.entity.living.insentient.ambient;

import net.blitzcube.peapi.api.entity.living.insentient.IAmbientData;
import org.bukkit.entity.Entity;

public interface IBatData extends IAmbientData {
    Boolean isHanging();

    void setHanging(boolean value);

    void unsetHanging(Entity e);

    void resetToDefault();

    void clearAll();
}
