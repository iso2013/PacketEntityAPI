package net.blitzcube.peapi.api.entity.living.insentient.creature.monster;

import net.blitzcube.peapi.api.entity.living.insentient.creature.IMonsterData;
import org.bukkit.entity.Entity;

public interface IBlazeData extends IMonsterData {
    Boolean isBlazeFire();

    void setBlazeFire(boolean value);

    void unsetBlazeFire(Entity e);

    void resetToDefault();

    void clearAll();
}
