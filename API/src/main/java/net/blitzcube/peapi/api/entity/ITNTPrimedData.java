package net.blitzcube.peapi.api.entity;

public interface ITNTPrimedData extends IEntityData {
    Integer getFuseTime();

    void setFuseTime(Integer value);

    void unsetFuseTime();

    void resetToDefault();

    void clearAll();
}
