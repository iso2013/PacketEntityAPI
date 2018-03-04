package net.blitzcube.peapi.entity.living.insentient.flying;

import net.blitzcube.peapi.api.entity.living.insentient.flying.IGhastData;
import net.blitzcube.peapi.entity.living.insentient.FlyingData;

public class GhastData extends FlyingData implements IGhastData {
    public Boolean isAttacking() {
        return (Boolean) this.dataMap.get(12);
    }

    public void setAttacking(Boolean value) {
        if (value == null) {
            unsetAttacking();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(12, value);
        this.dataMap.put(12, value);
    }

    public void unsetAttacking() {
        unsetAttacking(false);
    }

    public void unsetAttacking(boolean restoreDefault) {
        if (restoreDefault) {
            setAttacking(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(12);
            this.dataMap.remove(12);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetAttacking();
    }
}
