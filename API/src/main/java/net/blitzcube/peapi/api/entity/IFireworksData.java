package net.blitzcube.peapi.api.entity;

import org.bukkit.inventory.ItemStack;

public interface IFireworksData extends IEntityData {
    ItemStack getFireworkItem();

    void setFireworkItem(ItemStack value);

    Integer getBoostedID();

    void setBoostedID(Integer value);

    void unsetFireworkItem();

    void unsetBoostedID();

    void resetToDefault();

    void clearAll();
}
