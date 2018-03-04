package net.blitzcube.peapi.api.entity;

public interface IFishingHookData extends IEntityData {
    Integer getHookedID();

    void setHookedID(Integer value);

    void unsetHookedID();

    void resetToDefault();

    void clearAll();
}
