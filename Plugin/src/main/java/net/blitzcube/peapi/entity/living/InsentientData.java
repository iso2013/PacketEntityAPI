package net.blitzcube.peapi.entity.living;

import net.blitzcube.peapi.api.entity.living.IInsentientData;
import net.blitzcube.peapi.entity.LivingData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class InsentientData extends LivingData implements IInsentientData {
    public Boolean isNoAI() {
        return (((Byte) super.dataMap.get(11)) & 0x1) > 0;
    }

    public Boolean isLeftHanded() {
        return (((Byte) super.dataMap.get(11)) & 0x2) > 0;
    }

    public void setNoAI(boolean value) {
        setBitmask(11, (byte) 0x01, value);
    }

    public void setLeftHanded(boolean value) {
        setBitmask(11, (byte) 0x02, value);
    }

    public void unsetNoAI(Entity e) {
        unsetBitmask(11, (byte) 0x01, e != null && e instanceof LivingEntity && !((LivingEntity) e).hasAI());
    }

    public void unsetLeftHanded(Entity e) {
        unsetBitmask(11, (byte) 0x02, false);
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetNoAI(null);
        unsetLeftHanded(null);
    }
}
