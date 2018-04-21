package net.blitzcube.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.collect.ImmutableList;
import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;
import net.blitzcube.peapi.api.packet.IPacketEntityMount;
import net.blitzcube.peapi.entity.modifier.EntityIdentifier;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class EntityMountPacket extends EntityPacket implements IPacketEntityMount {
    private static final int TICK_DELAY = 1;
    private static final PacketType TYPE = PacketType.Play.Server.MOUNT;
    private final List<IEntityIdentifier> targets;
    private boolean changed = false;

    private EntityMountPacket(IEntityIdentifier identifier) {
        super(identifier, new PacketContainer(TYPE));
        this.targets = new ArrayList<>();
    }

    private EntityMountPacket(IEntityIdentifier identifier, PacketContainer rawPacket, List<IEntityIdentifier>
            targets) {
        super(identifier, rawPacket);
        this.targets = targets;
    }

    public static EntityPacket unwrap(int entityID, PacketContainer c, Player p) {
        return new EntityMountPacket(new EntityIdentifier(entityID, p), c,
                Arrays.stream(c.getIntegerArrays().read(0))
                        .mapToObj(value -> new EntityIdentifier(value, p)).collect(Collectors.toList())
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
}
