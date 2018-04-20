package net.blitzcube.peapi.api.entity;

import org.bukkit.entity.EntityType;

import java.util.List;

/**
 * Created by iso2013 on 4/20/2018.
 */
public interface IEntityModifierRegistry {
    List<IEntityModifier> lookup(EntityType type);

    IEntityModifier lookup(EntityType type, String label);

    List<IEntityModifier> lookup(EntityType type, Class field);
}
