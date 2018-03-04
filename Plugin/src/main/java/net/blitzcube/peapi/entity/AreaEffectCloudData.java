package net.blitzcube.peapi.entity;

import net.blitzcube.peapi.api.entity.IAreaEffectCloudData;

public class AreaEffectCloudData extends EntityData implements IAreaEffectCloudData {
    public Float getRadius() {
        return (Float) this.dataMap.get(6);
    }

    public void setRadius(Float value) {
        if (value == null) {
            unsetRadius();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(6, value);
        this.dataMap.put(6, value);
    }

    public Integer getColor() {
        return (Integer) this.dataMap.get(7);
    }

    public void setColor(Integer value) {
        if (value == null) {
            unsetColor();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(7, value);
        this.dataMap.put(7, value);
    }

    public Boolean isShowAsPoint() {
        return (Boolean) this.dataMap.get(8);
    }

    public Integer getParticleID() {
        return (Integer) this.dataMap.get(9);
    }

    public void setParticleID(Integer value) {
        if (value == null) {
            unsetParticleID();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(9, value);
        this.dataMap.put(9, value);
    }

    public Integer getParticleOption1() {
        return (Integer) this.dataMap.get(10);
    }

    public void setParticleOption1(Integer value) {
        if (value == null) {
            unsetParticleOption1();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(10, value);
        this.dataMap.put(10, value);
    }

    public Integer getParticleOption2() {
        return (Integer) this.dataMap.get(11);
    }

    public void setParticleOption2(Integer value) {
        if (value == null) {
            unsetParticleOption2();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(11, value);
        this.dataMap.put(11, value);
    }

    public void setShowAsPoint(Boolean value) {
        if (value == null) {
            unsetShowAsPoint();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(8, value);
        this.dataMap.put(8, value);
    }

    public void unsetRadius() {
        unsetRadius(false);
    }

    public void unsetRadius(boolean restoreDefault) {
        if (restoreDefault) {
            setRadius(0.5f);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(6);
            this.dataMap.remove(6);
        }
    }

    public void unsetColor() {
        unsetColor(false);
    }

    public void unsetColor(boolean restoreDefault) {
        if (restoreDefault) {
            setColor(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(7);
            this.dataMap.remove(7);
        }
    }

    public void unsetShowAsPoint() {
        unsetShowAsPoint(false);
    }

    public void unsetShowAsPoint(boolean restoreDefault) {
        if (restoreDefault) {
            setShowAsPoint(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(8);
            this.dataMap.remove(8);
        }
    }

    public void unsetParticleID() {
        unsetParticleID(false);
    }

    public void unsetParticleID(boolean restoreDefault) {
        if (restoreDefault) {
            setParticleID(15);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(9);
            this.dataMap.remove(9);
        }
    }

    public void unsetParticleOption1() {
        unsetParticleOption1(false);
    }

    public void unsetParticleOption1(boolean restoreDefault) {
        if (restoreDefault) {
            setParticleOption1(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(10);
            this.dataMap.remove(10);
        }
    }

    public void unsetParticleOption2() {
        unsetParticleOption2(false);
    }

    public void unsetParticleOption2(boolean restoreDefault) {
        if (restoreDefault) {
            setParticleOption2(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(11);
            this.dataMap.remove(11);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetRadius();
        unsetColor();
        unsetShowAsPoint();
        unsetParticleID();
        unsetParticleOption1();
        unsetParticleOption2();
    }
}
