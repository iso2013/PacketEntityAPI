package net.blitzcube.peapi.entity.living.insentient.creature.monster;

import net.blitzcube.peapi.api.entity.living.insentient.creature.monster.ICreeperData;
import net.blitzcube.peapi.entity.living.insentient.creature.MonsterData;

public class CreeperData extends MonsterData implements ICreeperData {
    public Integer getState() {
        return (Integer) this.dataMap.get(12);
    }

    public void setState(Integer value) {
        if (value == null) {
            unsetState();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(12, value);
        this.dataMap.put(12, value);
    }

    public Boolean isCharged() {
        return (Boolean) this.dataMap.get(13);
    }

    public Boolean isIgnited() {
        return (Boolean) this.dataMap.get(14);
    }

    public void setCharged(Boolean value) {
        if (value == null) {
            unsetCharged();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(13, value);
        this.dataMap.put(13, value);
    }

    public void setIgnited(Boolean value) {
        if (value == null) {
            unsetIgnited();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(14, value);
        this.dataMap.put(14, value);
    }

    public void unsetState() {
        unsetState(false);
    }

    public void unsetState(boolean restoreDefault) {
        if (restoreDefault) {
            setState(-1);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(12);
            this.dataMap.remove(12);
        }
    }

    public void unsetCharged() {
        unsetCharged(false);
    }

    public void unsetCharged(boolean restoreDefault) {
        if (restoreDefault) {
            setCharged(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(13);
            this.dataMap.remove(13);
        }
    }

    public void unsetIgnited() {
        unsetIgnited(false);
    }

    public void unsetIgnited(boolean restoreDefault) {
        if (restoreDefault) {
            setIgnited(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(14);
            this.dataMap.remove(14);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetState();
        unsetCharged();
        unsetIgnited();
    }
}
