package net.iso2013.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import net.iso2013.peapi.api.entity.EntityIdentifier;
import net.iso2013.peapi.api.packet.EntityEquipmentPacket;
import net.iso2013.peapi.entity.EntityIdentifierImpl;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class EntityEquipmentPacketImpl extends EntityPacketImpl implements EntityEquipmentPacket {
    private static final PacketType TYPE = PacketType.Play.Server.ENTITY_EQUIPMENT;
    private EquipmentSlot slot;
    private ItemStack item;

    EntityEquipmentPacketImpl(EntityIdentifier identifier) {
        super(identifier, new PacketContainer(TYPE), true);
    }

    private EntityEquipmentPacketImpl(EntityIdentifier identifier, PacketContainer rawPacket, EquipmentSlot slot, ItemStack item) {
        super(identifier, rawPacket, false);
        this.slot = slot;
        this.item = item;
    }

    public static EntityPacketImpl unwrap(int entityID, PacketContainer c, Player p) {
        return new EntityEquipmentPacketImpl(
                EntityIdentifierImpl.produce(entityID, p),
                c,
                fromItemSlot(c.getItemSlots().read(0)),
                c.getItemModifier().read(0)
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
        return slot;
    }

    @Override
    public void setSlot(EquipmentSlot slot) {
        this.slot = slot;
        super.rawPacket.getItemSlots().write(0, fromEquipmentSlot(slot));
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public void setItem(ItemStack item) {
        this.item = item;
        super.rawPacket.getItemModifier().write(0, item);
    }

    @Override
    public PacketContainer getRawPacket() {
        assert slot != null && item != null;
        return super.getRawPacket();
    }

    @Override
    public EntityPacketImpl clone() {
        EntityEquipmentPacketImpl p = new EntityEquipmentPacketImpl(getIdentifier());
        p.setSlot(slot);
        p.setItem(item);
        return p;
    }
}
