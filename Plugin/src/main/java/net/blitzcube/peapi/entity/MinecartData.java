package net.blitzcube.peapi.entity;

import net.blitzcube.peapi.api.entity.IMinecartData;

public class MinecartData extends EntityData implements IMinecartData {
    public Integer getShakingPower() {
        return (Integer) this.dataMap.get(6);
    }

    public void setShakingPower(Integer value) {
        if (value == null) {
            unsetShakingPower();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(6, value);
        this.dataMap.put(6, value);
    }

    public Integer getShakingDirection() {
        return (Integer) this.dataMap.get(7);
    }

    public void setShakingDirection(Integer value) {
        if (value == null) {
            unsetShakingDirection();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(7, value);
        this.dataMap.put(7, value);
    }

    public Float getShakingMultiplier() {
        return (Float) this.dataMap.get(8);
    }

    public void setShakingMultiplier(Float value) {
        if (value == null) {
            unsetShakingMultiplier();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(8, value);
        this.dataMap.put(8, value);
    }

    public Integer getCustomBlockID() {
        return (Integer) this.dataMap.get(9);
    }

    public void setCustomBlockID(Integer value) {
        if (value == null) {
            unsetCustomBlockID();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(9, value);
        this.dataMap.put(9, value);
    }

    public Integer getCustomBlockY() {
        return (Integer) this.dataMap.get(10);
    }

    public void setCustomBlockY(Integer value) {
        if (value == null) {
            unsetCustomBlockY();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(10, value);
        this.dataMap.put(10, value);
    }

    public Boolean isCustomBlockShown() {
        return (Boolean) this.dataMap.get(11);
    }

    public void setCustomBlockShown(Boolean value) {
        if (value == null) {
            unsetCustomBlockShown();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(11, value);
        this.dataMap.put(11, value);
    }

    public void unsetShakingPower() {
        unsetShakingPower(false);
    }

    public void unsetShakingPower(boolean restoreDefault) {
        if (restoreDefault) {
            setShakingPower(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(6);
            this.dataMap.remove(6);
        }
    }

    public void unsetShakingDirection() {
        unsetShakingDirection(false);
    }

    public void unsetShakingDirection(boolean restoreDefault) {
        if (restoreDefault) {
            setShakingDirection(1);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(7);
            this.dataMap.remove(7);
        }
    }

    public void unsetShakingMultiplier() {
        unsetShakingMultiplier(false);
    }

    public void unsetShakingMultiplier(boolean restoreDefault) {
        if (restoreDefault) {
            setShakingMultiplier(0.0f);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(8);
            this.dataMap.remove(8);
        }
    }

    public void unsetCustomBlockID() {
        unsetCustomBlockID(false);
    }

    public void unsetCustomBlockID(boolean restoreDefault) {
        if (restoreDefault) {
            setCustomBlockID(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(9);
            this.dataMap.remove(9);
        }
    }

    public void unsetCustomBlockY() {
        unsetCustomBlockY(false);
    }

    public void unsetCustomBlockY(boolean restoreDefault) {
        if (restoreDefault) {
            setCustomBlockY(6);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(10);
            this.dataMap.remove(10);
        }
    }

    public void unsetCustomBlockShown() {
        unsetCustomBlockShown(false);
    }

    public void unsetCustomBlockShown(boolean restoreDefault) {
        if (restoreDefault) {
            setCustomBlockShown(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(11);
            this.dataMap.remove(11);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetShakingPower();
        unsetShakingDirection();
        unsetShakingMultiplier();
        unsetCustomBlockID();
        unsetCustomBlockY();
        unsetCustomBlockShown();
    }
}
