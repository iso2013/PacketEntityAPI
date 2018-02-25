package net.blitzcube.peapi.api.event;

import com.comphenix.protocol.wrappers.EnumWrappers;

/**
 * Created by iso2013 on 2/24/2018.
 */
public interface IPacketEntityClickEvent extends IPacketEntityEvent {
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
