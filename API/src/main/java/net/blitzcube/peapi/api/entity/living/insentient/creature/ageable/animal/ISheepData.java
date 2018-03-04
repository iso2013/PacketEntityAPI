package net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal;

import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.IAnimalData;
import org.bukkit.entity.Entity;

public interface ISheepData extends IAnimalData {
    Boolean isSheared();

    Boolean isColor();

    void setSheared(boolean value);

    void setColor(boolean value);

    void unsetSheared(Entity e);

    void unsetColor(Entity e);

    void resetToDefault();

    void clearAll();
}
