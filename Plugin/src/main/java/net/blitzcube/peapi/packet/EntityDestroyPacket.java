package net.blitzcube.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.collect.ImmutableList;
import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.api.packet.IEntityDestroyPacket;
import net.blitzcube.peapi.entity.EntityIdentifier;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class EntityDestroyPacket extends EntityPacket implements IEntityDestroyPacket {
    private static final PacketType TYPE = PacketType.Play.Server.ENTITY_DESTROY;
    private final List<IEntityIdentifier> targets;
    private boolean changed = false;

    EntityDestroyPacket() {
        super(null, new PacketContainer(TYPE), true);
        this.targets = new ArrayList<>();
    }

    private EntityDestroyPacket(PacketContainer rawPacket, List<IEntityIdentifier> targets) {
        super(null, rawPacket, false);
        this.targets = targets;
    }

    public static EntityPacket unwrap(PacketContainer c, Player p) {
        return new EntityDestroyPacket(c, Arrays.stream(c.getIntegerArrays().read(0))
                .mapToObj(value -> EntityIdentifier.produce(value, p, true)).filter(Objects::nonNull).collect(Collectors.toList())
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
        if (e == null) return;
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
        EntityDestroyPacket p = new EntityDestroyPacket();
        for (IEntityIdentifier e : targets) p.addToGroup(e);
        return p;
    }
}
