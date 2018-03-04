package net.blitzcube.peapi.entity.living.insentient.creature.ageable.animal.tameableanimal;

import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.tameableanimal.IParrotData;
import net.blitzcube.peapi.entity.living.insentient.creature.ageable.animal.TameableAnimalData;

public class ParrotData extends TameableAnimalData implements IParrotData {
    public Integer getVariant() {
        return (Integer) this.dataMap.get(15);
    }

    public void setVariant(Integer value) {
        if (value == null) {
            unsetVariant();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(15, value);
        this.dataMap.put(15, value);
    }

    public void unsetVariant() {
        unsetVariant(false);
    }

    public void unsetVariant(boolean restoreDefault) {
        if (restoreDefault) {
            setVariant(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(15);
            this.dataMap.remove(15);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetVariant();
    }
}
