package net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.tameableanimal;

import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.ITameableAnimalData;

public interface IOcelotData extends ITameableAnimalData {
    Integer getType();

    void setType(Integer value);

    void unsetType();

    void resetToDefault();

    void clearAll();
}
