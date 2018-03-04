package net.blitzcube.peapi.entity.arrow;

import net.blitzcube.peapi.api.entity.arrow.ITippedArrowData;
import net.blitzcube.peapi.entity.ArrowData;

public class TippedArrowData extends ArrowData implements ITippedArrowData {
    public Integer getColor() {
        return (Integer) this.dataMap.get(7);
    }

    public void setColor(Integer value) {
        if (value == null) {
            unsetColor();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(7, value);
        this.dataMap.put(7, value);
    }

    public void unsetColor() {
        unsetColor(false);
    }

    public void unsetColor(boolean restoreDefault) {
        if (restoreDefault) {
            setColor(-1);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(7);
            this.dataMap.remove(7);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetColor();
    }
}
