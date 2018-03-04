package net.blitzcube.peapi.entity.living.insentient.creature.ageable.animal;

import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.IPigData;
import net.blitzcube.peapi.entity.living.insentient.creature.ageable.AnimalData;

public class PigData extends AnimalData implements IPigData {
    public Boolean isSaddled() {
        return (Boolean) this.dataMap.get(13);
    }

    public Integer getBoostTime() {
        return (Integer) this.dataMap.get(14);
    }

    public void setBoostTime(Integer value) {
        if (value == null) {
            unsetBoostTime();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(14, value);
        this.dataMap.put(14, value);
    }

    public void setSaddled(Boolean value) {
        if (value == null) {
            unsetSaddled();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(13, value);
        this.dataMap.put(13, value);
    }

    public void unsetSaddled() {
        unsetSaddled(false);
    }

    public void unsetSaddled(boolean restoreDefault) {
        if (restoreDefault) {
            setSaddled(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(13);
            this.dataMap.remove(13);
        }
    }

    public void unsetBoostTime() {
        unsetBoostTime(false);
    }

    public void unsetBoostTime(boolean restoreDefault) {
        if (restoreDefault) {
            setBoostTime(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(14);
            this.dataMap.remove(14);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetSaddled();
        unsetBoostTime();
    }
}
