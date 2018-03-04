package net.blitzcube.peapi.api.entity.living.insentient.creature.monster;

import net.blitzcube.peapi.api.entity.living.insentient.creature.IMonsterData;
import org.bukkit.entity.Entity;

public interface ISpiderData extends IMonsterData {
    Boolean isClimbing();

    void setClimbing(boolean value);

    void unsetClimbing(Entity e);

    void resetToDefault();

    void clearAll();
}
