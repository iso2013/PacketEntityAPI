package net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal;

import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.IAnimalData;

public interface IPigData extends IAnimalData {
    Boolean isSaddled();

    Integer getBoostTime();

    void setBoostTime(Integer value);

    void setSaddled(Boolean value);

    void unsetSaddled();

    void unsetBoostTime();

    void resetToDefault();

    void clearAll();
}
