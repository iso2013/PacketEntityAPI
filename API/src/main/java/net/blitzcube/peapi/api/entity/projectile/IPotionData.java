package net.blitzcube.peapi.api.entity.projectile;

import net.blitzcube.peapi.api.entity.IProjectileData;
import org.bukkit.inventory.ItemStack;

public interface IPotionData extends IProjectileData {
    ItemStack getPotionItem();

    void setPotionItem(ItemStack value);

    void unsetPotionItem();

    void resetToDefault();

    void clearAll();
}
