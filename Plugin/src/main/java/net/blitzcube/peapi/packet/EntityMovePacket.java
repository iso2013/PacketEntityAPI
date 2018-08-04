package net.blitzcube.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.api.packet.IEntityMovePacket;
import net.blitzcube.peapi.entity.EntityIdentifier;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Created by iso2013 on 8/1/2018.
 */
public class EntityMovePacket extends EntityPacket implements IEntityMovePacket {
    private boolean onGround;
    private MoveType type;
    private Vector position;
    private Vector direction;
    private double pitch, yaw;

    EntityMovePacket(IEntityIdentifier identifier, MoveType type) {
        super(identifier, new PacketContainer(type.getPacketType()), true);
        this.type = type;
    }

    private EntityMovePacket(IEntityIdentifier identifier, PacketContainer rawPacket, Vector newAngle, Vector
            newLocation, boolean onGround, boolean teleport) {
        super(identifier, rawPacket, true);
        if (teleport) {
            type = MoveType.TELEPORT;
        } else if (newAngle == null && newLocation != null) {
            type = MoveType.REL_MOVE;
        } else if (newAngle != null && newLocation == null) {
            type = MoveType.LOOK;
        } else if (newAngle != null) {
            type = MoveType.LOOK_AND_REL_MOVE;
        }
        this.direction = newAngle != null ? newAngle : new Vector();
        this.position = newLocation != null ? newLocation : new Vector();
        this.onGround = onGround;
        double[] angles = vectorToAngles(this.direction);
        pitch = angles[0];
        yaw = angles[1];
    }

    public static EntityPacket unwrap(int entityID, PacketContainer c, Player p) {
        PacketType pt = c.getType();

        if (pt == PacketType.Play.Server.ENTITY_TELEPORT) {
            return new EntityMovePacket(
                    new EntityIdentifier(entityID, p),
                    c,
                    vectorFromAngles(c.getBytes().read(1), c.getBytes().read(0)),
                    new Vector(c.getDoubles().read(0), c.getDoubles().read(1), c.getDoubles().read(2)),
                    c.getBooleans().read(0),
                    true
            );
        } else if (pt == PacketType.Play.Server.REL_ENTITY_MOVE) {
            return new EntityMovePacket(
                    new EntityIdentifier(entityID, p),
                    c,
                    null,
                    new Vector(
                            c.getIntegers().read(1) / 4096,
                            c.getIntegers().read(2) / 4096,
                            c.getIntegers().read(3) / 4096
                    ),
                    c.getBooleans().read(0),
                    false
            );
        } else if (pt == PacketType.Play.Server.REL_ENTITY_MOVE_LOOK) {
            return new EntityMovePacket(
                    new EntityIdentifier(entityID, p),
                    c,
                    vectorFromAngles(c.getBytes().read(1), c.getBytes().read(0)),
                    new Vector(
                            c.getIntegers().read(1) / 4096,
                            c.getIntegers().read(2) / 4096,
                            c.getIntegers().read(3) / 4096
                    ),
                    c.getBooleans().read(0),
                    false
            );
        } else if (pt == PacketType.Play.Server.ENTITY_LOOK) {
            return new EntityMovePacket(
                    new EntityIdentifier(entityID, p),
                    c,
                    vectorFromAngles(c.getBytes().read(1), c.getBytes().read(0)),
                    null,
                    c.getBooleans().read(0),
                    false
            );
        }
        return null;
    }

    private static Vector vectorFromAngles(double pitch, double yaw) {
        Vector dir = new Vector();
        pitch = Math.toRadians(pitch);
        yaw = Math.toRadians(yaw);
        dir.setY(-Math.sin(pitch));
        double horizontalLength = Math.cos(pitch);
        dir.setX(-horizontalLength * Math.sin(yaw));
        dir.setX(horizontalLength * Math.cos(yaw));
        return dir;
    }

    private static double[] vectorToAngles(Vector v) {
        double[] angles = new double[2];
        if (v.getX() == 0 && v.getZ() == 0) {
            angles[0] = (byte) (v.getY() > 0 ? -90 : 90);
        }

        double theta = Math.atan2(-v.getX(), v.getZ());
        angles[1] = Math.toDegrees((theta + (Math.PI * 2) % (Math.PI * 2)));
        angles[0] = Math.toDegrees(Math.atan(-v.getY() / Math.sqrt((v.getX() * v.getX()) + (v.getY() * v.getY()))));
        return angles;
    }

    @Override
    public Vector getNewDirection() {
        return direction;
    }

    @Override
    public void setNewDirection(Vector direction) {
        this.direction = direction;
        double[] angles = vectorToAngles(direction);
        switch (type) {
            case REL_MOVE:
                setType(MoveType.LOOK_AND_REL_MOVE);
            case LOOK_AND_REL_MOVE:
            case LOOK:
            case TELEPORT:
                super.rawPacket.getBytes().write(1, (byte) angles[0]);
                super.rawPacket.getBytes().write(0, (byte) angles[1]);
                break;
        }
        this.pitch = angles[0];
        this.yaw = angles[1];
    }

    @Override
    public Vector getNewPosition() {
        return position;
    }

    @Override
    public void setNewPosition(Vector position, boolean teleport) {
        this.position = position;
        switch (type) {
            case REL_MOVE:
            case LOOK_AND_REL_MOVE:
                super.rawPacket.getIntegers().write(1, (int) (position.getX() * 4096));
                super.rawPacket.getIntegers().write(2, (int) (position.getY() * 4096));
                super.rawPacket.getIntegers().write(3, (int) (position.getZ() * 4096));
                break;
            case LOOK:
                if (teleport) {
                    setType(MoveType.TELEPORT);
                } else {
                    setType(MoveType.LOOK_AND_REL_MOVE);
                    super.rawPacket.getIntegers().write(1, (int) (position.getX() * 4096));
                    super.rawPacket.getIntegers().write(2, (int) (position.getY() * 4096));
                    super.rawPacket.getIntegers().write(3, (int) (position.getZ() * 4096));
                    break;
                }
            case TELEPORT:
                super.rawPacket.getDoubles().write(0, position.getX());
                super.rawPacket.getDoubles().write(1, position.getY());
                super.rawPacket.getDoubles().write(2, position.getZ());
                break;
        }
    }

    private void setType(MoveType newType) {
        if (newType == type) return;
        super.rawPacket = new PacketContainer(type.getPacketType());
        super.rawPacket.getModifier().writeDefaults();
        setNewPosition(position, newType == MoveType.TELEPORT);
        setNewDirection(direction);
        setOnGround(onGround);
    }

    @Override
    public MoveType getMoveType() {
        return type;
    }

    @Override
    public boolean isOnGround() {
        return onGround;
    }

    @Override
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
        super.rawPacket.getBooleans().write(0, onGround);
    }

    @Override
    public double[] getPitchYaw() {
        return new double[]{pitch, yaw};
    }

    @Override
    public void setPitchYaw(double pitch, double yaw) {
        setNewDirection(vectorFromAngles(pitch, yaw));
    }
}
