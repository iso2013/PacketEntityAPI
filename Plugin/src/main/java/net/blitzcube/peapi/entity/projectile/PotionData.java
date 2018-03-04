package net.blitzcube.peapi.entity.projectile;

import net.blitzcube.peapi.api.entity.projectile.IPotionData;
import net.blitzcube.peapi.entity.ProjectileData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PotionData extends ProjectileData implements IPotionData {
    public ItemStack getPotionItem() {
        return (ItemStack) this.dataMap.get(6);
    }

    public void setPotionItem(ItemStack value) {
        if (value == null) {
            unsetPotionItem();
            return;
        }
        if (this.dataWatcher != null) this.dataWatcher.setObject(6, value);
        this.dataMap.put(6, value);
    }

    public void unsetPotionItem() {
        unsetPotionItem(false);
    }

    public void unsetPotionItem(boolean restoreDefault) {
        if (restoreDefault) {
            setPotionItem(new ItemStack(Material.AIR));
        } else {
            if (this.dataWatcher != null) this.dataWatcher.remove(6);
            this.dataMap.remove(6);
        }
    }

    public void resetToDefault() {
        super.resetToDefault();
        unsetPotionItem();
    }
}
