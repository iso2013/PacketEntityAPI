package net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal;

import com.google.common.base.Optional;
import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.IAnimalData;
import org.bukkit.entity.Entity;

import java.util.UUID;

public interface IAbstractHorseData extends IAnimalData {
    Boolean isEating();

    Boolean isRearing();

    Boolean isMouthOpen();

    Boolean isTame();

    Boolean isSaddled();

    Boolean isBred();

    Optional<UUID> getOwner();

    void setOwner(Optional<UUID> value);

    void setEating(boolean value);

    void setRearing(boolean value);

    void setMouthOpen(boolean value);

    void setTame(boolean value);

    void setSaddled(boolean value);

    void setBred(boolean value);

    void unsetEating(Entity e);

    void unsetRearing(Entity e);

    void unsetMouthOpen(Entity e);

    void unsetTame(Entity e);

    void unsetSaddled(Entity e);

    void unsetBred(Entity e);

    void unsetOwner();

    void resetToDefault();

    void clearAll();
}
