package net.blitzcube.peapi.api.entity.living.insentient.creature.monster;

import net.blitzcube.peapi.api.entity.living.insentient.creature.IMonsterData;
import org.bukkit.entity.Entity;

public interface IVexData extends IMonsterData {
    Boolean isAttackMode();

    void setAttackMode(boolean value);

    void unsetAttackMode(Entity e);

    void resetToDefault();

    void clearAll();
}
