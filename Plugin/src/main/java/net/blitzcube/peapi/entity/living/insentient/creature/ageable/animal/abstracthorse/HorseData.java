package net.blitzcube.peapi.entity.living.insentient.creature.ageable.animal.abstracthorse;

import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.abstracthorse.IHorseData;
import net.blitzcube.peapi.entity.living.insentient.creature.ageable.animal.AbstractHorseData;

public class HorseData extends AbstractHorseData implements IHorseData {
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

    public Integer getArmorType() {
        return (Integer) this.dataMap.get(16);
    }

    public void setArmorType(Integer value) {
        if (value == null) {
            unsetArmorType();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(16, value);
        this.dataMap.put(16, value);
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

    public void unsetArmorType() {
        unsetArmorType(false);
    }

    public void unsetArmorType(boolean restoreDefault) {
        if (restoreDefault) {
            setArmorType(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(16);
            this.dataMap.remove(16);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetVariant();
        unsetArmorType();
    }
}
