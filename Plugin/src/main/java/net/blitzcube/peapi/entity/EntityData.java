package net.blitzcube.peapi.entity;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.blitzcube.peapi.api.entity.IEntityData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Map;

public class EntityData implements IEntityData {
    protected Map<Integer, Object> dataMap;
    protected WrappedDataWatcher dataWatcher;

    public Boolean isInvisible() {
        return (((Byte) this.dataMap.get(0)) & 0x20) > 0;
    }

    public Boolean isGlowing() {
        return (((Byte) this.dataMap.get(0)) & 0x40) > 0;
    }

    public Boolean isOnFire() {
        return (((Byte) this.dataMap.get(0)) & 0x1) > 0;
    }

    public Boolean isCrouching() {
        return (((Byte) this.dataMap.get(0)) & 0x2) > 0;
    }

    public Boolean isSprinting() {
        return (((Byte) this.dataMap.get(0)) & 0x8) > 0;
    }

    public Boolean isElytraFlying() {
        return (((Byte) this.dataMap.get(0)) & 0xffffff80) > 0;
    }

    public Integer getRemainingAir() {
        return (Integer) this.dataMap.get(1);
    }

    public void setRemainingAir(Integer value) {
        if (value == null) {
            unsetRemainingAir();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(1, value);
        this.dataMap.put(1, value);
    }

    public String getCustomName() {
        return (String) this.dataMap.get(2);
    }

    public void setCustomName(String value) {
        if (value == null) {
            unsetCustomName();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(2, value);
        this.dataMap.put(2, value);
    }

    public Boolean isCustomNameVisible() {
        return (Boolean) this.dataMap.get(3);
    }

    public Boolean isSilent() {
        return (Boolean) this.dataMap.get(4);
    }

    public Boolean isGravity() {
        return (Boolean) this.dataMap.get(5);
    }

    public void setInvisible(boolean value) {
        setBitmask(0, (byte) 0x20, value);
    }

    public void setGlowing(boolean value) {
        setBitmask(0, (byte) 0x40, value);
    }

    public void setOnFire(boolean value) {
        setBitmask(0, (byte) 0x01, value);
    }

    public void setCrouching(boolean value) {
        setBitmask(0, (byte) 0x02, value);
    }

    public void setSprinting(boolean value) {
        setBitmask(0, (byte) 0x08, value);
    }

    public void setElytraFlying(boolean value) {
        setBitmask(0, (byte) 0x80, value);
    }

    public void setCustomNameVisible(Boolean value) {
        if (value == null) {
            unsetCustomNameVisible();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(3, value);
        this.dataMap.put(3, value);
    }

    public void setSilent(Boolean value) {
        if (value == null) {
            unsetSilent();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(4, value);
        this.dataMap.put(4, value);
    }

    public void setGravity(Boolean value) {
        if (value == null) {
            unsetGravity();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(5, value);
        this.dataMap.put(5, value);
    }

    public void unsetInvisible(Entity e) {
        unsetBitmask(0, (byte) 0x20, false);
    }

    public void unsetGlowing(Entity e) {
        unsetBitmask(0, (byte) 0x40, e != null && e.isGlowing());
    }

    public void unsetOnFire(Entity e) {
        unsetBitmask(0, (byte) 0x01, e != null && e.getFireTicks() > 0);
    }

    public void unsetCrouching(Entity e) {
        unsetBitmask(0, (byte) 0x02, e != null && e instanceof Player && ((Player) e).isSneaking());
    }

    public void unsetSprinting(Entity e) {
        unsetBitmask(0, (byte) 0x08, e != null && e instanceof Player && ((Player) e).isSprinting());
    }

    public void unsetElytraFlying(Entity e) {
        unsetBitmask(0, (byte) 0x80, e != null && e instanceof LivingEntity && ((LivingEntity) e).isGliding());
    }

    protected void unsetBitmask(int index, byte bitmask, boolean b) {
        if (!this.dataMap.containsKey(0)) return;
        setBitmask(index, bitmask, b);
    }

    protected void setBitmask(int index, byte bitmask, boolean b) {
        Byte bVal = (byte) (((Byte) this.dataMap.get(index) & ~bitmask) | (b ? bitmask : 0x00));
        if (this.dataWatcher != null) this.dataWatcher.setObject(index, bVal);
        this.dataMap.put(index, bVal);
    }

    public void unsetRemainingAir() {
        unsetRemainingAir(false);
    }

    public void unsetRemainingAir(boolean restoreDefault) {
        if (restoreDefault) {
            setRemainingAir(300);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(1);
            this.dataMap.remove(1);
        }
    }

    public void unsetCustomName() {
        unsetCustomName(false);
    }

    public void unsetCustomName(boolean restoreDefault) {
        if (restoreDefault) {
            setCustomName("");
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(2);
            this.dataMap.remove(2);
        }
    }

    public void unsetCustomNameVisible() {
        unsetCustomNameVisible(false);
    }

    public void unsetCustomNameVisible(boolean restoreDefault) {
        if (restoreDefault) {
            setCustomNameVisible(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(3);
            this.dataMap.remove(3);
        }
    }

    public void unsetSilent() {
        unsetSilent(false);
    }

    public void unsetSilent(boolean restoreDefault) {
        if (restoreDefault) {
            setSilent(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(4);
            this.dataMap.remove(4);
        }
    }

    public void unsetGravity() {
        unsetGravity(false);
    }

    public void unsetGravity(boolean restoreDefault) {
        if (restoreDefault) {
            setGravity(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(5);
            this.dataMap.remove(5);
        }
    }

    public void resetToDefault() {
        unsetInvisible(null);
        unsetGlowing(null);
        unsetOnFire(null);
        unsetCrouching(null);
        unsetSprinting(null);
        unsetElytraFlying(null);
        unsetRemainingAir();
        unsetCustomName();
        unsetCustomNameVisible();
        unsetSilent();
        unsetGravity();
    }

    @Override
    public void clearAll() {
        for (Integer i : dataMap.keySet()) dataWatcher.remove(i);
        dataMap.clear();
    }
}
