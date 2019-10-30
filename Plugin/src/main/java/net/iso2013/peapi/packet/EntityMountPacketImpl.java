package net.iso2013.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.collect.ImmutableList;
import net.iso2013.peapi.api.entity.EntityIdentifier;
import net.iso2013.peapi.api.packet.EntityMountPacket;
import net.iso2013.peapi.entity.EntityIdentifierImpl;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class EntityMountPacketImpl extends EntityPacketImpl implements EntityMountPacket {
    private static final PacketType TYPE = PacketType.Play.Server.MOUNT;
    private final List<EntityIdentifier> targets;
    private boolean changed = false;

    EntityMountPacketImpl(EntityIdentifier identifier) {
        super(identifier, new PacketContainer(TYPE), true);
        this.targets = new ArrayList<>();
    }

    private EntityMountPacketImpl(EntityIdentifier identifier, PacketContainer rawPacket, List<EntityIdentifier>
            targets) {
        super(identifier, rawPacket, false);
        this.targets = targets;
    }

    public static EntityPacketImpl unwrap(int entityID, PacketContainer c, Player p) {
        return new EntityMountPacketImpl(EntityIdentifierImpl.produce(entityID, p), c,
                Arrays.stream(c.getIntegerArrays().read(0))
                        .mapToObj(value -> EntityIdentifierImpl.produce(value, p)).collect(Collectors.toList())
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
        if (getIdentifier().getEntityID() == e.getEntityID())
            throw new IllegalArgumentException("Cannot make an entity ride itself!");
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
        EntityMountPacketImpl p = new EntityMountPacketImpl(getIdentifier());
        for (EntityIdentifier e : targets) p.addToGroup(e);
        return p;
    }
}
