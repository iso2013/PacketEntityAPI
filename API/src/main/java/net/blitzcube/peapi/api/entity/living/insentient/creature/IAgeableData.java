package net.blitzcube.peapi.api.entity.living.insentient.creature;

import net.blitzcube.peapi.api.entity.living.insentient.ICreatureData;

public interface IAgeableData extends ICreatureData {
    Boolean isBaby();

    void setBaby(Boolean value);

    void unsetBaby();

    void resetToDefault();

    void clearAll();
}
