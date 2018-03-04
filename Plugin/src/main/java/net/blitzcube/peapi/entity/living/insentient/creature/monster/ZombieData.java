package net.blitzcube.peapi.entity.living.insentient.creature.monster;

import net.blitzcube.peapi.api.entity.living.insentient.creature.monster.IZombieData;
import net.blitzcube.peapi.entity.living.insentient.creature.MonsterData;

public class ZombieData extends MonsterData implements IZombieData {
    public Boolean isBaby() {
        return (Boolean) this.dataMap.get(12);
    }

    public Boolean isHandsUp() {
        return (Boolean) this.dataMap.get(14);
    }

    public void setBaby(Boolean value) {
        if (value == null) {
            unsetBaby();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(12, value);
        this.dataMap.put(12, value);
    }

    public void setHandsUp(Boolean value) {
        if (value == null) {
            unsetHandsUp();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(14, value);
        this.dataMap.put(14, value);
    }

    public void unsetBaby() {
        unsetBaby(false);
    }

    public void unsetBaby(boolean restoreDefault) {
        if (restoreDefault) {
            setBaby(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(12);
            this.dataMap.remove(12);
        }
    }

    public void unsetHandsUp() {
        unsetHandsUp(false);
    }

    public void unsetHandsUp(boolean restoreDefault) {
        if (restoreDefault) {
            setHandsUp(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(14);
            this.dataMap.remove(14);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetBaby();
        unsetHandsUp();
    }
}
