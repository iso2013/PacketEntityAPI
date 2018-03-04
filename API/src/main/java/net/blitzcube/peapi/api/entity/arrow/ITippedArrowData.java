package net.blitzcube.peapi.api.entity.arrow;

import net.blitzcube.peapi.api.entity.IArrowData;

public interface ITippedArrowData extends IArrowData {
    Integer getColor();

    void setColor(Integer value);

    void unsetColor();

    void resetToDefault();

    void clearAll();
}
