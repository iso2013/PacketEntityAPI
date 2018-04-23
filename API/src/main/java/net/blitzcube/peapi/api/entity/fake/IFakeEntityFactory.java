package net.blitzcube.peapi.api.entity.fake;

import net.blitzcube.peapi.api.entity.hitbox.IHitbox;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

/**
 * Created by iso2013 on 4/21/2018.
 */
public interface IFakeEntityFactory {
    IHitbox createHitboxFromType(EntityType type);

    IHitbox createHitbox(Vector min, Vector max);

    IFakeEntity createFakeEntity(EntityType type);

    IFakeEntity createFakeEntity(EntityType type, boolean lookupModifiers);

    boolean isFakeEntity(int entityID);

    IFakeEntity getFakeByID(int entityID);
}
