package net.blitzcube.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.api.packet.IEntityPotionRemovePacket;
import net.blitzcube.peapi.entity.EntityIdentifier;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by iso2013 on 6/8/2018.
 */
public class EntityPotionRemovePacket extends EntityPacket implements IEntityPotionRemovePacket {
    private static final PacketType TYPE = PacketType.Play.Server.ENTITY_EFFECT;
    private PotionEffectType type;

    EntityPotionRemovePacket(IEntityIdentifier identifier) {
        super(identifier, new PacketContainer(TYPE), true);
        type = null;
    }

    private EntityPotionRemovePacket(IEntityIdentifier identifier, PacketContainer rawPacket, PotionEffectType type) {
        super(identifier, rawPacket, false);
        this.type = type;
    }

    public static EntityPacket unwrap(int entityID, PacketContainer c, Player p) {
        return new EntityPotionRemovePacket(
                EntityIdentifier.produce(entityID, p),
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
    public EntityPacket clone() {
        EntityPotionRemovePacket p = new EntityPotionRemovePacket(getIdentifier());
        p.setEffectType(type);
        return p;
    }
}
