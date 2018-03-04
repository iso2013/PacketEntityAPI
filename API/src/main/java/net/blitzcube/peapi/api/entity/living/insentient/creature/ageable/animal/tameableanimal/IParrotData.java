package net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.tameableanimal;

import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.ITameableAnimalData;

public interface IParrotData extends ITameableAnimalData {
    Integer getVariant();

    void setVariant(Integer value);

    void unsetVariant();

    void resetToDefault();

    void clearAll();
}
