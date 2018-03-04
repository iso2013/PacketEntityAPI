package net.blitzcube.peapi.entity.living.insentient.creature.monster;

import net.blitzcube.peapi.api.entity.living.insentient.creature.monster.IVindicationIllagerData;
import net.blitzcube.peapi.entity.living.insentient.creature.MonsterData;
import org.bukkit.entity.Entity;

public class VindicationIllagerData extends MonsterData implements IVindicationIllagerData {
    public Boolean isAggressive() {
        return (((Byte) super.dataMap.get(12)) & 0x1) > 0;
    }

    public void setAggressive(boolean value) {
        setBitmask(12, (byte) 0x01, value);
    }

    public void unsetAggressive(Entity e) {
        unsetBitmask(12, (byte) 0x01, false);
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetAggressive(null);
    }
}
