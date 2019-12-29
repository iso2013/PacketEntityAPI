package net.iso2013.peapi.api.listener;

import net.iso2013.peapi.api.event.EntityPacketEvent;
import org.bukkit.entity.EntityType;

import java.util.Comparator;

/**
 * @author iso2013
 * @version 0.1
 * @since 2018-02-16
 */
public interface Listener {
    /**
     * Gets the priority that this listener should run at. Higher priority listeners are run LAST, to ensure that their
     * changes take effect.
     *
     * @return the priority of this listener
     */
    ListenerPriority getPriority();

    /**
     * This method is invoked by the event dispatcher whenever a packet event fires.
     *
     * @param e the event
     */
    void onEvent(EntityPacketEvent e);

    /**
     * Gets whether or not to fire this listener for fake packet-based entities. By default, this returns false.
     * <br>
     * Other listeners can set this to true for their listeners, so <b>you can not assume that just because this returns
     * false, all events fired are for real entities.</b>
     *
     * @return if it should be fired for fake entities
     */
    default boolean shouldFireForFake() {
        return false;
    }

    /**
     * Gets the targets that this listener is fired for. This is <b>only</b> used to check if this listener needs to
     * listen to objects or to entities. <b>You can not assume that this listener will be fired only for your type
     * because this only contains your type; if it contains an entity it will be fired for all entities, and if it
     * contains an object, it will be fired for all objects.</b>
     *
     * @return the array containing the list of targets that this listener needs to fire for.
     */
    EntityType[] getTargets();

    /**
     * Whether or not this listener should be fired for custom collision events caused by custom hitboxes in fake
     * entities.
     *
     * @return whether or not this should be fired for fake collision boxes
     */
    default boolean requiresCollidable() { return false; }

    enum ListenerPriority {
        MONITOR(3),
        HIGHEST(2),
        HIGH(1),
        NORMAL(0),
        LOW(-1),
        LOWEST(-2);

        private final int priority;

        ListenerPriority(int priority) {
            this.priority = priority;
        }

        /**
         * Gets a comparator that sorts from least to highest priority.
         *
         * @return a comparator which will sort by the listener's priority.
         */
        public static Comparator<ListenerPriority> getComparator() {
            return Comparator.comparingInt(o -> o.priority);
        }
    }
}