package net.blitzcube.peapi.api.entity;

import org.bukkit.entity.Entity;

public interface IArrowData extends IEntityData {
    Boolean isCritical();

    void setCritical(boolean value);

    void unsetCritical(Entity e);

    void resetToDefault();

    void clearAll();
}
