package net.blitzcube.peapi.api.entity.modifier;

import org.bukkit.entity.EntityType;

import java.util.Set;

/**
 * Created by iso2013 on 4/20/2018.
 */
public interface IEntityModifierRegistry {
    Set<IEntityModifier> lookup(EntityType type);

    IEntityModifier lookup(EntityType type, String label);

    <T> Set<IEntityModifier<T>> lookup(EntityType type, Class<? extends T> field);

    <T> IEntityModifier<T> lookup(EntityType type, String label, Class<? extends T> field);

    <T> Set<IEntityModifier<T>> lookup(EntityType type, Class<? extends T> field, boolean optional);

    <T> IEntityModifier<T> lookup(EntityType type, String label, Class<? extends T> field, boolean optional);
}
