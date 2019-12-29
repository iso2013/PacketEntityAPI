package net.iso2013.peapi.api.packet;

import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * @author iso2013
 * @version 0.1
 * @since 2018-04-21
 */
public interface EntityEquipmentPacket extends EntityPacket {
    /**
     * Gets the equipment slot that is being set by this packet
     *
     * @return the equipment slot
     */
    EquipmentSlot getSlot();

    /**
     * Sets the equipment slot that is being set by this packet to a new value
     *
     * @param slot the new equipment slot to modify
     */
    void setSlot(EquipmentSlot slot);

    /**
     * Gets the item that the equipment slot is being set to
     *
     * @return the item that is being put into the slot
     */
    ItemStack getItem();

    /**
     * Sets the item that will be put into the equipment slot
     *
     * @param item the new item
     */
    void setItem(ItemStack item);
}
