package net.iso2013.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import net.iso2013.peapi.api.entity.EntityIdentifier;
import net.iso2013.peapi.api.packet.EntityPotionRemovePacket;
import net.iso2013.peapi.entity.EntityIdentifierImpl;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by iso2013 on 6/8/2018.
 */
public class EntityPotionRemovePacketImpl extends EntityPacketImpl implements EntityPotionRemovePacket {
    private static final PacketType TYPE = PacketType.Play.Server.ENTITY_EFFECT;
    private PotionEffectType type;

    EntityPotionRemovePacketImpl(EntityIdentifier identifier) {
        super(identifier, new PacketContainer(TYPE), true);
        type = null;
    }

    private EntityPotionRemovePacketImpl(EntityIdentifier identifier, PacketContainer rawPacket,
                                         PotionEffectType type) {
        super(identifier, rawPacket, false);
        this.type = type;
    }

    public static EntityPacketImpl unwrap(int entityID, PacketContainer c, Player p) {
        return new EntityPotionRemovePacketImpl(
                EntityIdentifierImpl.produce(entityID, p),
                c,
                c.getEffectTypes().read(0)
        );
    }

    @Override
    public PotionEffectType getEffectType() {
        return type;
    }

    @Override
    public void setEffectType(PotionEffectType value) {
        this.type = value;
    }

    @Override
    public PacketContainer getRawPacket() {
        assert type != null;
        return super.getRawPacket();
    }

    @Override
    public EntityPacketImpl clone() {
        EntityPotionRemovePacketImpl p = new EntityPotionRemovePacketImpl(getIdentifier());
        p.setEffectType(type);
        return p;
    }
}
