package net.blitzcube.peapi.entity.living.insentient.creature.monster;

import net.blitzcube.peapi.api.entity.living.insentient.creature.monster.IGuardianData;
import net.blitzcube.peapi.entity.living.insentient.creature.MonsterData;

public class GuardianData extends MonsterData implements IGuardianData {
    public Boolean isSpikesRetracting() {
        return (Boolean) this.dataMap.get(12);
    }

    public Integer getTargetID() {
        return (Integer) this.dataMap.get(13);
    }

    public void setTargetID(Integer value) {
        if (value == null) {
            unsetTargetID();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(13, value);
        this.dataMap.put(13, value);
    }

    public void setSpikesRetracting(Boolean value) {
        if (value == null) {
            unsetSpikesRetracting();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(12, value);
        this.dataMap.put(12, value);
    }

    public void unsetSpikesRetracting() {
        unsetSpikesRetracting(false);
    }

    public void unsetSpikesRetracting(boolean restoreDefault) {
        if (restoreDefault) {
            setSpikesRetracting(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(12);
            this.dataMap.remove(12);
        }
    }

    public void unsetTargetID() {
        unsetTargetID(false);
    }

    public void unsetTargetID(boolean restoreDefault) {
        if (restoreDefault) {
            setTargetID(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(13);
            this.dataMap.remove(13);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetSpikesRetracting();
        unsetTargetID();
    }
}
