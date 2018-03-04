package net.blitzcube.peapi.api.entity.living.insentient;

import net.blitzcube.peapi.api.entity.living.IInsentientData;

public interface ISlimeData extends IInsentientData {
    Integer getSize();

    void setSize(Integer value);

    void unsetSize();

    void resetToDefault();

    void clearAll();
}
