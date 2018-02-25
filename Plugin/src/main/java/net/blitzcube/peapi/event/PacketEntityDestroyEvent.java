package net.blitzcube.peapi.event;

import com.comphenix.protocol.events.PacketContainer;
import com.google.common.collect.ImmutableList;
import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.api.event.IPacketEntityDestroyEvent;
import net.blitzcube.peapi.entity.EntityIdentifier;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by iso2013 on 2/24/2018.
 */
public class PacketEntityDestroyEvent extends PacketEntityEvent implements IPacketEntityDestroyEvent {
    private final List<IEntityIdentifier> targets;
    private boolean changed = false;

    private PacketEntityDestroyEvent(Player player, List<IEntityIdentifier> targets,
                                     PacketContainer packet) {
        super(null, player, packet);
        this.targets = targets;
    }

    public static IPacketEntityDestroyEvent unwrapPacket(PacketContainer p, Player target) {
        return new PacketEntityDestroyEvent(target, Arrays.stream(p.getIntegerArrays().read(0))
                .mapToObj(value -> new EntityIdentifier(value, target)).collect(Collectors.toList()), p);
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
        packet.getIntegerArrays().write(0, targets.stream().mapToInt(IEntityIdentifier::getEntityID).toArray());
    }
}
