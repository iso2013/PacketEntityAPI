package net.blitzcube.peapi.api.packet;

/**
 * @author iso2013
 * @version 0.1
 * @since 2018-04-21
 */
public interface IEntityAnimationPacket extends IEntityPacket {
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
