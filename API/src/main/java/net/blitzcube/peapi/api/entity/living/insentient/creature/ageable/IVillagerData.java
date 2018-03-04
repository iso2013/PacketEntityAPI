package net.blitzcube.peapi.api.entity.living.insentient.creature.ageable;

import net.blitzcube.peapi.api.entity.living.insentient.creature.IAgeableData;

public interface IVillagerData extends IAgeableData {
    Integer getProfession();

    void setProfession(Integer value);

    void unsetProfession();

    void resetToDefault();

    void clearAll();
}
