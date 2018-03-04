package net.blitzcube.peapi.entity.living.insentient;

import net.blitzcube.peapi.api.entity.living.insentient.ISlimeData;
import net.blitzcube.peapi.entity.living.InsentientData;

public class SlimeData extends InsentientData implements ISlimeData {
    public Integer getSize() {
        return (Integer) this.dataMap.get(12);
    }

    public void setSize(Integer value) {
        if (value == null) {
            unsetSize();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(12, value);
        this.dataMap.put(12, value);
    }

    public void unsetSize() {
        unsetSize(false);
    }

    public void unsetSize(boolean restoreDefault) {
        if (restoreDefault) {
            setSize(1);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(12);
            this.dataMap.remove(12);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetSize();
    }
}
