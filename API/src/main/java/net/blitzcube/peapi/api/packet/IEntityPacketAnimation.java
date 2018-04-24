package net.blitzcube.peapi.api.packet;

/**
 * Created by iso2013 on 4/21/2018.
 */
public interface IEntityPacketAnimation extends IEntityPacket {
    /**
     * Gets the animation that this packet is invoking
     *
     * @return the animation
     */
    AnimationType getAnimation();

    /**
     * Set the animation that this packet is invoking
     *
     * @param type the new animation type
     */
    void setAnimation(AnimationType type);

    /**
     * An animation type
     */
    enum AnimationType {
        SWING_ARM,
        TAKE_DAMAGE,
        LEAVE_BED,
        SWING_OFFHAND,
        CRITICAL_EFFECT,
        MAGIC_CRITICAL_EFFECT
    }
}
