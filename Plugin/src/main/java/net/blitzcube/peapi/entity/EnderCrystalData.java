package net.blitzcube.peapi.entity;

import com.comphenix.protocol.wrappers.BlockPosition;
import com.google.common.base.Optional;
import net.blitzcube.peapi.api.entity.IEnderCrystalData;

public class EnderCrystalData extends EntityData implements IEnderCrystalData {
    public Optional<BlockPosition> getBeamTarget() {
        return (Optional<BlockPosition>) this.dataMap.get(6);
    }

    public void setBeamTarget(Optional<BlockPosition> value) {
        if (value == null) {
            unsetBeamTarget();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(6, value);
        this.dataMap.put(6, value);
    }

    public Boolean isShowBottom() {
        return (Boolean) this.dataMap.get(7);
    }

    public void setShowBottom(Boolean value) {
        if (value == null) {
            unsetShowBottom();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(7, value);
        this.dataMap.put(7, value);
    }

    public void unsetBeamTarget() {
        unsetBeamTarget(false);
    }

    public void unsetBeamTarget(boolean restoreDefault) {
        if (restoreDefault) {
            setBeamTarget(Optional.absent());
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(6);
            this.dataMap.remove(6);
        }
    }

    public void unsetShowBottom() {
        unsetShowBottom(false);
    }

    public void unsetShowBottom(boolean restoreDefault) {
        if (restoreDefault) {
            setShowBottom(true);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(7);
            this.dataMap.remove(7);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetBeamTarget();
        unsetShowBottom();
    }
}
