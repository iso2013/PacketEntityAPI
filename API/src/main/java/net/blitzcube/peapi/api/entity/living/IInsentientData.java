package net.blitzcube.peapi.api.entity.living;

import net.blitzcube.peapi.api.entity.ILivingData;
import org.bukkit.entity.Entity;

public interface IInsentientData extends ILivingData {
    Boolean isNoAI();

    Boolean isLeftHanded();

    void setNoAI(boolean value);

    void setLeftHanded(boolean value);

    void unsetNoAI(Entity e);

    void unsetLeftHanded(Entity e);

    void resetToDefault();

    void clearAll();
}
