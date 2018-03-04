package net.blitzcube.peapi.entity.living.insentient.creature.ageable.animal;

import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.ISheepData;
import net.blitzcube.peapi.entity.living.insentient.creature.ageable.AnimalData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Sheep;

public class SheepData extends AnimalData implements ISheepData {
    public Boolean isSheared() {
        return (((Byte) super.dataMap.get(13)) & 0x10) > 0;
    }

    public Boolean isColor() {
        return (((Byte) super.dataMap.get(13)) & 0xf) > 0;
    }

    public void setSheared(boolean value) {
        setBitmask(13, (byte) 0x10, value);
    }

    public void setColor(boolean value) {
        //TODO
        Byte bVal = (Byte) super.dataMap.get(13);
        if (bVal == null) bVal = 0x0;
        Byte newVal = (byte) ((bVal & 0xfffffff0) | (value ? 0xf : 0x00));
        if (this.dataWatcher != null) this.dataWatcher.setObject(13, newVal);
        this.dataMap.put(13, value);
    }

    public void unsetSheared(Entity e) {
        unsetBitmask(13, (byte) 0x10, e != null && e instanceof Sheep && ((Sheep) e).isSheared());
    }

    public void unsetColor(Entity e) {
        //TODO
        Byte bVal = (Byte) super.dataMap.get(13);
        if (bVal == null) return;
        boolean b = e != null && e instanceof Sheep && (16 - ((Sheep) e).getColor().ordinal()) != 0;
        Byte newVal = (byte) ((bVal & 0xfffffff0) | (b ? 0xf : 0x00));
        if (this.dataWatcher != null) this.dataWatcher.setObject(13, newVal);
        this.dataMap.put(13, newVal);
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetSheared(null);
        unsetColor(null);
    }
}
