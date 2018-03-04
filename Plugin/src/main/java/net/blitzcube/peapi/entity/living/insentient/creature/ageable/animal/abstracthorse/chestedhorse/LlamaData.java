package net.blitzcube.peapi.entity.living.insentient.creature.ageable.animal.abstracthorse.chestedhorse;

import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.abstracthorse.chestedhorse.ILlamaData;
import net.blitzcube.peapi.entity.living.insentient.creature.ageable.animal.abstracthorse.ChestedHorseData;

public class LlamaData extends ChestedHorseData implements ILlamaData {
    public Integer getStrength() {
        return (Integer) this.dataMap.get(16);
    }

    public void setStrength(Integer value) {
        if (value == null) {
            unsetStrength();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(16, value);
        this.dataMap.put(16, value);
    }

    public Integer getCarpetColor() {
        return (Integer) this.dataMap.get(17);
    }

    public void setCarpetColor(Integer value) {
        if (value == null) {
            unsetCarpetColor();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(17, value);
        this.dataMap.put(17, value);
    }

    public Integer getVariant() {
        return (Integer) this.dataMap.get(18);
    }

    public void setVariant(Integer value) {
        if (value == null) {
            unsetVariant();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(18, value);
        this.dataMap.put(18, value);
    }

    public void unsetStrength() {
        unsetStrength(false);
    }

    public void unsetStrength(boolean restoreDefault) {
        if (restoreDefault) {
            setStrength(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(16);
            this.dataMap.remove(16);
        }
    }

    public void unsetCarpetColor() {
        unsetCarpetColor(false);
    }

    public void unsetCarpetColor(boolean restoreDefault) {
        if (restoreDefault) {
            setCarpetColor(-1);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(17);
            this.dataMap.remove(17);
        }
    }

    public void unsetVariant() {
        unsetVariant(false);
    }

    public void unsetVariant(boolean restoreDefault) {
        if (restoreDefault) {
            setVariant(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(18);
            this.dataMap.remove(18);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetStrength();
        unsetCarpetColor();
        unsetVariant();
    }
}
