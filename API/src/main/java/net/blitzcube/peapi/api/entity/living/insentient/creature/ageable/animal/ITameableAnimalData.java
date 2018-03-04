package net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal;

import com.google.common.base.Optional;
import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.IAnimalData;
import org.bukkit.entity.Entity;

import java.util.UUID;

public interface ITameableAnimalData extends IAnimalData {
    Boolean isSitting();

    Boolean isAngry();

    Boolean isTamed();

    Optional<UUID> getOwner();

    void setOwner(Optional<UUID> value);

    void setSitting(boolean value);

    void setAngry(boolean value);

    void setTamed(boolean value);

    void unsetSitting(Entity e);

    void unsetAngry(Entity e);

    void unsetTamed(Entity e);

    void unsetOwner();

    void resetToDefault();

    void clearAll();
}
