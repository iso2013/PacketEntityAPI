package net.blitzcube.peapi.entity.living.insentient.creature.ageable.animal.abstracthorse;

import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.abstracthorse.IChestedHorseData;
import net.blitzcube.peapi.entity.living.insentient.creature.ageable.animal.AbstractHorseData;

public class ChestedHorseData extends AbstractHorseData implements IChestedHorseData {
    public Boolean isChested() {
        return (Boolean) this.dataMap.get(15);
    }

    public void setChested(Boolean value) {
        if (value == null) {
            unsetChested();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(15, value);
        this.dataMap.put(15, value);
    }

    public void unsetChested() {
        unsetChested(false);
    }

    public void unsetChested(boolean restoreDefault) {
        if (restoreDefault) {
            setChested(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(15);
            this.dataMap.remove(15);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetChested();
    }
}
