package net.blitzcube.peapi.api.entity.fireball;

import net.blitzcube.peapi.api.entity.IFireballData;

public interface IWitherSkullData extends IFireballData {
    Boolean isInvulnerable();

    void setInvulnerable(Boolean value);

    void unsetInvulnerable();

    void resetToDefault();

    void clearAll();
}
