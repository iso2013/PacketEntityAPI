package net.blitzcube.peapi.api.event;

import net.blitzcube.peapi.api.entity.EntityIdentifier;

/**
 * Created by iso2013 on 2/23/2018.
 */
public class PacketEntityEvent {
    private final EntityIdentifier identifier;

    public PacketEntityEvent(EntityIdentifier identifier) {
        this.identifier = identifier;
    }

    public EntityIdentifier getIdentifier() {
        return identifier;
    }
}
