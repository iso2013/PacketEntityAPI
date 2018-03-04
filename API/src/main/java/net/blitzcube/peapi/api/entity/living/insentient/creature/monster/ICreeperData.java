package net.blitzcube.peapi.api.entity.living.insentient.creature.monster;

import net.blitzcube.peapi.api.entity.living.insentient.creature.IMonsterData;

public interface ICreeperData extends IMonsterData {
    Integer getState();

    void setState(Integer value);

    Boolean isCharged();

    Boolean isIgnited();

    void setCharged(Boolean value);

    void setIgnited(Boolean value);

    void unsetState();

    void unsetCharged();

    void unsetIgnited();

    void resetToDefault();

    void clearAll();
}
