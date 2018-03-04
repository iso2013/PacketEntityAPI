package net.blitzcube.peapi.api.entity;

public interface IBoatData extends IEntityData {
    Integer getTimeSinceLastHit();

    void setTimeSinceLastHit(Integer value);

    Integer getForwardDirection();

    void setForwardDirection(Integer value);

    Float getDamageTaken();

    void setDamageTaken(Float value);

    Integer getType();

    void setType(Integer value);

    Boolean isRightPaddleTurning();

    Boolean isLeftPaddleTurning();

    void setRightPaddleTurning(Boolean value);

    void setLeftPaddleTurning(Boolean value);

    void unsetTimeSinceLastHit();

    void unsetForwardDirection();

    void unsetDamageTaken();

    void unsetType();

    void unsetRightPaddleTurning();

    void unsetLeftPaddleTurning();

    void resetToDefault();

    void clearAll();
}
