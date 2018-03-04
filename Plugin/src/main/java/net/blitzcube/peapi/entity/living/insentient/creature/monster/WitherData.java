package net.blitzcube.peapi.entity.living.insentient.creature.monster;

import net.blitzcube.peapi.api.entity.living.insentient.creature.monster.IWitherData;
import net.blitzcube.peapi.entity.living.insentient.creature.MonsterData;

public class WitherData extends MonsterData implements IWitherData {
    public Integer getCenterHeadTarget() {
        return (Integer) this.dataMap.get(12);
    }

    public void setCenterHeadTarget(Integer value) {
        if (value == null) {
            unsetCenterHeadTarget();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(12, value);
        this.dataMap.put(12, value);
    }

    public Integer getLeftHeadTarget() {
        return (Integer) this.dataMap.get(3);
    }

    public void setLeftHeadTarget(Integer value) {
        if (value == null) {
            unsetLeftHeadTarget();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(3, value);
        this.dataMap.put(3, value);
    }

    public Integer getRightHeadTarget() {
        return (Integer) this.dataMap.get(14);
    }

    public void setRightHeadTarget(Integer value) {
        if (value == null) {
            unsetRightHeadTarget();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(14, value);
        this.dataMap.put(14, value);
    }

    public Integer getInvulnerableTime() {
        return (Integer) this.dataMap.get(15);
    }

    public void setInvulnerableTime(Integer value) {
        if (value == null) {
            unsetInvulnerableTime();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(15, value);
        this.dataMap.put(15, value);
    }

    public void unsetCenterHeadTarget() {
        unsetCenterHeadTarget(false);
    }

    public void unsetCenterHeadTarget(boolean restoreDefault) {
        if (restoreDefault) {
            setCenterHeadTarget(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(12);
            this.dataMap.remove(12);
        }
    }

    public void unsetLeftHeadTarget() {
        unsetLeftHeadTarget(false);
    }

    public void unsetLeftHeadTarget(boolean restoreDefault) {
        if (restoreDefault) {
            setLeftHeadTarget(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(3);
            this.dataMap.remove(3);
        }
    }

    public void unsetRightHeadTarget() {
        unsetRightHeadTarget(false);
    }

    public void unsetRightHeadTarget(boolean restoreDefault) {
        if (restoreDefault) {
            setRightHeadTarget(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(14);
            this.dataMap.remove(14);
        }
    }

    public void unsetInvulnerableTime() {
        unsetInvulnerableTime(false);
    }

    public void unsetInvulnerableTime(boolean restoreDefault) {
        if (restoreDefault) {
            setInvulnerableTime(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(15);
            this.dataMap.remove(15);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetCenterHeadTarget();
        unsetLeftHeadTarget();
        unsetRightHeadTarget();
        unsetInvulnerableTime();
    }
}
