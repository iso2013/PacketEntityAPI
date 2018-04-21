package net.blitzcube.peapi.event;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;
import net.blitzcube.peapi.api.event.IPacketEntityEquipmentEvent;
import net.blitzcube.peapi.entity.modifier.EntityIdentifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * Created by iso2013 on 2/24/2018.
 */
public class PacketEntityEquipmentEvent extends PacketEntityEvent implements IPacketEntityEquipmentEvent {
    private EquipmentSlot slot;
    private ItemStack item;

    private PacketEntityEquipmentEvent(IEntityIdentifier identifier, Player target, EquipmentSlot slot, ItemStack
            item, PacketContainer packet) {
        super(identifier, target, packet);
        this.slot = slot;
        this.item = item;
    }

    public static IPacketEntityEquipmentEvent unwrapPacket(int id, PacketContainer p, Player target) {
        return new PacketEntityEquipmentEvent(
                new EntityIdentifier(id, target),
                target,
                fromItemSlot(p.getItemSlots().read(0)),
                p.getItemModifier().read(0),
                p
        );
    }

    private static EquipmentSlot fromItemSlot(EnumWrappers.ItemSlot i) {
        switch (i) {
            case MAINHAND:
                return EquipmentSlot.HAND;
            case OFFHAND:
                return EquipmentSlot.OFF_HAND;
            case FEET:
                return EquipmentSlot.FEET;
            case LEGS:
                return EquipmentSlot.LEGS;
            case CHEST:
                return EquipmentSlot.CHEST;
            case HEAD:
                return EquipmentSlot.HEAD;
        }
        return null;
    }

    private static EnumWrappers.ItemSlot fromEquipmentSlot(EquipmentSlot i) {
        switch (i) {
            case HAND:
                return EnumWrappers.ItemSlot.MAINHAND;
            case OFF_HAND:
                return EnumWrappers.ItemSlot.OFFHAND;
            case FEET:
                return EnumWrappers.ItemSlot.FEET;
            case LEGS:
                return EnumWrappers.ItemSlot.LEGS;
            case CHEST:
                return EnumWrappers.ItemSlot.CHEST;
            case HEAD:
                return EnumWrappers.ItemSlot.HEAD;
        }
        return null;
    }

    @Override
    public EquipmentSlot getSlot() {
        return this.slot;
    }

    @Override
    public void setSlot(EquipmentSlot slot) {
        this.slot = slot;
        super.packet.getItemSlots().write(0, fromEquipmentSlot(slot));
    }

    @Override
    public ItemStack getItem() {
        return this.item;
    }

    @Override
    public void setItem(ItemStack item) {
        this.item = item;
        super.packet.getItemModifier().write(0, item);
    }
}
