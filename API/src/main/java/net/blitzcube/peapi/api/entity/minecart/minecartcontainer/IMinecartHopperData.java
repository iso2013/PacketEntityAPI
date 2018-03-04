package net.blitzcube.peapi.api.entity.minecart.minecartcontainer;

import net.blitzcube.peapi.api.entity.minecart.IMinecartContainerData;

public interface IMinecartHopperData extends IMinecartContainerData {

    void resetToDefault();

    void clearAll();
}
