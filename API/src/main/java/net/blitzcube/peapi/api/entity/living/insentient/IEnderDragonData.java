package net.blitzcube.peapi.api.entity.living.insentient;

import net.blitzcube.peapi.api.entity.living.IInsentientData;

public interface IEnderDragonData extends IInsentientData {
    Integer getDragonPhase();

    void setDragonPhase(Integer value);

    void unsetDragonPhase();

    void resetToDefault();

    void clearAll();
}
