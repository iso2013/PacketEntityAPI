package net.blitzcube.peapi.api.event;

import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * Created by iso2013 on 2/24/2018.
 */
public interface IPacketEntityEquipmentEvent extends IPacketEntityEvent {
    EquipmentSlot getSlot();

    void setSlot(EquipmentSlot slot);

    ItemStack getItem();

    void setItem(ItemStack item);
}
