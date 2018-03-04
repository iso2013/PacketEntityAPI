package net.blitzcube.peapi.entity;

import net.blitzcube.peapi.api.entity.ILivingData;
import org.bukkit.entity.Entity;

public class LivingData extends EntityData implements ILivingData {
    public Boolean isHandActive() {
        return (((Byte) super.dataMap.get(6)) & 0x1) > 0;
    }

    public Boolean isOffhand() {
        return (((Byte) super.dataMap.get(6)) & 0x2) > 0;
    }

    public Float getHealth() {
        return (Float) this.dataMap.get(7);
    }

    public void setHealth(Float value) {
        if (value == null) {
            unsetHealth();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(7, value);
        this.dataMap.put(7, value);
    }

    public Integer getPotionColor() {
        return (Integer) this.dataMap.get(8);
    }

    public void setPotionColor(Integer value) {
        if (value == null) {
            unsetPotionColor();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(8, value);
        this.dataMap.put(8, value);
    }

    public Boolean isPotionAmbient() {
        return (Boolean) this.dataMap.get(9);
    }

    public Integer getNumberOfArrowsInEntity() {
        return (Integer) this.dataMap.get(10);
    }

    public void setNumberOfArrowsInEntity(Integer value) {
        if (value == null) {
            unsetNumberOfArrowsInEntity();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(10, value);
        this.dataMap.put(10, value);
    }

    public void setHandActive(boolean value) {
        setBitmask(6, (byte) 0x01, value);
    }

    public void setOffhand(boolean value) {
        setBitmask(6, (byte) 0x02, value);
    }

    public void setPotionAmbient(Boolean value) {
        if (value == null) {
            unsetPotionAmbient();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(9, value);
        this.dataMap.put(9, value);
    }

    public void unsetHandActive(Entity e) {
        unsetBitmask(6, (byte) 0x01, false);
    }

    public void unsetOffhand(Entity e) {
        unsetBitmask(6, (byte) 0x02, false);
    }

    public void unsetHealth() {
        unsetHealth(false);
    }

    public void unsetHealth(boolean restoreDefault) {
        if (restoreDefault) {
            setHealth(1.0f);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(7);
            this.dataMap.remove(7);
        }
    }

    public void unsetPotionColor() {
        unsetPotionColor(false);
    }

    public void unsetPotionColor(boolean restoreDefault) {
        if (restoreDefault) {
            setPotionColor(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(8);
            this.dataMap.remove(8);
        }
    }

    public void unsetPotionAmbient() {
        unsetPotionAmbient(false);
    }

    public void unsetPotionAmbient(boolean restoreDefault) {
        if (restoreDefault) {
            setPotionAmbient(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(9);
            this.dataMap.remove(9);
        }
    }

    public void unsetNumberOfArrowsInEntity() {
        unsetNumberOfArrowsInEntity(false);
    }

    public void unsetNumberOfArrowsInEntity(boolean restoreDefault) {
        if (restoreDefault) {
            setNumberOfArrowsInEntity(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(10);
            this.dataMap.remove(10);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetHandActive(null);
        unsetOffhand(null);
        unsetHealth();
        unsetPotionColor();
        unsetPotionAmbient();
        unsetNumberOfArrowsInEntity();
    }
}
