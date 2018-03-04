package net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal;

import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.IAnimalData;

public interface IRabbitData extends IAnimalData {
    Integer getType();

    void setType(Integer value);

    void unsetType();

    void resetToDefault();

    void clearAll();
}
