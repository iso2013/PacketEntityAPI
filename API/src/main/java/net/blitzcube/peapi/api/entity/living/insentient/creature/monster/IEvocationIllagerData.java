package net.blitzcube.peapi.api.entity.living.insentient.creature.monster;

import net.blitzcube.peapi.api.entity.living.insentient.creature.IMonsterData;

public interface IEvocationIllagerData extends IMonsterData {
    Byte getSpell();

    void setSpell(Byte value);

    void unsetSpell();

    void resetToDefault();

    void clearAll();
}
