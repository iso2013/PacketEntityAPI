package net.blitzcube.peapi.entity.living.insentient.creature.monster.zombie;

import net.blitzcube.peapi.api.entity.living.insentient.creature.monster.zombie.IZombieVillagerData;
import net.blitzcube.peapi.entity.living.insentient.creature.monster.ZombieData;

public class ZombieVillagerData extends ZombieData implements IZombieVillagerData {
    public Boolean isConverting() {
        return (Boolean) this.dataMap.get(15);
    }

    public Integer getProfession() {
        return (Integer) this.dataMap.get(16);
    }

    public void setProfession(Integer value) {
        if (value == null) {
            unsetProfession();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(16, value);
        this.dataMap.put(16, value);
    }

    public void setConverting(Boolean value) {
        if (value == null) {
            unsetConverting();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(15, value);
        this.dataMap.put(15, value);
    }

    public void unsetConverting() {
        unsetConverting(false);
    }

    public void unsetConverting(boolean restoreDefault) {
        if (restoreDefault) {
            setConverting(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(15);
            this.dataMap.remove(15);
        }
    }

    public void unsetProfession() {
        unsetProfession(false);
    }

    public void unsetProfession(boolean restoreDefault) {
        if (restoreDefault) {
            setProfession(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(16);
            this.dataMap.remove(16);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetConverting();
        unsetProfession();
    }
}
