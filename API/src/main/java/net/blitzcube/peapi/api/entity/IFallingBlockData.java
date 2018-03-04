package net.blitzcube.peapi.api.entity;

import com.comphenix.protocol.wrappers.BlockPosition;

public interface IFallingBlockData extends IEntityData {
    BlockPosition getSpawnPosition();

    void setSpawnPosition(BlockPosition value);

    void unsetSpawnPosition();

    void resetToDefault();

    void clearAll();
}
