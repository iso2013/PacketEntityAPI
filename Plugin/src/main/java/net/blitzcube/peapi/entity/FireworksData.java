package net.blitzcube.peapi.entity;

import net.blitzcube.peapi.api.entity.IFireworksData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class FireworksData extends EntityData implements IFireworksData {
    public ItemStack getFireworkItem() {
        return (ItemStack) this.dataMap.get(6);
    }

    public void setFireworkItem(ItemStack value) {
        if (value == null) {
            unsetFireworkItem();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(6, value);
        this.dataMap.put(6, value);
    }

    public Integer getBoostedID() {
        return (Integer) this.dataMap.get(7);
    }

    public void setBoostedID(Integer value) {
        if (value == null) {
            unsetBoostedID();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(7, value);
        this.dataMap.put(7, value);
    }

    public void unsetFireworkItem() {
        unsetFireworkItem(false);
    }

    public void unsetFireworkItem(boolean restoreDefault) {
        if (restoreDefault) {
            setFireworkItem(new ItemStack(Material.AIR));
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(6);
            this.dataMap.remove(6);
        }
    }

    public void unsetBoostedID() {
        unsetBoostedID(false);
    }

    public void unsetBoostedID(boolean restoreDefault) {
        if (restoreDefault) {
            setBoostedID(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(7);
            this.dataMap.remove(7);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetFireworkItem();
        unsetBoostedID();
    }
}
