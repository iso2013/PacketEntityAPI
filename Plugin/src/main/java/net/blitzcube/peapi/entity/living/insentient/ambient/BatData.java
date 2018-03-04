package net.blitzcube.peapi.entity.living.insentient.ambient;

import net.blitzcube.peapi.api.entity.living.insentient.ambient.IBatData;
import net.blitzcube.peapi.entity.living.insentient.AmbientData;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;

public class BatData extends AmbientData implements IBatData {
    public Boolean isHanging() {
        return (((Byte) super.dataMap.get(12)) & 0x1) > 0;
    }

    public void setHanging(boolean value) {
        setBitmask(12, (byte) 0x01, value);
    }

    public void unsetHanging(Entity e) {
        unsetBitmask(12, (byte) 0x01, e != null && e instanceof Bat && !((Bat) e).isAwake());
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetHanging(null);
    }
}
