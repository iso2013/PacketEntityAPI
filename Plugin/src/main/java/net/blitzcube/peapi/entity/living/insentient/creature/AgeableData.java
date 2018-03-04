package net.blitzcube.peapi.entity.living.insentient.creature;

import net.blitzcube.peapi.api.entity.living.insentient.creature.IAgeableData;
import net.blitzcube.peapi.entity.living.insentient.CreatureData;

public class AgeableData extends CreatureData implements IAgeableData {
    public Boolean isBaby() {
        return (Boolean) this.dataMap.get(12);
    }

    public void setBaby(Boolean value) {
        if (value == null) {
            unsetBaby();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(12, value);
        this.dataMap.put(12, value);
    }

    public void unsetBaby() {
        unsetBaby(false);
    }

    public void unsetBaby(boolean restoreDefault) {
        if (restoreDefault) {
            setBaby(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(12);
            this.dataMap.remove(12);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetBaby();
    }
}
