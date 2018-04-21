package net.blitzcube.peapi.api.event;

import com.google.common.collect.ImmutableList;
import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;

/**
 * Created by iso2013 on 2/24/2018.
 */
public interface IPacketGroupEntityEvent {
    ImmutableList<IEntityIdentifier> getGroup();

    void removeFromGroup(IEntityIdentifier e);

    void addToGroup(IEntityIdentifier e);

    void apply();
}
