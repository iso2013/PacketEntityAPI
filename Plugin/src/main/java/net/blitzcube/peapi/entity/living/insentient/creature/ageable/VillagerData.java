package net.blitzcube.peapi.entity.living.insentient.creature.ageable;

import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.IVillagerData;
import net.blitzcube.peapi.entity.living.insentient.creature.AgeableData;

public class VillagerData extends AgeableData implements IVillagerData {
    public Integer getProfession() {
        return (Integer) this.dataMap.get(13);
    }

    public void setProfession(Integer value) {
        if (value == null) {
            unsetProfession();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(13, value);
        this.dataMap.put(13, value);
    }

    public void unsetProfession() {
        unsetProfession(false);
    }

    public void unsetProfession(boolean restoreDefault) {
        if (restoreDefault) {
            setProfession(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(13);
            this.dataMap.remove(13);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetProfession();
    }
}
