package net.blitzcube.peapi.api.entity;

import org.bukkit.entity.Entity;

public interface ILivingData extends IEntityData {
    Boolean isHandActive();

    Boolean isOffhand();

    Float getHealth();

    void setHealth(Float value);

    Integer getPotionColor();

    void setPotionColor(Integer value);

    Boolean isPotionAmbient();

    Integer getNumberOfArrowsInEntity();

    void setNumberOfArrowsInEntity(Integer value);

    void setHandActive(boolean value);

    void setOffhand(boolean value);

    void setPotionAmbient(Boolean value);

    void unsetHandActive(Entity e);

    void unsetOffhand(Entity e);

    void unsetHealth();

    void unsetPotionColor();

    void unsetPotionAmbient();

    void unsetNumberOfArrowsInEntity();

    void resetToDefault();

    void clearAll();
}
