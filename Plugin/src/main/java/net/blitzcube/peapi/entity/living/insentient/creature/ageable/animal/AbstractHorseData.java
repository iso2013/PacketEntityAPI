package net.blitzcube.peapi.entity.living.insentient.creature.ageable.animal;

import com.google.common.base.Optional;
import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.IAbstractHorseData;
import net.blitzcube.peapi.entity.living.insentient.creature.ageable.AnimalData;
import org.bukkit.Material;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class AbstractHorseData extends AnimalData implements IAbstractHorseData {
    public Boolean isEating() {
        return (((Byte) super.dataMap.get(13)) & 0x10) > 0;
    }

    public Boolean isRearing() {
        return (((Byte) super.dataMap.get(13)) & 0x20) > 0;
    }

    public Boolean isMouthOpen() {
        return (((Byte) super.dataMap.get(13)) & 0x40) > 0;
    }

    public Boolean isTame() {
        return (((Byte) super.dataMap.get(13)) & 0x2) > 0;
    }

    public Boolean isSaddled() {
        return (((Byte) super.dataMap.get(13)) & 0x4) > 0;
    }

    public Boolean isBred() {
        return (((Byte) super.dataMap.get(13)) & 0x8) > 0;
    }

    public Optional<UUID> getOwner() {
        return (Optional<UUID>) this.dataMap.get(14);
    }

    public void setOwner(Optional<UUID> value) {
        if (value == null) {
            unsetOwner();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(14, value);
        this.dataMap.put(14, value);
    }

    public void setEating(boolean value) {
        setBitmask(13, (byte) 0x10, value);
    }

    public void setRearing(boolean value) {
        setBitmask(13, (byte) 0x20, value);
    }

    public void setMouthOpen(boolean value) {
        setBitmask(13, (byte) 0x40, value);
    }

    public void setTame(boolean value) {
        setBitmask(13, (byte) 0x02, value);
    }

    public void setSaddled(boolean value) {
        setBitmask(13, (byte) 0x04, value);
    }

    public void setBred(boolean value) {
        setBitmask(13, (byte) 0x08, value);
    }

    public void unsetEating(Entity e) {
        unsetBitmask(13, (byte) 0x10, false);
    }

    public void unsetRearing(Entity e) {
        unsetBitmask(13, (byte) 0x20, false);
    }

    public void unsetMouthOpen(Entity e) {
        unsetBitmask(13, (byte) 0x40, false);
    }

    public void unsetTame(Entity e) {
        unsetBitmask(13, (byte) 0x02, e != null && e instanceof AbstractHorse && ((AbstractHorse) e).isTamed());
    }

    public void unsetSaddled(Entity e) {
        unsetBitmask(13, (byte) 0x04, e != null && e instanceof AbstractHorse && ((AbstractHorse) e)
                .getInventory().getSaddle().getType() == Material.SADDLE);
    }

    public void unsetBred(Entity e) {
        unsetBitmask(13, (byte) 0x08, false);
    }

    public void unsetOwner() {
        unsetOwner(false);
    }

    public void unsetOwner(boolean restoreDefault) {
        if (restoreDefault) {
            setOwner(Optional.absent());
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(14);
            this.dataMap.remove(14);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetEating(null);
        unsetRearing(null);
        unsetMouthOpen(null);
        unsetTame(null);
        unsetSaddled(null);
        unsetBred(null);
        unsetOwner();
    }
}
