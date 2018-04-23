package net.blitzcube.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;
import net.blitzcube.peapi.api.packet.IEntityPacketData;
import net.blitzcube.peapi.entity.EntityIdentifier;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class EntityDataPacket extends EntityPacket implements IEntityPacketData {
    private static final int TICK_DELAY = 1;
    private static final PacketType TYPE = PacketType.Play.Server.ENTITY_METADATA;
    private List<WrappedWatchableObject> metadata;

    EntityDataPacket(IEntityIdentifier identifier) {
        super(identifier, new PacketContainer(TYPE));
    }

    private EntityDataPacket(IEntityIdentifier identifier, PacketContainer rawPacket, List<WrappedWatchableObject>
            metadata) {
        super(identifier, rawPacket);
        this.metadata = metadata;
    }

    public static EntityPacket unwrap(int entityID, PacketContainer c, Player p) {
        return new EntityDataPacket(
                new EntityIdentifier(entityID, p),
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
}
