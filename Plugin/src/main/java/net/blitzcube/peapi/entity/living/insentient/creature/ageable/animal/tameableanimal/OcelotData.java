package net.blitzcube.peapi.entity.living.insentient.creature.ageable.animal.tameableanimal;

import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.tameableanimal.IOcelotData;
import net.blitzcube.peapi.entity.living.insentient.creature.ageable.animal.TameableAnimalData;

public class OcelotData extends TameableAnimalData implements IOcelotData {
    public Integer getType() {
        return (Integer) this.dataMap.get(15);
    }

    public void setType(Integer value) {
        if (value == null) {
            unsetType();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(15, value);
        this.dataMap.put(15, value);
    }

    public void unsetType() {
        unsetType(false);
    }

    public void unsetType(boolean restoreDefault) {
        if (restoreDefault) {
            setType(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(15);
            this.dataMap.remove(15);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetType();
    }
}
