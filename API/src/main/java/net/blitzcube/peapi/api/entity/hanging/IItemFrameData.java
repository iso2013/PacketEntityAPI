package net.blitzcube.peapi.api.entity.hanging;

import net.blitzcube.peapi.api.entity.IHangingData;
import org.bukkit.inventory.ItemStack;

public interface IItemFrameData extends IHangingData {
    ItemStack getItem();

    void setItem(ItemStack value);

    Integer getRotation();

    void setRotation(Integer value);

    void unsetItem();

    void unsetRotation();

    void resetToDefault();

    void clearAll();
}
