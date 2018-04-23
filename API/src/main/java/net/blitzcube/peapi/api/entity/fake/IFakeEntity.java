package net.blitzcube.peapi.api.entity.fake;

import net.blitzcube.peapi.api.entity.hitbox.IHitbox;
import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;
import net.blitzcube.peapi.api.entity.modifier.IEntityModifier;
import net.blitzcube.peapi.api.entity.modifier.IModifiableEntity;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

/**
 * Created by iso2013 on 4/21/2018.
 */
public interface IFakeEntity {
    int getEntityID();

    UUID getUUID();

    Location getLocation();

    void setLocation(Location location);

    EntityType getType();

    IHitbox getHitbox();

    void setHitbox(IHitbox value);

    IEntityIdentifier getIdentifier();

    IModifiableEntity getModifiableEntity();

    Map<String, IEntityModifier> getModifiers();

    boolean checkIntersect(Player target);
}