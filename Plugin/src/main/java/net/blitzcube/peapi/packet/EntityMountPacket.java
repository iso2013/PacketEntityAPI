package net.blitzcube.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.collect.ImmutableList;
import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.api.packet.IEntityMountPacket;
import net.blitzcube.peapi.entity.EntityIdentifier;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class EntityMountPacket extends EntityPacket implements IEntityMountPacket {
    private static final PacketType TYPE = PacketType.Play.Server.MOUNT;
    private final List<IEntityIdentifier> targets;
    private boolean changed = false;

    EntityMountPacket(IEntityIdentifier identifier) {
        super(identifier, new PacketContainer(TYPE), true);
        this.targets = new ArrayList<>();
    }

    private EntityMountPacket(IEntityIdentifier identifier, PacketContainer rawPacket, List<IEntityIdentifier>
            targets) {
        super(identifier, rawPacket, false);
        this.targets = targets;
    }

    public static EntityPacket unwrap(int entityID, PacketContainer c, Player p) {
        return new EntityMountPacket(EntityIdentifier.produce(entityID, p, false), c,
                Arrays.stream(c.getIntegerArrays().read(0))
                        .mapToObj(value -> EntityIdentifier.produce(value, p, false)).collect(Collectors.toList())
        );
    }

    @Override
    public ImmutableList<IEntityIdentifier> getGroup() {
        return ImmutableList.copyOf(targets);
    }

    @Override
    public void removeFromGroup(IEntityIdentifier e) {
        targets.remove(e);
        changed = true;
    }

    @Override
    public void addToGroup(IEntityIdentifier e) {
        targets.add(e);
        changed = true;
    }

    @Override
    public void apply() {
        if (!changed) return;
        super.rawPacket.getIntegerArrays().write(0, targets.stream().mapToInt(IEntityIdentifier::getEntityID)
                .toArray());
    }

    @Override
    public PacketContainer getRawPacket() {
        assert targets.size() > 0;
        return super.getRawPacket();
    }

    @Override
    public EntityPacket clone() {
        EntityMountPacket p = new EntityMountPacket(getIdentifier());
        for (IEntityIdentifier e : targets) p.addToGroup(e);
        return p;
    }
}
