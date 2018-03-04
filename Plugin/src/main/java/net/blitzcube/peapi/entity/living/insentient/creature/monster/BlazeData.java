package net.blitzcube.peapi.entity.living.insentient.creature.monster;

import net.blitzcube.peapi.api.entity.living.insentient.creature.monster.IBlazeData;
import net.blitzcube.peapi.entity.living.insentient.creature.MonsterData;
import org.bukkit.entity.Entity;

public class BlazeData extends MonsterData implements IBlazeData {
    public Boolean isBlazeFire() {
        return (((Byte) super.dataMap.get(12)) & 0x1) > 0;
    }

    public void setBlazeFire(boolean value) {
        setBitmask(12, (byte) 0x01, value);
    }

    public void unsetBlazeFire(Entity e) {
        unsetBitmask(12, (byte) 0x01, false);
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetBlazeFire(null);
    }
}
