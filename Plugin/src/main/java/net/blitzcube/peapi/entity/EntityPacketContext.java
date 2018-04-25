package net.blitzcube.peapi.entity;

import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.base.Preconditions;
import net.blitzcube.peapi.api.event.IEntityPacketContext;
import net.blitzcube.peapi.api.packet.IEntityPacket;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by iso2013 on 4/23/2018.
 */
public class EntityPacketContext implements IEntityPacketContext {
    private final ProtocolManager manager;
    private final Player target;
    private TaskChain chain;

    public EntityPacketContext(ProtocolManager manager, TaskChainFactory chainFactory, Player target) {
        this.manager = manager;
        this.chain = chainFactory.newChain();
        this.target = target;
    }

    @Override
    public IEntityPacketContext queueDispatch(IEntityPacket... packets) {
        for (IEntityPacket p : packets) {
            PacketContainer c = p.getRawPacket();
            if (c.getType().isClient()) {
                chain = chain.sync(() -> safeReceive(c));
            } else {
                chain = chain.sync(() -> safeSend(c));
            }
            if (p.getDelay() > 0) {
                chain = chain.delay(p.getDelay());
            }
        }
        return this;
    }

    @Override
    public IEntityPacketContext queueDispatch(IEntityPacket[] packets, int[] delays) {
        Preconditions.checkArgument(packets.length >= delays.length, "Too many delays have " +
                "been specified!");
        for (int i = 0; i < packets.length; i++) {
            IEntityPacket p = packets[i];
            int delay = i < delays.length ? delays[i] : p.getDelay();
            if (delay < 0) delay = p.getDelay();
            PacketContainer c = p.getRawPacket();
            if (c.getType().isClient()) {
                chain = chain.sync(() -> safeReceive(c));
            } else {
                chain = chain.sync(() -> safeSend(c));
            }
            if (delay > 0) chain = chain.delay(delay);
        }
        return this;
    }

    @Override
    public IEntityPacketContext queueDispatch(IEntityPacket[] packets, int delay) {
        int[] delays = new int[packets.length];
        delays[0] = delay;
        return queueDispatch(packets, delays);
    }

    @Override
    public IEntityPacketContext queueDispatch(IEntityPacket packet, int delay) {
        PacketContainer c = packet.getRawPacket();
        if (c.getType().isClient()) {
            chain = chain.sync(() -> safeReceive(c));
        } else {
            chain = chain.sync(() -> safeSend(c));
        }
        if (delay < 0) delay = packet.getDelay();
        if (delay > 0) chain = chain.delay(delay);
        return this;
    }

    @Override
    public IEntityPacketContext queueDispatch(IEntityPacket packet) {
        PacketContainer c = packet.getRawPacket();
        if (c.getType().isClient()) {
            chain = chain.sync(() -> safeReceive(c));
        } else {
            chain = chain.sync(() -> safeSend(c));
        }
        int delay = packet.getDelay();
        if (delay > 0) chain = chain.delay(delay);
        return this;
    }

    @Override
    public void execute() {
        chain.execute();
    }

    private void safeSend(PacketContainer packet) {
        try {
            manager.sendServerPacket(target, packet, true);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void safeReceive(PacketContainer packet) {
        try {
            manager.recieveClientPacket(target, packet, true);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
