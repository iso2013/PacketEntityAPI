package net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.abstracthorse.chestedhorse;

import net.blitzcube.peapi.api.entity.living.insentient.creature.ageable.animal.abstracthorse.IChestedHorseData;

public interface ILlamaData extends IChestedHorseData {
    Integer getStrength();

    void setStrength(Integer value);

    Integer getCarpetColor();

    void setCarpetColor(Integer value);

    Integer getVariant();

    void setVariant(Integer value);

    void unsetStrength();

    void unsetCarpetColor();

    void unsetVariant();

    void resetToDefault();

    void clearAll();
}
