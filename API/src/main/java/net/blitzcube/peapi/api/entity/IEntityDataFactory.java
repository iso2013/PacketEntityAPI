package net.blitzcube.peapi.api.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * Created by iso2013 on 2/26/2018.
 */
public interface IEntityDataFactory {
    IEntityData provideForType(EntityType t);

    IEntityData wrapEntity(Entity e);
}
