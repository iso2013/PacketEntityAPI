package net.blitzcube.peapi.api.packet;

import com.comphenix.protocol.PacketType;
import org.bukkit.util.Vector;

/**
 * Created by iso2013 on 8/1/2018.
 */
public interface IEntityMovePacket extends IEntityPacket {
    /**
     * Gets the new direction set by this packet. This is a vector representing the pitch and yaw.
     *
     * @return the new direction set by this packet
     */
    Vector getNewDirection();

    /**
     * @param direction the new direction to be set. This should be a vector representing the pitch and yaw.
     */
    void setNewDirection(Vector direction);

    /**
     * If the {@link MoveType} is REL_MOVE or REL_ENTITY_MOVE_LOOK, this represents the change in the position.
     * If it is ENTITY_LOOK, this value is not used and is null or a zero vector.
     * If it is TELEPORT, this is the new absolute position.
     *
     * @return the new position to be set, as detailed above.
     */
    Vector getNewPosition();


    /**
     * Sets the new position to be moved to. See {@link #getNewPosition()} for details on the position parameter.
     *
     * @param position the new position to be set
     * @param teleport whether or not the position is absolute
     */
    void setNewPosition(Vector position, boolean teleport);

    /**
     * @return the type of movement this packet represents. This cannot be changed directly.
     */
    MoveType getMoveType();

    /**
     * @return whether or not the move packet says the entity is on the ground
     */
    boolean isOnGround();

    /**
     * @param onGround whether or not the move packet says the entity is on the ground
     */
    void setOnGround(boolean onGround);

    /**
     * Get the pitch and yaw associated with the new direction, in the order pitch, yaw.
     *
     * @return a double array containing the pitch and yaw
     */
    double[] getPitchYaw();

    /**
     * Set the pitch and yaw for the new direction.
     *
     * @param pitch the pitch to set
     * @param yaw   the yaw to set
     */
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


        /**
         * @return the ProtocolLib packet type associated with this MoveType
         */
        public PacketType getPacketType() {
            return type;
        }
    }
}
