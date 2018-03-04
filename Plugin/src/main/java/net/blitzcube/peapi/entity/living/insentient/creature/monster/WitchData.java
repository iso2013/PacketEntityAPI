package net.blitzcube.peapi.entity.living.insentient.creature.monster;

import net.blitzcube.peapi.api.entity.living.insentient.creature.monster.IWitchData;
import net.blitzcube.peapi.entity.living.insentient.creature.MonsterData;

public class WitchData extends MonsterData implements IWitchData {
    public Boolean isDrinkingPotion() {
        return (Boolean) this.dataMap.get(12);
    }

    public void setDrinkingPotion(Boolean value) {
        if (value == null) {
            unsetDrinkingPotion();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(12, value);
        this.dataMap.put(12, value);
    }

    public void unsetDrinkingPotion() {
        unsetDrinkingPotion(false);
    }

    public void unsetDrinkingPotion(boolean restoreDefault) {
        if (restoreDefault) {
            setDrinkingPotion(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(12);
            this.dataMap.remove(12);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetDrinkingPotion();
    }
}
