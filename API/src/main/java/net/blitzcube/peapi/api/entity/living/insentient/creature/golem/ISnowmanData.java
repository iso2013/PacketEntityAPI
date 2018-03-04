package net.blitzcube.peapi.api.entity.living.insentient.creature.golem;

import net.blitzcube.peapi.api.entity.living.insentient.creature.IGolemData;
import org.bukkit.entity.Entity;

public interface ISnowmanData extends IGolemData {
    Boolean isPumpkinHat();

    void setPumpkinHat(boolean value);

    void unsetPumpkinHat(Entity e);

    void resetToDefault();

    void clearAll();
}
