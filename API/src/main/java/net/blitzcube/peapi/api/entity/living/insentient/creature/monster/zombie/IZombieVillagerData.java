package net.blitzcube.peapi.api.entity.living.insentient.creature.monster.zombie;

import net.blitzcube.peapi.api.entity.living.insentient.creature.monster.IZombieData;

public interface IZombieVillagerData extends IZombieData {
    Boolean isConverting();

    Integer getProfession();

    void setProfession(Integer value);

    void setConverting(Boolean value);

    void unsetConverting();

    void unsetProfession();

    void resetToDefault();

    void clearAll();
}
