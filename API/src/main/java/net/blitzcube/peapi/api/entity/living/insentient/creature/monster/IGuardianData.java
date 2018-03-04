package net.blitzcube.peapi.api.entity.living.insentient.creature.monster;

import net.blitzcube.peapi.api.entity.living.insentient.creature.IMonsterData;

public interface IGuardianData extends IMonsterData {
    int GUARDIAN_SOUND_STATUS = 21;

    Boolean isSpikesRetracting();

    Integer getTargetID();

    void setTargetID(Integer value);

    void setSpikesRetracting(Boolean value);

    void unsetSpikesRetracting();

    void unsetTargetID();

    void resetToDefault();

    void clearAll();
}
