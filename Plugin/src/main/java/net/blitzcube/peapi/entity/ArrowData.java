package net.blitzcube.peapi.entity;

import net.blitzcube.peapi.api.entity.IArrowData;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;

public class ArrowData extends EntityData implements IArrowData {
    public Boolean isCritical() {
        return (((Byte) super.dataMap.get(6)) & 0x1) > 0;
    }

    public void setCritical(boolean value) {
        setBitmask(6, (byte) 0x01, value);
    }

    public void unsetCritical(Entity e) {
        unsetBitmask(6, (byte) 0x01, e != null && e instanceof Arrow && ((Arrow) e).isCritical());
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetCritical(null);
    }
}
