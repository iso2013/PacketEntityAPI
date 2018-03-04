package net.blitzcube.peapi.api.entity.living.insentient.creature.monster;

import net.blitzcube.peapi.api.entity.living.insentient.creature.IMonsterData;
import org.bukkit.entity.Entity;

public interface IVindicationIllagerData extends IMonsterData {
    Boolean isAggressive();

    void setAggressive(boolean value);

    void unsetAggressive(Entity e);

    void resetToDefault();

    void clearAll();
}
