package net.blitzcube.peapi.entity.fireball;

import net.blitzcube.peapi.api.entity.fireball.IWitherSkullData;
import net.blitzcube.peapi.entity.FireballData;

public class WitherSkullData extends FireballData implements IWitherSkullData {
    public Boolean isInvulnerable() {
        return (Boolean) this.dataMap.get(6);
    }

    public void setInvulnerable(Boolean value) {
        if (value == null) {
            unsetInvulnerable();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(6, value);
        this.dataMap.put(6, value);
    }

    public void unsetInvulnerable() {
        unsetInvulnerable(false);
    }

    public void unsetInvulnerable(boolean restoreDefault) {
        if (restoreDefault) {
            setInvulnerable(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(6);
            this.dataMap.remove(6);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetInvulnerable();
    }
}
