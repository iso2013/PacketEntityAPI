package net.blitzcube.peapi.entity.living;

import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import net.blitzcube.peapi.api.entity.living.IPlayerData;
import net.blitzcube.peapi.entity.LivingData;
import org.bukkit.entity.Entity;

public class PlayerData extends LivingData implements IPlayerData {
    public Float getAdditionalHearts() {
        return (Float) this.dataMap.get(11);
    }

    public void setAdditionalHearts(Float value) {
        if (value == null) {
            unsetAdditionalHearts();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(11, value);
        this.dataMap.put(11, value);
    }

    public Integer getScore() {
        return (Integer) this.dataMap.get(12);
    }

    public void setScore(Integer value) {
        if (value == null) {
            unsetScore();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(12, value);
        this.dataMap.put(12, value);
    }

    public Boolean isLeftPantsShown() {
        return (((Byte) super.dataMap.get(13)) & 0x10) > 0;
    }

    public Boolean isRightPantsShown() {
        return (((Byte) super.dataMap.get(13)) & 0x20) > 0;
    }

    public Boolean isHatShown() {
        return (((Byte) super.dataMap.get(13)) & 0x40) > 0;
    }

    public Boolean isCapeShown() {
        return (((Byte) super.dataMap.get(13)) & 0x1) > 0;
    }

    public Boolean isJacketShown() {
        return (((Byte) super.dataMap.get(13)) & 0x2) > 0;
    }

    public Boolean isLeftSleeveShown() {
        return (((Byte) super.dataMap.get(13)) & 0x4) > 0;
    }

    public Boolean isRightSleeveShown() {
        return (((Byte) super.dataMap.get(13)) & 0x8) > 0;
    }

    public Byte getMainHand() {
        return (Byte) this.dataMap.get(14);
    }

    public void setMainHand(Byte value) {
        if (value == null) {
            unsetMainHand();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(14, value);
        this.dataMap.put(14, value);
    }

    public NbtCompound getLeftShoulderParrot() {
        return (NbtCompound) this.dataMap.get(15);
    }

    public void setLeftShoulderParrot(NbtCompound value) {
        if (value == null) {
            unsetLeftShoulderParrot();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(15, value);
        this.dataMap.put(15, value);
    }

    public NbtCompound getRightShoulderParrot() {
        return (NbtCompound) this.dataMap.get(16);
    }

    public void setRightShoulderParrot(NbtCompound value) {
        if (value == null) {
            unsetRightShoulderParrot();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(16, value);
        this.dataMap.put(16, value);
    }

    public void setLeftPantsShown(boolean value) {
        setBitmask(13, (byte) 0x10, value);
    }

    public void setRightPantsShown(boolean value) {
        setBitmask(13, (byte) 0x20, value);
    }

    public void setHatShown(boolean value) {
        setBitmask(13, (byte) 0x40, value);
    }

    public void setCapeShown(boolean value) {
        setBitmask(13, (byte) 0x01, value);
    }

    public void setJacketShown(boolean value) {
        setBitmask(13, (byte) 0x02, value);
    }

    public void setLeftSleeveShown(boolean value) {
        setBitmask(13, (byte) 0x04, value);
    }

    public void setRightSleeveShown(boolean value) {
        setBitmask(13, (byte) 0x08, value);
    }

    public void unsetAdditionalHearts() {
        unsetAdditionalHearts(false);
    }

    public void unsetAdditionalHearts(boolean restoreDefault) {
        if (restoreDefault) {
            setAdditionalHearts(0.0f);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(11);
            this.dataMap.remove(11);
        }
    }

    public void unsetScore() {
        unsetScore(false);
    }

    public void unsetScore(boolean restoreDefault) {
        if (restoreDefault) {
            setScore(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(12);
            this.dataMap.remove(12);
        }
    }

    public void unsetLeftPantsShown(Entity e) {
        unsetBitmask(13, (byte) 0x10, false);
    }

    public void unsetRightPantsShown(Entity e) {
        unsetBitmask(13, (byte) 0x20, false);
    }

    public void unsetHatShown(Entity e) {
        unsetBitmask(13, (byte) 0x40, false);
    }

    public void unsetCapeShown(Entity e) {
        unsetBitmask(13, (byte) 0x01, false);
    }

    public void unsetJacketShown(Entity e) {
        unsetBitmask(13, (byte) 0x02, false);
    }

    public void unsetLeftSleeveShown(Entity e) {
        unsetBitmask(13, (byte) 0x04, false);
    }

    public void unsetRightSleeveShown(Entity e) {
        unsetBitmask(13, (byte) 0x08, false);
    }

    public void unsetMainHand() {
        unsetMainHand(false);
    }

    public void unsetMainHand(boolean restoreDefault) {
        if (restoreDefault) {
            setMainHand((byte) 1);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(14);
            this.dataMap.remove(14);
        }
    }

    public void unsetLeftShoulderParrot() {
        unsetLeftShoulderParrot(false);
    }

    public void unsetLeftShoulderParrot(boolean restoreDefault) {
        if (restoreDefault) {
            setLeftShoulderParrot(NbtFactory.ofCompound("{}"));
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(15);
            this.dataMap.remove(15);
        }
    }

    public void unsetRightShoulderParrot() {
        unsetRightShoulderParrot(false);
    }

    public void unsetRightShoulderParrot(boolean restoreDefault) {
        if (restoreDefault) {
            setRightShoulderParrot(NbtFactory.ofCompound("{}"));
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(16);
            this.dataMap.remove(16);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetAdditionalHearts();
        unsetScore();
        unsetLeftPantsShown(null);
        unsetRightPantsShown(null);
        unsetHatShown(null);
        unsetCapeShown(null);
        unsetJacketShown(null);
        unsetLeftSleeveShown(null);
        unsetRightSleeveShown(null);
        unsetMainHand();
        unsetLeftShoulderParrot();
        unsetRightShoulderParrot();
    }
}
