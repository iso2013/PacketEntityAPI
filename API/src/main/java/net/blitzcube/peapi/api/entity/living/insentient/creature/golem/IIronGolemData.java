package net.blitzcube.peapi.api.entity.living.insentient.creature.golem;

import net.blitzcube.peapi.api.entity.living.insentient.creature.IGolemData;
import org.bukkit.entity.Entity;

public interface IIronGolemData extends IGolemData {
    Boolean isPlayerCreated();

    void setPlayerCreated(boolean value);

    void unsetPlayerCreated(Entity e);

    void resetToDefault();

    void clearAll();
}
