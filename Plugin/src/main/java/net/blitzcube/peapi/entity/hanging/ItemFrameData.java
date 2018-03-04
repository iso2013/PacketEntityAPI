package net.blitzcube.peapi.entity.hanging;

import net.blitzcube.peapi.api.entity.hanging.IItemFrameData;
import net.blitzcube.peapi.entity.HangingData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemFrameData extends HangingData implements IItemFrameData {
    public ItemStack getItem() {
        return (ItemStack) this.dataMap.get(6);
    }

    public void setItem(ItemStack value) {
        if (value == null) {
            unsetItem();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(6, value);
        this.dataMap.put(6, value);
    }

    public Integer getRotation() {
        return (Integer) this.dataMap.get(7);
    }

    public void setRotation(Integer value) {
        if (value == null) {
            unsetRotation();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(7, value);
        this.dataMap.put(7, value);
    }

    public void unsetItem() {
        unsetItem(false);
    }

    public void unsetItem(boolean restoreDefault) {
        if (restoreDefault) {
            setItem(new ItemStack(Material.AIR));
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(6);
            this.dataMap.remove(6);
        }
    }

    public void unsetRotation() {
        unsetRotation(false);
    }

    public void unsetRotation(boolean restoreDefault) {
        if (restoreDefault) {
            setRotation(0);
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(7);
            this.dataMap.remove(7);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetItem();
        unsetRotation();
    }
}
