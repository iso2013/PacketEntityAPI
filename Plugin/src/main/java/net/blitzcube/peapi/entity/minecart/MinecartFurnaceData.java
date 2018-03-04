package net.blitzcube.peapi.entity.minecart;

import net.blitzcube.peapi.api.entity.minecart.IMinecartFurnaceData;
import net.blitzcube.peapi.entity.MinecartData;

public class MinecartFurnaceData extends MinecartData implements IMinecartFurnaceData {
    public Boolean isPowered() {
        return (Boolean) this.dataMap.get(12);
    }

    public void setPowered(Boolean value) {
        if (value == null) {
            unsetPowered();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(12, value);
        this.dataMap.put(12, value);
    }

    public void unsetPowered() {
        unsetPowered(false);
    }

    public void unsetPowered(boolean restoreDefault) {
        if (restoreDefault) {
            setPowered(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(12);
            this.dataMap.remove(12);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetPowered();
    }
}
