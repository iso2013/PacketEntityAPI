package net.iso2013.peapi.api.packet;

import com.comphenix.protocol.wrappers.EnumWrappers;
import net.iso2013.peapi.api.entity.fake.FakeEntity;
import net.iso2013.peapi.api.entity.hitbox.Hitbox;

/**
 * @author iso2013
 * @version 0.1
 * @since 2018-04-21
 */
public interface EntityClickPacket extends EntityPacket {
    /**
     * Gets the click type that was used during the interaction with the entity included in the packet.
     * <br>
     * See {@link FakeEntity#setHitbox(Hitbox)} for more information about custom
     * entity hitboxes.
     *
     * @return the click type
     */
    ClickType getClickType();

    /**
     * Sets the click type that was used during the interaction with the entity included in the packet.
     *
     * @param type the new click type
     */
    void setClickType(ClickType type);

    enum ClickType {
        INTERACT,
        ATTACK,
        INTERACT_AT;

        /**
         * Gets a click type using the byte ID. This isn't really designed for public use, but it can't harm anything.
         *
         * @param id the ID to lookup
         * @return the corresponding click type
         */
        public static ClickType getByID(byte id) {
            return values()[id];
        }

        /**
         * Converts a wrapped ProtocolLib enum value to this enum. Not really designed for public use, but it wouldn't
         * harm anything to use it.
         *
         * @param read the input ProtocolLib enum
         * @return the click type that the given ProtocolLib corresponds to
         */
        public static ClickType getByProtocolLib(EnumWrappers.EntityUseAction read) {
            return values()[read.ordinal()];
        }

        /**
         * Converts this enum to the wrapped ProtocolLib enum. Not really designed for public use, but it wouldn't harm
         * anything to use it.
         *
         * @return the ProtocolLib enum wrapper.
         */
        public EnumWrappers.EntityUseAction getProtocolLibEquivalent() {
            return EnumWrappers.EntityUseAction.values()[this.ordinal()];
        }
    }
}
