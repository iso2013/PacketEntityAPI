package net.blitzcube.peapi.entity;

import net.blitzcube.peapi.api.entity.IItemData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemData extends EntityData implements IItemData {
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

    public void resetToDefault() {
        super.resetToDefault();
        unsetItem();
    }
}
