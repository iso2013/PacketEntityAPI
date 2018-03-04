package net.blitzcube.peapi.entity.living.insentient.creature.golem;

import net.blitzcube.peapi.api.entity.living.insentient.creature.golem.IIronGolemData;
import net.blitzcube.peapi.entity.living.insentient.creature.GolemData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;

public class IronGolemData extends GolemData implements IIronGolemData {
    public Boolean isPlayerCreated() {
        return (((Byte) super.dataMap.get(12)) & 0x1) > 0;
    }

    public void setPlayerCreated(boolean value) {
        setBitmask(12, (byte) 0x01, value);
    }

    public void unsetPlayerCreated(Entity e) {
        unsetBitmask(12, (byte) 0x01, e != null && e instanceof IronGolem && ((IronGolem) e).isPlayerCreated());
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetPlayerCreated(null);
    }
}
