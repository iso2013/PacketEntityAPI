package net.blitzcube.peapi.api.entity;

import org.bukkit.inventory.ItemStack;

public interface IItemData extends IEntityData {
    ItemStack getItem();

    void setItem(ItemStack value);

    void unsetItem();

    void resetToDefault();

    void clearAll();
}
