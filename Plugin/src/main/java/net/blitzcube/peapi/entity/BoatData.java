package net.blitzcube.peapi.entity;

import net.blitzcube.peapi.api.entity.IBoatData;

public class BoatData extends EntityData implements IBoatData {
    public Integer getTimeSinceLastHit() {
        return (Integer) this.dataMap.get(6);
    }

    public void setTimeSinceLastHit(Integer value) {
        if (value == null) {
            unsetTimeSinceLastHit();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(6, value);
        this.dataMap.put(6, value);
    }

    public Integer getForwardDirection() {
        return (Integer) this.dataMap.get(7);
    }

    public void setForwardDirection(Integer value) {
        if (value == null) {
            unsetForwardDirection();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(7, value);
        this.dataMap.put(7, value);
    }

    public Float getDamageTaken() {
        return (Float) this.dataMap.get(8);
    }

    public void setDamageTaken(Float value) {
        if (value == null) {
            unsetDamageTaken();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(8, value);
        this.dataMap.put(8, value);
    }

    public Integer getType() {
        return (Integer) this.dataMap.get(9);
    }

    public void setType(Integer value) {
        if (value == null) {
            unsetType();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(9, value);
        this.dataMap.put(9, value);
    }

    public Boolean isRightPaddleTurning() {
        return (Boolean) this.dataMap.get(10);
    }

    public Boolean isLeftPaddleTurning() {
        return (Boolean) this.dataMap.get(11);
    }

    public void setRightPaddleTurning(Boolean value) {
        if (value == null) {
            unsetRightPaddleTurning();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(10, value);
        this.dataMap.put(10, value);
    }

    public void setLeftPaddleTurning(Boolean value) {
        if (value == null) {
            unsetLeftPaddleTurning();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(11, value);
        this.dataMap.put(11, value);
    }

    public void unsetTimeSinceLastHit() {
        unsetTimeSinceLastHit(false);
    }

    public void unsetTimeSinceLastHit(boolean restoreDefault) {
        if (restoreDefault) {
            setTimeSinceLastHit(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(6);
            this.dataMap.remove(6);
        }
    }

    public void unsetForwardDirection() {
        unsetForwardDirection(false);
    }

    public void unsetForwardDirection(boolean restoreDefault) {
        if (restoreDefault) {
            setForwardDirection(1);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(7);
            this.dataMap.remove(7);
        }
    }

    public void unsetDamageTaken() {
        unsetDamageTaken(false);
    }

    public void unsetDamageTaken(boolean restoreDefault) {
        if (restoreDefault) {
            setDamageTaken(0.0f);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(8);
            this.dataMap.remove(8);
        }
    }

    public void unsetType() {
        unsetType(false);
    }

    public void unsetType(boolean restoreDefault) {
        if (restoreDefault) {
            setType(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(9);
            this.dataMap.remove(9);
        }
    }

    public void unsetRightPaddleTurning() {
        unsetRightPaddleTurning(false);
    }

    public void unsetRightPaddleTurning(boolean restoreDefault) {
        if (restoreDefault) {
            setRightPaddleTurning(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(10);
            this.dataMap.remove(10);
        }
    }

    public void unsetLeftPaddleTurning() {
        unsetLeftPaddleTurning(false);
    }

    public void unsetLeftPaddleTurning(boolean restoreDefault) {
        if (restoreDefault) {
            setLeftPaddleTurning(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(11);
            this.dataMap.remove(11);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetTimeSinceLastHit();
        unsetForwardDirection();
        unsetDamageTaken();
        unsetType();
        unsetRightPaddleTurning();
        unsetLeftPaddleTurning();
    }
}
