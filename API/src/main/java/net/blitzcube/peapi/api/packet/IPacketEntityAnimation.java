package net.blitzcube.peapi.api.packet;

/**
 * Created by iso2013 on 4/21/2018.
 */
public interface IPacketEntityAnimation extends IPacketEntity {
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
