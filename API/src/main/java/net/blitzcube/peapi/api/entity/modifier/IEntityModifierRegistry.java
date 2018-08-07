package net.blitzcube.peapi.api.entity.modifier;

import org.bukkit.entity.EntityType;

import java.util.Set;

/**
 * @author iso2013
 * @version 0.1
 * @since 2018-04-20
 */
public interface IEntityModifierRegistry {
    /**
     * Look up all of the possible modifiers for the given entity type.
     * <br>
     * Use {@link IEntityModifierRegistry#lookup(EntityType, Class)} if you know the type; it returns a
     * parameterized object instead of a generic.
     *
     * @param type the type to search for
     * @return a set containing all of the possible modifiers
     */
    Set<IEntityModifier> lookup(EntityType type);

    /**
     * Look up a modifier for a given entity type by using its label ({@link IEntityModifier#getLabel()}.
     * <br>
     * Use {@link IEntityModifierRegistry#lookup(EntityType, String, Class)} if you know the field type; it returns a
     * parameterized object instead of a generic.
     * @param type the type to search for
     * @param label the label of the modifier to be returned
     * @return the modifier, or null if nothing matched the given parameters
     */
    IEntityModifier lookup(EntityType type, String label);

    /**
     * Look up all of the possible modifiers for the given entity type that have a specific field type. For Optional-
     * wrapped fields, see {@link #lookup(EntityType, Class, boolean)}.
     * @param type the entity type to search for
     * @param field the class representing the type of the field to search for
     * @param <T> the field type
     * @return a set containing all of the possible modifiers
     */
    <T> Set<IEntityModifier<T>> lookup(EntityType type, Class<? extends T> field);

    /**
     * Look up a modifier for a given entity type by using its label ({@link IEntityModifier#getLabel()}) and the field
     * type. For Optional-wrapped fields, see {@link #lookup(EntityType, String, Class, boolean)} .
     * @param type the entity type to search for
     * @param label the label of the modifier to be returned
     * @param field the class representing the type of the field to be returned
     * @param <T> the type of the field to be returned
     * @return the modifier, or null if nothing matched the given parameters
     */
    <T> IEntityModifier<T> lookup(EntityType type, String label, Class<? extends T> field);

    /**
     * Look up all of the possible modifiers for the given entity type that have a specific field type. Optionally get
     * modifiers that are wrapped with an Optional&lt;&gt;.
     * @param type the entity type to search for
     * @param field the class representing the type of the field to search for
     * @param optional whether or not to return Optional-wrapped fields
     * @param <T> the field type
     * @return a set containing all of the possible modifiers
     */
    <T> Set<IEntityModifier<T>> lookup(EntityType type, Class<? extends T> field, boolean optional);


    /**
     * Look up a modifier for a given entity type by using its label ({@link IEntityModifier#getLabel()}) and the field
     * type. Optionally get
     * modifiers that are wrapped with an Optional&lt;&gt;.
     * @param type the entity type to search for
     * @param label the label of the modifier to be returned
     * @param field the class representing the type of the field to be returned
     * @param optional whether or not to return an Optional-wrapped field
     * @param <T> the type of the field to be returned
     * @return the modifier, or null if nothing matched the given parameters
     */
    <T> IEntityModifier<T> lookup(EntityType type, String label, Class<? extends T> field, boolean optional);
}
