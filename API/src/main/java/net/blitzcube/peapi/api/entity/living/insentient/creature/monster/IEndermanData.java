package net.blitzcube.peapi.api.entity.living.insentient.creature.monster;

import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.google.common.base.Optional;
import net.blitzcube.peapi.api.entity.living.insentient.creature.IMonsterData;

public interface IEndermanData extends IMonsterData {
    Optional<WrappedBlockData> getCarriedBlock();

    void setCarriedBlock(Optional<WrappedBlockData> value);

    Boolean isScreaming();

    void setScreaming(Boolean value);

    void unsetCarriedBlock();

    void unsetScreaming();

    void resetToDefault();

    void clearAll();
}
