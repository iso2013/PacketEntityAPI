package net.blitzcube.peapi.api.packet;

import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * Created by iso2013 on 4/21/2018.
 */
public interface IPacketEntityEquipment extends IPacketEntity {
    EquipmentSlot getSlot();

    void setSlot(EquipmentSlot slot);

    ItemStack getItem();

    void setItem(ItemStack item);
}
