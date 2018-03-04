package net.blitzcube.peapi.entity.living.insentient.creature.golem;

import net.blitzcube.peapi.api.entity.living.insentient.creature.golem.ISnowmanData;
import net.blitzcube.peapi.entity.living.insentient.creature.GolemData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Snowman;

public class SnowmanData extends GolemData implements ISnowmanData {
    public Boolean isPumpkinHat() {
        return (((Byte) super.dataMap.get(12)) & 0x10) > 0;
    }

    public void setPumpkinHat(boolean value) {
        setBitmask(12, (byte) 0x10, value);
    }

    public void unsetPumpkinHat(Entity e) {
        unsetBitmask(12, (byte) 0x10, e == null || e instanceof Snowman && !((Snowman) e).isDerp());
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetPumpkinHat(null);
    }
}
