package net.blitzcube.peapi.api.entity.living.insentient.creature.monster;

import net.blitzcube.peapi.api.entity.living.insentient.creature.IMonsterData;

public interface IWitchData extends IMonsterData {
    int WITCH_PARTICLE_STATUS = 15;

    Boolean isDrinkingPotion();

    void setDrinkingPotion(Boolean value);

    void unsetDrinkingPotion();

    void resetToDefault();

    void clearAll();
}
