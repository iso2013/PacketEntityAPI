package net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal;

import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.IAnimalData;

public interface IPolarBearData extends IAnimalData {
    Boolean isStanding();

    void setStanding(Boolean value);

    void unsetStanding();

    void resetToDefault();

    void clearAll();
}
