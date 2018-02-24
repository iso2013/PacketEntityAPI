package net.blitzcube.peapi.api.listener;

import net.blitzcube.peapi.api.event.PacketEntityEvent;
import org.bukkit.entity.EntityType;

import java.util.Comparator;

/**
 * Created by iso2013 on 2/16/2018.
 */
public interface IListener {
    ListenerPriority getPriority();

    void onEvent(PacketEntityEvent e);

    default boolean shouldPreload() {
        return false;
    }

    default boolean shouldFireForFake() {
        return false;
    }

    EntityType[] getTargets();

    enum ListenerPriority {
        MONITOR(3),
        HIGHEST(2),
        HIGH(1),
        NORMAL(0),
        LOW(-1),
        LOWEST(-2);

        private int priority;

        ListenerPriority(int priority) {
            this.priority = priority;
        }

        public static Comparator<ListenerPriority> getComparator() {
            return Comparator.comparingInt(o -> o.priority);
        }
    }
}