package net.iso2013.peapi.event;

import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.base.Preconditions;
import net.iso2013.peapi.api.event.EntityPacketContext;
import net.iso2013.peapi.api.packet.EntityPacket;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by iso2013 on 4/23/2018.
 */
public class EntityPacketContextImpl implements EntityPacketContext {
    private final ProtocolManager manager;
    private final Player target;
    private TaskChain chain;

    EntityPacketContextImpl(ProtocolManager manager, TaskChainFactory chainFactory, Player target) {
        this.manager = manager;
        this.chain = chainFactory.newChain();
        this.target = target;
    }

    @Override
    public EntityPacketContext queueDispatch(EntityPacket... packets) {
        for (EntityPacket p : packets) {
            PacketContainer c = p.getRawPacket();
            if (c != null && c.getType().isClient()) {
                chain = chain.sync(() -> safeReceive(c));
            } else {
                chain = chain.sync(() -> safeSend(c));
            }
        }
        return this;
    }

    @Override
    public EntityPacketContext queueDispatch(Set<EntityPacket> packets) {
        for (EntityPacket p : packets) {
            PacketContainer c = p.getRawPacket();
            if (c != null && c.getType().isClient()) {
                chain = chain.sync(() -> safeReceive(c));
            } else {
                chain = chain.sync(() -> safeSend(c));
            }
        }
        return this;
    }

    @Override
    public EntityPacketContext queueDispatch(EntityPacket[] packets, int[] delays) {
        Preconditions.checkArgument(packets.length >= delays.length, "Too many delays have " +
                "been specified!");
        for (int i = 0; i < packets.length; i++) {
            EntityPacket p = packets[i];
            int delay = i < delays.length ? delays[i] : 0;
            if (delay < 0) delay = 0;
            PacketContainer c = p.getRawPacket();
            if (c != null && c.getType().isClient()) {
                chain = chain.sync(() -> safeReceive(c));
            } else {
                chain = chain.sync(() -> safeSend(c));
            }
            if (delay > 0) chain = chain.delay(delay);
        }
        return this;
    }

    @Override
    public EntityPacketContext queueDispatch(Collection<EntityPacket> packets, int[] delays) {
        Preconditions.checkArgument(packets.size() >= delays.length, "Too many delays have " +
                "been specified!");
        Iterator<EntityPacket> it = packets.iterator();
        int i = 0;
        while (it.hasNext()) {
            EntityPacket p = it.next();
            int delay = i < delays.length ? delays[i] : 0;
            if (delay < 0) delay = 0;
            PacketContainer c = p.getRawPacket();
            if (c != null && c.getType().isClient()) {
                chain = chain.sync(() -> safeReceive(c));
            } else {
                chain = chain.sync(() -> safeSend(c));
            }
            if (delay > 0) chain = chain.delay(delay);
            i++;
        }
        return this;
    }

    @Override
    public EntityPacketContext queueDispatch(EntityPacket[] packets, int delay) {
        int[] delays = new int[packets.length];
        delays[0] = delay;
        return queueDispatch(packets, delays);
    }

    @Override
    public EntityPacketContext queueDispatch(Set<EntityPacket> packets, int delay) {
        int[] delays = new int[packets.size()];
        delays[0] = delay;
        return queueDispatch(packets, delays);
    }

    @Override
    public EntityPacketContext queueDispatch(EntityPacket packet, int delay) {
        PacketContainer c = packet.getRawPacket();
        if (c != null && c.getType().isClient()) {
            chain = chain.sync(() -> safeReceive(c));
        } else {
            chain = chain.sync(() -> safeSend(c));
        }
        if (delay > 0) chain = chain.delay(delay);
        return this;
    }

    @Override
    public EntityPacketContext queueDispatch(EntityPacket packet) {
        PacketContainer c = packet.getRawPacket();
        if (c.getType().isClient()) {
            chain = chain.sync(() -> safeReceive(c));
        } else {
            chain = chain.sync(() -> safeSend(c));
        }
        return this;
    }

    @Override
    public void execute() {
        chain.execute();
    }

    private void safeSend(PacketContainer packet) {
        if (packet == null) return;
        try {
            manager.sendServerPacket(target, packet, true);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void safeReceive(PacketContainer packet) {
        if (packet == null) return;
        try {
            manager.recieveClientPacket(target, packet, true);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
