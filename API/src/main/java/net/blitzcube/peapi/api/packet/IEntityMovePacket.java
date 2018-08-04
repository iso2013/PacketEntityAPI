package net.blitzcube.peapi.api.packet;

import com.comphenix.protocol.PacketType;
import org.bukkit.util.Vector;

/**
 * Created by iso2013 on 8/1/2018.
 */
public interface IEntityMovePacket extends IEntityPacket {
    Vector getNewDirection();

    void setNewDirection(Vector direction);

    Vector getNewPosition();

    void setNewPosition(Vector position, boolean teleport);

    MoveType getMoveType();

    boolean isOnGround();

    void setOnGround(boolean onGround);

    double[] getPitchYaw();

    void setPitchYaw(double pitch, double yaw);

    enum MoveType {
        REL_MOVE(PacketType.Play.Server.REL_ENTITY_MOVE),
        LOOK_AND_REL_MOVE(PacketType.Play.Server.REL_ENTITY_MOVE_LOOK),
        LOOK(PacketType.Play.Server.ENTITY_LOOK),
        TELEPORT(PacketType.Play.Server.ENTITY_TELEPORT);

        private final PacketType type;

        MoveType(PacketType type) {
            this.type = type;
        }

        public PacketType getPacketType() {
            return type;
        }
    }
}
