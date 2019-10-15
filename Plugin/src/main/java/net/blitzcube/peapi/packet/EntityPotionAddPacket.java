package net.blitzcube.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.api.packet.IEntityPotionAddPacket;
import net.blitzcube.peapi.entity.EntityIdentifier;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by iso2013 on 6/8/2018.
 */
public class EntityPotionAddPacket extends EntityPacket implements IEntityPotionAddPacket {
    private static final PacketType TYPE = PacketType.Play.Server.ENTITY_EFFECT;
    private PotionEffect effect;

    EntityPotionAddPacket(IEntityIdentifier identifier) {
        super(identifier, new PacketContainer(TYPE), true);
        effect = null;
    }

    private EntityPotionAddPacket(IEntityIdentifier identifier, PacketContainer rawPacket, PotionEffect effect) {
        super(identifier, rawPacket, false);
        this.effect = effect;
    }

    public static EntityPacket unwrap(int entityID, PacketContainer c, Player p) {
        byte flags = c.getBytes().read(2);
        return new EntityPotionAddPacket(
                EntityIdentifier.produce(entityID, p),
                c,
                new PotionEffect(
                        PotionEffectType.getById(c.getBytes().read(0)),
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
    public EntityPacket clone() {
        EntityPotionAddPacket p = new EntityPotionAddPacket(getIdentifier());
        p.setEffect(effect);
        return p;
    }
}
