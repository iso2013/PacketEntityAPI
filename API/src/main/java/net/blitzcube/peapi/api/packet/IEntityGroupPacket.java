package net.blitzcube.peapi.api.packet;

import com.google.common.collect.ImmutableList;
import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;

/**
 * Created by iso2013 on 4/21/2018.
 */
public interface IEntityGroupPacket extends IEntityPacket {
    ImmutableList<IEntityIdentifier> getGroup();

    void removeFromGroup(IEntityIdentifier e);

    void addToGroup(IEntityIdentifier e);

    void apply();
}
