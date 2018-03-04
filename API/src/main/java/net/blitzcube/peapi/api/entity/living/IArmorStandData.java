package net.blitzcube.peapi.api.entity.living;

import net.blitzcube.peapi.api.entity.ILivingData;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public interface IArmorStandData extends ILivingData {
    Boolean isMarker();

    Boolean isSmall();

    Boolean isArms();

    Boolean isNoBasePlate();

    Vector getHeadRotation();

    void setHeadRotation(Vector value);

    Vector getBodyRotation();

    void setBodyRotation(Vector value);

    Vector getLeftArmRotation();

    void setLeftArmRotation(Vector value);

    Vector getRightArmRotation();

    void setRightArmRotation(Vector value);

    Vector getLeftLegRotation();

    void setLeftLegRotation(Vector value);

    Vector getRightLegRotation();

    void setRightLegRotation(Vector value);

    void setMarker(boolean value);

    void setSmall(boolean value);

    void setArms(boolean value);

    void setNoBasePlate(boolean value);

    void unsetMarker(Entity e);

    void unsetSmall(Entity e);

    void unsetArms(Entity e);

    void unsetNoBasePlate(Entity e);

    void unsetHeadRotation();

    void unsetBodyRotation();

    void unsetLeftArmRotation();

    void unsetRightArmRotation();

    void unsetLeftLegRotation();

    void unsetRightLegRotation();

    void resetToDefault();

    void clearAll();
}
