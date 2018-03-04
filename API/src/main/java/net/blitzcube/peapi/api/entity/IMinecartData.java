package net.blitzcube.peapi.api.entity;

public interface IMinecartData extends IEntityData {
    Integer getShakingPower();

    void setShakingPower(Integer value);

    Integer getShakingDirection();

    void setShakingDirection(Integer value);

    Float getShakingMultiplier();

    void setShakingMultiplier(Float value);

    Integer getCustomBlockID();

    void setCustomBlockID(Integer value);

    Integer getCustomBlockY();

    void setCustomBlockY(Integer value);

    Boolean isCustomBlockShown();

    void setCustomBlockShown(Boolean value);

    void unsetShakingPower();

    void unsetShakingDirection();

    void unsetShakingMultiplier();

    void unsetCustomBlockID();

    void unsetCustomBlockY();

    void unsetCustomBlockShown();

    void resetToDefault();

    void clearAll();
}
