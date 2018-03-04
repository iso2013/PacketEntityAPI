package net.blitzcube.peapi.entity.living.insentient.creature.monster;

import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.google.common.base.Optional;
import net.blitzcube.peapi.api.entity.living.insentient.creature.monster.IEndermanData;
import net.blitzcube.peapi.entity.living.insentient.creature.MonsterData;

public class EndermanData extends MonsterData implements IEndermanData {
    public Optional<WrappedBlockData> getCarriedBlock() {
        return (Optional<WrappedBlockData>) this.dataMap.get(12);
    }

    public void setCarriedBlock(Optional<WrappedBlockData> value) {
        if (value == null) {
            unsetCarriedBlock();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(12, value);
        this.dataMap.put(12, value);
    }

    public Boolean isScreaming() {
        return (Boolean) this.dataMap.get(13);
    }

    public void setScreaming(Boolean value) {
        if (value == null) {
            unsetScreaming();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(13, value);
        this.dataMap.put(13, value);
    }

    public void unsetCarriedBlock() {
        unsetCarriedBlock(false);
    }

    public void unsetCarriedBlock(boolean restoreDefault) {
        if (restoreDefault) {
            setCarriedBlock(Optional.absent());
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(12);
            this.dataMap.remove(12);
        }
    }

    public void unsetScreaming() {
        unsetScreaming(false);
    }

    public void unsetScreaming(boolean restoreDefault) {
        if (restoreDefault) {
            setScreaming(false);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(13);
            this.dataMap.remove(13);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetCarriedBlock();
        unsetScreaming();
    }
}
