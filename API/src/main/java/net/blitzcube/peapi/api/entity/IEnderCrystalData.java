package net.blitzcube.peapi.api.entity;

import com.comphenix.protocol.wrappers.BlockPosition;
import com.google.common.base.Optional;

public interface IEnderCrystalData extends IEntityData {
    Optional<BlockPosition> getBeamTarget();

    void setBeamTarget(Optional<BlockPosition> value);

    Boolean isShowBottom();

    void setShowBottom(Boolean value);

    void unsetBeamTarget();

    void unsetShowBottom();

    void resetToDefault();

    void clearAll();
}
