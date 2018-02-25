package net.blitzcube.peapi.api.event;

/**
 * Created by iso2013 on 2/24/2018.
 */
public interface IPacketEntityAnimationEvent extends IPacketEntityEvent {
    AnimationType getAnimation();

    void setAnimation(AnimationType type);

    enum AnimationType {
        SWING_ARM,
        TAKE_DAMAGE,
        LEAVE_BED,
        SWING_OFFHAND,
        CRITICAL_EFFECT,
        MAGIC_CRITICAL_EFFECT
    }
}
