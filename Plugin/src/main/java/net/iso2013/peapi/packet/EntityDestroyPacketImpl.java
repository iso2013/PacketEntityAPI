package net.iso2013.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.collect.ImmutableList;
import net.iso2013.peapi.api.entity.EntityIdentifier;
import net.iso2013.peapi.api.packet.EntityDestroyPacket;
import net.iso2013.peapi.entity.EntityIdentifierImpl;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class EntityDestroyPacketImpl extends EntityPacketImpl implements EntityDestroyPacket {
    private static final PacketType TYPE = PacketType.Play.Server.ENTITY_DESTROY;
    private final List<EntityIdentifier> targets;
    private boolean changed = false;

    EntityDestroyPacketImpl() {
        super(null, new PacketContainer(TYPE), true);
        this.targets = new ArrayList<>();
    }

    private EntityDestroyPacketImpl(PacketContainer rawPacket, List<EntityIdentifier> targets) {
        super(null, rawPacket, false);
        this.targets = targets;
    }

    public static EntityPacketImpl unwrap(PacketContainer c, Player p) {
        return new EntityDestroyPacketImpl(c, Arrays.stream(c.getIntegerArrays().read(0))
                .mapToObj(value -> EntityIdentifierImpl.produce(value, p)).filter(Objects::nonNull).collect(Collectors.toList())
        );
    }

    @Override
    public ImmutableList<EntityIdentifier> getGroup() {
        return ImmutableList.copyOf(targets);
    }

    @Override
    public void removeFromGroup(EntityIdentifier e) {
        targets.remove(e);
        changed = true;
    }

    @Override
    public void addToGroup(EntityIdentifier e) {
        if (e == null) return;
        targets.add(e);
        changed = true;
    }

    @Override
    public void apply() {
        if (!changed) return;
        super.rawPacket.getIntegerArrays().write(0, targets.stream().mapToInt(EntityIdentifier::getEntityID)
                .toArray());
    }

    @Override
    public PacketContainer getRawPacket() {
        assert targets.size() > 0;
        return super.getRawPacket();
    }

    @Override
    public EntityPacketImpl clone() {
        EntityDestroyPacketImpl p = new EntityDestroyPacketImpl();
        for (EntityIdentifier e : targets) p.addToGroup(e);
        return p;
    }
}
