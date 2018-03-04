package net.blitzcube.peapi.api.entity.living.insentient.creature.monster;

import net.blitzcube.peapi.api.entity.living.insentient.creature.IMonsterData;

public interface IZombieData extends IMonsterData {
    Boolean isBaby();

    Boolean isHandsUp();

    void setBaby(Boolean value);

    void setHandsUp(Boolean value);

    void unsetBaby();

    void unsetHandsUp();

    void resetToDefault();

    void clearAll();
}
