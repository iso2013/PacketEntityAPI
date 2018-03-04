package net.blitzcube.peapi.api.entity.minecart;

import net.blitzcube.peapi.api.entity.IMinecartData;

public interface IMinecartFurnaceData extends IMinecartData {
    Boolean isPowered();

    void setPowered(Boolean value);

    void unsetPowered();

    void resetToDefault();

    void clearAll();
}
