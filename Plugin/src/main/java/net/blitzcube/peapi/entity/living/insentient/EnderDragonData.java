package net.blitzcube.peapi.entity.living.insentient;

import net.blitzcube.peapi.api.entity.living.insentient.IEnderDragonData;
import net.blitzcube.peapi.entity.living.InsentientData;

public class EnderDragonData extends InsentientData implements IEnderDragonData {
    public Integer getDragonPhase() {
        return (Integer) this.dataMap.get(12);
    }

    public void setDragonPhase(Integer value) {
        if (value == null) {
            unsetDragonPhase();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(12, value);
        this.dataMap.put(12, value);
    }

    public void unsetDragonPhase() {
        unsetDragonPhase(false);
    }

    public void unsetDragonPhase(boolean restoreDefault) {
        if (restoreDefault) {
            setDragonPhase(10);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(12);
            this.dataMap.remove(12);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetDragonPhase();
    }
}
