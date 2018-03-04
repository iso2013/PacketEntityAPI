package net.blitzcube.peapi.entity.living.insentient.creature.ageable.animal;

import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.IPolarBearData;
import net.blitzcube.peapi.entity.living.insentient.creature.ageable.AnimalData;

public class PolarBearData extends AnimalData implements IPolarBearData {
    public Boolean isStanding() {
        return (Boolean) this.dataMap.get(13);
    }

    public void setStanding(Boolean value) {
        if (value == null) {
            unsetStanding();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(13, value);
        this.dataMap.put(13, value);
    }

    public void unsetStanding() {
        unsetStanding(false);
    }

    public void unsetStanding(boolean restoreDefault) {
        if (restoreDefault) {
            setStanding(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(13);
            this.dataMap.remove(13);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetStanding();
    }
}
