package net.blitzcube.peapi.entity.living.insentient.creature.monster;

import net.blitzcube.peapi.api.entity.living.insentient.creature.monster.IAbstractSkeletonData;
import net.blitzcube.peapi.entity.living.insentient.creature.MonsterData;

public class AbstractSkeletonData extends MonsterData implements IAbstractSkeletonData {
    public Boolean isSwingingArms() {
        return (Boolean) this.dataMap.get(12);
    }

    public void setSwingingArms(Boolean value) {
        if (value == null) {
            unsetSwingingArms();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(12, value);
        this.dataMap.put(12, value);
    }

    public void unsetSwingingArms() {
        unsetSwingingArms(false);
    }

    public void unsetSwingingArms(boolean restoreDefault) {
        if (restoreDefault) {
            setSwingingArms(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(12);
            this.dataMap.remove(12);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetSwingingArms();
    }
}
