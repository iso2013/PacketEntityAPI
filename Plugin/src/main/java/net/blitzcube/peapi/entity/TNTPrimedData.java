package net.blitzcube.peapi.entity;

import net.blitzcube.peapi.api.entity.ITNTPrimedData;

public class TNTPrimedData extends EntityData implements ITNTPrimedData {
    public Integer getFuseTime() {
        return (Integer) this.dataMap.get(6);
    }

    public void setFuseTime(Integer value) {
        if (value == null) {
            unsetFuseTime();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(6, value);
        this.dataMap.put(6, value);
    }

    public void unsetFuseTime() {
        unsetFuseTime(false);
    }

    public void unsetFuseTime(boolean restoreDefault) {
        if (restoreDefault) {
            setFuseTime(80);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(6);
            this.dataMap.remove(6);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetFuseTime();
    }
}
