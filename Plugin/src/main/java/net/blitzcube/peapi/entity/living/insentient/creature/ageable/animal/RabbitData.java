package net.blitzcube.peapi.entity.living.insentient.creature.ageable.animal;

import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.IRabbitData;
import net.blitzcube.peapi.entity.living.insentient.creature.ageable.AnimalData;

public class RabbitData extends AnimalData implements IRabbitData {
    public Integer getType() {
        return (Integer) this.dataMap.get(13);
    }

    public void setType(Integer value) {
        if (value == null) {
            unsetType();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(13, value);
        this.dataMap.put(13, value);
    }

    public void unsetType() {
        unsetType(false);
    }

    public void unsetType(boolean restoreDefault) {
        if (restoreDefault) {
            setType(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(13);
            this.dataMap.remove(13);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetType();
    }
}
