package net.blitzcube.peapi.entity.living.insentient.creature.monster;

import net.blitzcube.peapi.api.entity.living.insentient.creature.monster.ISpiderData;
import net.blitzcube.peapi.entity.living.insentient.creature.MonsterData;
import org.bukkit.entity.Entity;

public class SpiderData extends MonsterData implements ISpiderData {
    public Boolean isClimbing() {
        return (((Byte) super.dataMap.get(12)) & 0x1) > 0;
    }

    public void setClimbing(boolean value) {
        setBitmask(12, (byte) 0x01, value);
    }

    public void unsetClimbing(Entity e) {
        unsetBitmask(12, (byte) 0x01, false);
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetClimbing(null);
    }
}
