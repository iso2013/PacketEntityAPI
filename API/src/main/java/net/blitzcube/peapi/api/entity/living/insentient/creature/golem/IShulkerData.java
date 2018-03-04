package net.blitzcube.peapi.api.entity.living.insentient.creature.golem;

import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.google.common.base.Optional;
import net.blitzcube.peapi.api.entity.living.insentient.creature.IGolemData;

public interface IShulkerData extends IGolemData {
    EnumWrappers.Direction getFacingDirection();

    void setFacingDirection(EnumWrappers.Direction value);

    Optional<BlockPosition> getAttachmentPosition();

    void setAttachmentPosition(Optional<BlockPosition> value);

    Byte getShieldHeight();

    void setShieldHeight(Byte value);

    Byte getColor();

    void setColor(Byte value);

    void unsetFacingDirection();

    void unsetAttachmentPosition();

    void unsetShieldHeight();

    void unsetColor();

    void resetToDefault();

    void clearAll();
}
