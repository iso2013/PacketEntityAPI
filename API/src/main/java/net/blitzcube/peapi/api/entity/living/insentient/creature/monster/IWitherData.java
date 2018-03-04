package net.blitzcube.peapi.api.entity.living.insentient.creature.monster;

import net.blitzcube.peapi.api.entity.living.insentient.creature.IMonsterData;

public interface IWitherData extends IMonsterData {
    Integer getCenterHeadTarget();

    void setCenterHeadTarget(Integer value);

    Integer getLeftHeadTarget();

    void setLeftHeadTarget(Integer value);

    Integer getRightHeadTarget();

    void setRightHeadTarget(Integer value);

    Integer getInvulnerableTime();

    void setInvulnerableTime(Integer value);

    void unsetCenterHeadTarget();

    void unsetLeftHeadTarget();

    void unsetRightHeadTarget();

    void unsetInvulnerableTime();

    void resetToDefault();

    void clearAll();
}
