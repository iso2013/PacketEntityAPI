package net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.abstracthorse;

import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.IAbstractHorseData;

public interface IChestedHorseData extends IAbstractHorseData {
    Boolean isChested();

    void setChested(Boolean value);

    void unsetChested();

    void resetToDefault();

    void clearAll();
}
