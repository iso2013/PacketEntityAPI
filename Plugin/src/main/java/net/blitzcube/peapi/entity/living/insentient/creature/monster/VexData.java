package net.blitzcube.peapi.entity.living.insentient.creature.monster;

import net.blitzcube.peapi.api.entity.living.insentient.creature.monster.IVexData;
import net.blitzcube.peapi.entity.living.insentient.creature.MonsterData;
import org.bukkit.entity.Entity;

public class VexData extends MonsterData implements IVexData {
    public Boolean isAttackMode() {
        return (((Byte) super.dataMap.get(12)) & 0x1) > 0;
    }

    public void setAttackMode(boolean value) {
        setBitmask(12, (byte) 0x01, value);
    }

    public void unsetAttackMode(Entity e) {
        unsetBitmask(12, (byte) 0x01, false);
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetAttackMode(null);
    }
}
