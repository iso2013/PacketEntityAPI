package net.iso2013.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import net.iso2013.peapi.api.entity.EntityIdentifier;
import net.iso2013.peapi.api.packet.EntityPotionAddPacket;
import net.iso2013.peapi.entity.EntityIdentifierImpl;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by iso2013 on 6/8/2018.
 */
public class EntityPotionAddPacketImpl extends EntityPacketImpl implements EntityPotionAddPacket {
    private static final PacketType TYPE = PacketType.Play.Server.ENTITY_EFFECT;
    private PotionEffect effect;

    EntityPotionAddPacketImpl(EntityIdentifier identifier) {
        super(identifier, new PacketContainer(TYPE), true);
        effect = null;
    }

    private EntityPotionAddPacketImpl(EntityIdentifier identifier, PacketContainer rawPacket, PotionEffect effect) {
        super(identifier, rawPacket, false);
        this.effect = effect;
    }

    public static EntityPacketImpl unwrap(int entityID, PacketContainer c, Player p) {
        byte flags = c.getBytes().read(2);
        //noinspection deprecation
        PotionEffectType type = PotionEffectType.getById(c.getBytes().read(0));
        if (type == null) return null;

        return new EntityPotionAddPacketImpl(
                EntityIdentifierImpl.produce(entityID, p),
                c,
                new PotionEffect(
                        type,
                        c.getIntegers().read(1),
                        c.getBytes().read(1),
                        (flags & 0x01) > 0,
                        (flags & 0x02) > 0
                )
        );
    }

    @Override
    public PotionEffect getEffect() {
        return effect;
    }

    @Override
    public void setEffect(PotionEffect value) {
        this.effect = value;
        //noinspection deprecation
        super.rawPacket.getBytes().write(0, (byte) value.getType().getId());
        super.rawPacket.getBytes().write(1, (byte) value.getAmplifier());
        super.rawPacket.getIntegers().write(1, value.getDuration());
        byte flags = 0;
        if (value.isAmbient()) flags = (byte) (flags | 0x01);
        if (value.hasParticles()) flags = (byte) (flags | 0x02);
        super.rawPacket.getBytes().write(2, flags);
    }

    @Override
    public PacketContainer getRawPacket() {
        assert effect != null;
        return super.getRawPacket();
    }

    @Override
    public EntityPacketImpl clone() {
        EntityPotionAddPacketImpl p = new EntityPotionAddPacketImpl(getIdentifier());
        p.setEffect(effect);
        return p;
    }
}
