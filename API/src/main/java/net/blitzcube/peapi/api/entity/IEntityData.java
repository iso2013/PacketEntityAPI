package net.blitzcube.peapi.api.entity;

import org.bukkit.entity.Entity;

public interface IEntityData {
    Boolean isInvisible();

    Boolean isGlowing();

    Boolean isOnFire();

    Boolean isCrouching();

    Boolean isSprinting();

    Boolean isElytraFlying();

    Integer getRemainingAir();

    void setRemainingAir(Integer value);

    String getCustomName();

    void setCustomName(String value);

    Boolean isCustomNameVisible();

    Boolean isSilent();

    Boolean isGravity();

    void setInvisible(boolean value);

    void setGlowing(boolean value);

    void setOnFire(boolean value);

    void setCrouching(boolean value);

    void setSprinting(boolean value);

    void setElytraFlying(boolean value);

    void setCustomNameVisible(Boolean value);

    void setSilent(Boolean value);

    void setGravity(Boolean value);

    void unsetInvisible(Entity e);

    void unsetGlowing(Entity e);

    void unsetOnFire(Entity e);

    void unsetCrouching(Entity e);

    void unsetSprinting(Entity e);

    void unsetElytraFlying(Entity e);

    void unsetRemainingAir();

    void unsetCustomName();

    void unsetCustomNameVisible();

    void unsetSilent();

    void unsetGravity();

    void resetToDefault();

    void clearAll();
}
