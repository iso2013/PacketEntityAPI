package net.blitzcube.peapi.api.packet;

import com.comphenix.protocol.wrappers.EnumWrappers;

/**
 * Created by iso2013 on 4/21/2018.
 */
public interface IEntityClickPacket extends IEntityPacket {
    ClickType getClickType();

    void setClickType(ClickType type);

    enum ClickType {
        INTERACT,
        ATTACK,
        INTERACT_AT;

        public static ClickType getByID(byte id) {
            return values()[id];
        }

        public static ClickType getByProtocolLib(EnumWrappers.EntityUseAction read) {
            return values()[read.ordinal()];
        }

        public EnumWrappers.EntityUseAction getProtocolLibEquivalent() {
            switch (this) {
                case INTERACT:
                    return EnumWrappers.EntityUseAction.INTERACT;
                case ATTACK:
                    return EnumWrappers.EntityUseAction.ATTACK;
                case INTERACT_AT:
                    return EnumWrappers.EntityUseAction.INTERACT_AT;
            }
            return null;
        }
    }
}
