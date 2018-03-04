package net.blitzcube.peapi.entity.living.insentient.creature.monster;

import net.blitzcube.peapi.api.entity.living.insentient.creature.monster.IEvocationIllagerData;
import net.blitzcube.peapi.entity.living.insentient.creature.MonsterData;

public class EvocationIllagerData extends MonsterData implements IEvocationIllagerData {
    public Byte getSpell() {
        return (Byte) this.dataMap.get(12);
    }

    public void setSpell(Byte value) {
        if (value == null) {
            unsetSpell();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(12, value);
        this.dataMap.put(12, value);
    }

    public void unsetSpell() {
        unsetSpell(false);
    }

    public void unsetSpell(boolean restoreDefault) {
        if (restoreDefault) {
            setSpell((byte) 0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(12);
            this.dataMap.remove(12);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetSpell();
    }
}
