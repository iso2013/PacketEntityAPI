package net.blitzcube.peapi.entity;

import net.blitzcube.peapi.api.entity.IFishingHookData;

public class FishingHookData extends EntityData implements IFishingHookData {
    public Integer getHookedID() {
        return (Integer) this.dataMap.get(6);
    }

    public void setHookedID(Integer value) {
        if (value == null) {
            unsetHookedID();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(6, value);
        this.dataMap.put(6, value);
    }

    public void unsetHookedID() {
        unsetHookedID(false);
    }

    public void unsetHookedID(boolean restoreDefault) {
        if (restoreDefault) {
            setHookedID(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(6);
            this.dataMap.remove(6);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetHookedID();
    }
}
