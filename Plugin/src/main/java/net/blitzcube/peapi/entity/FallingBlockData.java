package net.blitzcube.peapi.entity;

import com.comphenix.protocol.wrappers.BlockPosition;
import net.blitzcube.peapi.api.entity.IFallingBlockData;

public class FallingBlockData extends EntityData implements IFallingBlockData {
    public BlockPosition getSpawnPosition() {
        return (BlockPosition) this.dataMap.get(6);
    }

    public void setSpawnPosition(BlockPosition value) {
        if (value == null) {
            unsetSpawnPosition();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(6, value);
        this.dataMap.put(6, value);
    }

    public void unsetSpawnPosition() {
        unsetSpawnPosition(false);
    }

    public void unsetSpawnPosition(boolean restoreDefault) {
        if (restoreDefault) {
            setSpawnPosition(new BlockPosition(0, 0, 0));
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(6);
            this.dataMap.remove(6);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetSpawnPosition();
    }
}
