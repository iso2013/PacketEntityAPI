package net.blitzcube.peapi.api.entity.living.insentient.creature.monster;

import net.blitzcube.peapi.api.entity.living.insentient.creature.IMonsterData;

public interface IAbstractSkeletonData extends IMonsterData {
    Boolean isSwingingArms();

    void setSwingingArms(Boolean value);

    void unsetSwingingArms();

    void resetToDefault();

    void clearAll();
}
