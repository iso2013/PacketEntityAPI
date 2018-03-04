package net.blitzcube.peapi.entity.living;

import net.blitzcube.peapi.api.entity.living.IArmorStandData;
import net.blitzcube.peapi.entity.LivingData;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class ArmorStandData extends LivingData implements IArmorStandData {
    public Boolean isMarker() {
        return (((Byte) super.dataMap.get(11)) & 0x10) > 0;
    }

    public Boolean isSmall() {
        return (((Byte) super.dataMap.get(11)) & 0x1) > 0;
    }

    public Boolean isArms() {
        return (((Byte) super.dataMap.get(11)) & 0x4) > 0;
    }

    public Boolean isNoBasePlate() {
        return (((Byte) super.dataMap.get(11)) & 0x8) > 0;
    }

    public Vector getHeadRotation() {
        return (Vector) this.dataMap.get(12);
    }

    public void setHeadRotation(Vector value) {
        if (value == null) {
            unsetHeadRotation();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(12, value);
        this.dataMap.put(12, value);
    }

    public Vector getBodyRotation() {
        return (Vector) this.dataMap.get(13);
    }

    public void setBodyRotation(Vector value) {
        if (value == null) {
            unsetBodyRotation();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(13, value);
        this.dataMap.put(13, value);
    }

    public Vector getLeftArmRotation() {
        return (Vector) this.dataMap.get(14);
    }

    public void setLeftArmRotation(Vector value) {
        if (value == null) {
            unsetLeftArmRotation();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(14, value);
        this.dataMap.put(14, value);
    }

    public Vector getRightArmRotation() {
        return (Vector) this.dataMap.get(15);
    }

    public void setRightArmRotation(Vector value) {
        if (value == null) {
            unsetRightArmRotation();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(15, value);
        this.dataMap.put(15, value);
    }

    public Vector getLeftLegRotation() {
        return (Vector) this.dataMap.get(16);
    }

    public void setLeftLegRotation(Vector value) {
        if (value == null) {
            unsetLeftLegRotation();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(16, value);
        this.dataMap.put(16, value);
    }

    public Vector getRightLegRotation() {
        return (Vector) this.dataMap.get(17);
    }

    public void setRightLegRotation(Vector value) {
        if (value == null) {
            unsetRightLegRotation();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(17, value);
        this.dataMap.put(17, value);
    }

    public void setMarker(boolean value) {
        setBitmask(11, (byte) 0x10, value);
    }

    public void setSmall(boolean value) {
        setBitmask(11, (byte) 0x01, value);
    }

    public void setArms(boolean value) {
        setBitmask(11, (byte) 0x04, value);
    }

    public void setNoBasePlate(boolean value) {
        setBitmask(11, (byte) 0x08, value);
    }

    public void unsetMarker(Entity e) {
        unsetBitmask(11, (byte) 0x10, e != null && e instanceof ArmorStand && ((ArmorStand) e).isMarker());
    }

    public void unsetSmall(Entity e) {
        unsetBitmask(11, (byte) 0x01, e != null && e instanceof ArmorStand && ((ArmorStand) e).isSmall());
    }

    public void unsetArms(Entity e) {
        unsetBitmask(11, (byte) 0x04, e != null && e instanceof ArmorStand && ((ArmorStand) e).hasArms());
    }

    public void unsetNoBasePlate(Entity e) {
        unsetBitmask(11, (byte) 0x08, e != null && e instanceof ArmorStand && !((ArmorStand) e).hasBasePlate());
    }

    public void unsetHeadRotation() {
        unsetHeadRotation(false);
    }

    public void unsetHeadRotation(boolean restoreDefault) {
        if (restoreDefault) {
            setHeadRotation(new Vector(0.0, 0.0, 0.0));
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(12);
            this.dataMap.remove(12);
        }
    }

    public void unsetBodyRotation() {
        unsetBodyRotation(false);
    }

    public void unsetBodyRotation(boolean restoreDefault) {
        if (restoreDefault) {
            setBodyRotation(new Vector(0.0, 0.0, 0.0));
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(13);
            this.dataMap.remove(13);
        }
    }

    public void unsetLeftArmRotation() {
        unsetLeftArmRotation(false);
    }

    public void unsetLeftArmRotation(boolean restoreDefault) {
        if (restoreDefault) {
            setLeftArmRotation(new Vector(-10.0, 0.0, -10.0));
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(14);
            this.dataMap.remove(14);
        }
    }

    public void unsetRightArmRotation() {
        unsetRightArmRotation(false);
    }

    public void unsetRightArmRotation(boolean restoreDefault) {
        if (restoreDefault) {
            setRightArmRotation(new Vector(-15.0, 0.0, 10.0));
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(15);
            this.dataMap.remove(15);
        }
    }

    public void unsetLeftLegRotation() {
        unsetLeftLegRotation(false);
    }

    public void unsetLeftLegRotation(boolean restoreDefault) {
        if (restoreDefault) {
            setLeftLegRotation(new Vector(-1.0, 0.0, -1.0));
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(16);
            this.dataMap.remove(16);
        }
    }

    public void unsetRightLegRotation() {
        unsetRightLegRotation(false);
    }

    public void unsetRightLegRotation(boolean restoreDefault) {
        if (restoreDefault) {
            setRightLegRotation(new Vector(1.0, 0.0, 1.0));
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(17);
            this.dataMap.remove(17);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetMarker(null);
        unsetSmall(null);
        unsetArms(null);
        unsetNoBasePlate(null);
        unsetHeadRotation();
        unsetBodyRotation();
        unsetLeftArmRotation();
        unsetRightArmRotation();
        unsetLeftLegRotation();
        unsetRightLegRotation();
    }
}
