package net.blitzcube.peapi.entity.living.insentient.creature.ageable.animal.tameableanimal;

import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.tameableanimal.IWolfData;
import net.blitzcube.peapi.entity.living.insentient.creature.ageable.animal.TameableAnimalData;

public class WolfData extends TameableAnimalData implements IWolfData {
    public Float getDamageTaken() {
        return (Float) this.dataMap.get(15);
    }

    public void setDamageTaken(Float value) {
        if (value == null) {
            unsetDamageTaken();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(15, value);
        this.dataMap.put(15, value);
    }

    public Boolean isBegging() {
        return (Boolean) this.dataMap.get(16);
    }

    public Integer getCollarColor() {
        return (Integer) this.dataMap.get(17);
    }

    public void setCollarColor(Integer value) {
        if (value == null) {
            unsetCollarColor();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(17, value);
        this.dataMap.put(17, value);
    }

    public void setBegging(Boolean value) {
        if (value == null) {
            unsetBegging();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(16, value);
        this.dataMap.put(16, value);
    }

    public void unsetDamageTaken() {
        unsetDamageTaken(false);
    }

    public void unsetDamageTaken(boolean restoreDefault) {
        if (restoreDefault) {
            setDamageTaken(1.0f);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(15);
            this.dataMap.remove(15);
        }
    }

    public void unsetBegging() {
        unsetBegging(false);
    }

    public void unsetBegging(boolean restoreDefault) {
        if (restoreDefault) {
            setBegging(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(16);
            this.dataMap.remove(16);
        }
    }

    public void unsetCollarColor() {
        unsetCollarColor(false);
    }

    public void unsetCollarColor(boolean restoreDefault) {
        if (restoreDefault) {
            setCollarColor(14);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(17);
            this.dataMap.remove(17);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetDamageTaken();
        unsetBegging();
        unsetCollarColor();
    }
}
