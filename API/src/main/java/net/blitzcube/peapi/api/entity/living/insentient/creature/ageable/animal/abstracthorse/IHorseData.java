package net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.abstracthorse;

import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.IAbstractHorseData;

public interface IHorseData extends IAbstractHorseData {
    Integer getVariant();

    void setVariant(Integer value);

    Integer getArmorType();

    void setArmorType(Integer value);

    void unsetVariant();

    void unsetArmorType();

    void resetToDefault();

    void clearAll();
}
