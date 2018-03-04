package net.blitzcube.peapi.entity.living.insentient.creature.golem;

import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.google.common.base.Optional;
import net.blitzcube.peapi.api.entity.living.insentient.creature.golem.IShulkerData;
import net.blitzcube.peapi.entity.living.insentient.creature.GolemData;

public class ShulkerData extends GolemData implements IShulkerData {
    public EnumWrappers.Direction getFacingDirection() {
        return (EnumWrappers.Direction) this.dataMap.get(12);
    }

    public void setFacingDirection(EnumWrappers.Direction value) {
        if (value == null) {
            unsetFacingDirection();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(12, value);
        this.dataMap.put(12, value);
    }

    public Optional<BlockPosition> getAttachmentPosition() {
        return (Optional<BlockPosition>) this.dataMap.get(13);
    }

    public void setAttachmentPosition(Optional<BlockPosition> value) {
        if (value == null) {
            unsetAttachmentPosition();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(13, value);
        this.dataMap.put(13, value);
    }

    public Byte getShieldHeight() {
        return (Byte) this.dataMap.get(14);
    }

    public void setShieldHeight(Byte value) {
        if (value == null) {
            unsetShieldHeight();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(14, value);
        this.dataMap.put(14, value);
    }

    public Byte getColor() {
        return (Byte) this.dataMap.get(15);
    }

    public void setColor(Byte value) {
        if (value == null) {
            unsetColor();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(15, value);
        this.dataMap.put(15, value);
    }

    public void unsetFacingDirection() {
        unsetFacingDirection(false);
    }

    public void unsetFacingDirection(boolean restoreDefault) {
        if (restoreDefault) {
            setFacingDirection(EnumWrappers.Direction.UP);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(12);
            this.dataMap.remove(12);
        }
    }

    public void unsetAttachmentPosition() {
        unsetAttachmentPosition(false);
    }

    public void unsetAttachmentPosition(boolean restoreDefault) {
        if (restoreDefault) {
            setAttachmentPosition(Optional.absent());
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(13);
            this.dataMap.remove(13);
        }
    }

    public void unsetShieldHeight() {
        unsetShieldHeight(false);
    }

    public void unsetShieldHeight(boolean restoreDefault) {
        if (restoreDefault) {
            setShieldHeight((byte) 0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(14);
            this.dataMap.remove(14);
        }
    }

    public void unsetColor() {
        unsetColor(false);
    }

    public void unsetColor(boolean restoreDefault) {
        if (restoreDefault) {
            setColor((byte) 10);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(15);
            this.dataMap.remove(15);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetFacingDirection();
        unsetAttachmentPosition();
        unsetShieldHeight();
        unsetColor();
    }
}
