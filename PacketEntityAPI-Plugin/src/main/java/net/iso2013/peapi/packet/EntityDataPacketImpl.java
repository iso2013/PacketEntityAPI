package net.iso2013.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import net.iso2013.peapi.api.entity.EntityIdentifier;
import net.iso2013.peapi.api.packet.EntityDataPacket;
import net.iso2013.peapi.entity.EntityIdentifierImpl;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class EntityDataPacketImpl extends EntityPacketImpl implements EntityDataPacket {
    private static final PacketType TYPE = PacketType.Play.Server.ENTITY_METADATA;
    private List<WrappedWatchableObject> metadata;

    EntityDataPacketImpl(EntityIdentifier identifier) {
        super(identifier, new PacketContainer(TYPE), true);
    }

    private EntityDataPacketImpl(EntityIdentifier identifier, PacketContainer rawPacket, List<WrappedWatchableObject>
            metadata) {
        super(identifier, rawPacket, false);
        this.metadata = metadata;
    }

    public static EntityPacketImpl unwrap(int entityID, PacketContainer c, Player p) {
        return new EntityDataPacketImpl(
                EntityIdentifierImpl.produce(entityID, p),
                c,
                c.getWatchableCollectionModifier().read(0)
        );
    }

    @Override
    public List<WrappedWatchableObject> getMetadata() {
        return metadata;
    }

    @Override
    public void setMetadata(List<WrappedWatchableObject> metadata) {
        this.metadata = metadata;
        super.rawPacket.getWatchableCollectionModifier().write(0, metadata);
    }

    @Override
    public void rewriteMetadata() {
        super.rawPacket.getWatchableCollectionModifier().write(0, metadata);
    }

    @Override
    public PacketContainer getRawPacket() {
        assert metadata != null && metadata.size() > 0;
        return super.getRawPacket();
    }

    @Override
    public EntityPacketImpl clone() {
        EntityDataPacketImpl p = new EntityDataPacketImpl(getIdentifier());
        p.setMetadata(new ArrayList<>(metadata));
        return p;
    }
}
