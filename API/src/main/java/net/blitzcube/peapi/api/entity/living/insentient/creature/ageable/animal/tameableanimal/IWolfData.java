package net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.tameableanimal;

import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.ITameableAnimalData;

public interface IWolfData extends ITameableAnimalData {
    Float getDamageTaken();

    void setDamageTaken(Float value);

    Boolean isBegging();

    Integer getCollarColor();

    void setCollarColor(Integer value);

    void setBegging(Boolean value);

    void unsetDamageTaken();

    void unsetBegging();

    void unsetCollarColor();

    void resetToDefault();

    void clearAll();
}
