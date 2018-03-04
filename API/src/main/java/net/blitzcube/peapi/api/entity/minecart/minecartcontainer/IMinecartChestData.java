package net.blitzcube.peapi.api.entity.minecart.minecartcontainer;

import net.blitzcube.peapi.api.entity.minecart.IMinecartContainerData;

public interface IMinecartChestData extends IMinecartContainerData {

    void resetToDefault();

    void clearAll();
}
