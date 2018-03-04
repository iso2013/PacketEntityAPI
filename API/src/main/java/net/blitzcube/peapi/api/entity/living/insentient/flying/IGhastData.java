package net.blitzcube.peapi.api.entity.living.insentient.flying;

import net.blitzcube.peapi.api.entity.living.insentient.IFlyingData;

public interface IGhastData extends IFlyingData {
    Boolean isAttacking();

    void setAttacking(Boolean value);

    void unsetAttacking();

    void resetToDefault();

    void clearAll();
}
