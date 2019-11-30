package net.iso2013.peapi.api.entity.modifier;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.Optional;

/**
 * @author iso2013
 * @version 0.1
 * @since 2018-04-20
 */
public interface EntityModifierRegistry {
    /**
     * Look up all of the possible modifiers for the given entity type.
     * <br>
     * Use {@link EntityModifierRegistry#lookup(EntityType, Class)} if you know the type; it returns a
     * parameterized object instead of a generic.
     *
     * @param type the type to search for
     * @return a list containing all of the possible modifiers, ordered by index
     */
    List<EntityModifier<?>> lookup(EntityType type);

    /**
     * Look up all of the possible modifiers for the given entity class.
     * <br>
     * Use {@link EntityModifierRegistry#lookup(Class, Class)} if you know the type; it returns a
     * parameterized object instead of a generic.
     *
     * @param type the class to search for
     * @return a list containing all of the possible modifiers, ordered by index
     */
    List<EntityModifier<?>> lookup(Class<? extends Entity> type);

    /**
     * Look up a modifier for a given entity type by using its label ({@link EntityModifier#getLabel()}.
     * <br>
     * Use {@link EntityModifierRegistry#lookup(EntityType, String, Class)} if you know the field type; it returns a
     * parameterized object instead of a generic.
     *
     * @param type  the type to search for
     * @param label the label of the modifier to be returned
     * @return the modifier, or null if nothing matched the given parameters
     */
    EntityModifier<?> lookup(EntityType type, String label);

    /**
     * Look up a modifier for a given entity class by using its label ({@link EntityModifier#getLabel()}.
     * <br>
     * Use {@link EntityModifierRegistry#lookup(Class, String, Class)} if you know the field type; it returns a
     * parameterized object instead of a generic.
     *
     * @param type  the class to search for
     * @param label the label of the modifier to be returned
     * @return the modifier, or null if nothing matched the given parameters
     */
    EntityModifier<?> lookup(Class<? extends Entity> type, String label);

    /**
     * Look up a modifier for a given entity type by using its label ({@link EntityModifier#getLabel()}) and the field
     * type. For Optional-wrapped fields, see {@link #lookupOptional(EntityType, String, Class)} .
     *
     * @param type  the entity type to search for
     * @param label the label of the modifier to be returned
     * @param field the class representing the type of the field to be returned
     * @param <T>   the type of the field to be returned
     * @return the modifier, or null if nothing matched the given parameters
     */
    <T> EntityModifier<T> lookup(EntityType type, String label, Class<T> field);

    /**
     * Look up a modifier for a given entity class by using its label ({@link EntityModifier#getLabel()}) and the field
     * type. For Optional-wrapped fields, see {@link #lookupOptional(Class, String, Class)} .
     *
     * @param type  the entity class to search for
     * @param label the label of the modifier to be returned
     * @param field the class representing the type of the field to be returned
     * @param <T>   the type of the field to be returned
     * @return the modifier, or null if nothing matched the given parameters
     */
    <T> EntityModifier<T> lookup(Class<? extends Entity> type, String label, Class<T> field);

    /**
     * Look up a modifier for a given entity type by using its label ({@link EntityModifier#getLabel()}) and the field
     * type. This method gets modifiers that are wrapped with an Optional&lt;&gt;.
     *
     * @param type  the entity type to search for
     * @param label the label of the modifier to be returned
     * @param field the class representing the type of the field to be returned
     * @param <T>   the type of the field to be returned
     * @return the modifier, or null if nothing matched the given parameters
     */
    <T> EntityModifier<Optional<T>> lookupOptional(EntityType type, String label, Class<T> field);

    /**
     * Look up a modifier for a given entity class by using its label ({@link EntityModifier#getLabel()}) and the field
     * type. This method gets modifiers that are wrapped with an Optional&lt;&gt;.
     *
     * @param type  the entity class to search for
     * @param label the label of the modifier to be returned
     * @param field the class representing the type of the field to be returned
     * @param <T>   the type of the field to be returned
     * @return the modifier, or null if nothing matched the given parameters
     */
    <T> EntityModifier<Optional<T>> lookupOptional(Class<? extends Entity> type, String label, Class<T> field);

    /**
     * Look up all of the possible modifiers for the given entity type that have a specific field type. For Optional-
     * wrapped fields, see {@link #lookupOptional(EntityType, Class)}.
     *
     * @param <T>   the field type
     * @param type  the entity type to search for
     * @param field the class representing the type of the field to search for
     * @return a list containing all of the possible modifiers, ordered by index
     */
    <T> List<EntityModifier<T>> lookup(EntityType type, Class<T> field);

    /**
     * Look up all of the possible modifiers for the given entity class that have a specific field type. For Optional-
     * wrapped fields, see {@link #lookupOptional(Class, Class)}.
     *
     * @param <T>   the field type
     * @param type  the entity class to search for
     * @param field the class representing the type of the field to search for
     * @return a list containing all of the possible modifiers, ordered by index
     */
    <T> List<EntityModifier<T>> lookup(Class<? extends Entity> type, Class<T> field);

    /**
     * Look up a modifier for a given entity type by using its label ({@link EntityModifier#getLabel()}) and the field
     * type. This method gets modifiers that are wrapped with an Optional&lt;&gt;.
     *
     * @param <T>   the type of the field to be returned
     * @param type  the entity type to search for
     * @param field the class representing the type of the field to be returned
     * @return a list containing all of the possible modifiers, ordered by index
     */
    <T> List<EntityModifier<Optional<T>>> lookupOptional(EntityType type, Class<T> field);

    /**
     * Look up a modifier for a given entity class by using its label ({@link EntityModifier#getLabel()}) and the field
     * type. This method gets modifiers that are wrapped with an Optional&lt;&gt;.
     *
     * @param <T>   the type of the field to be returned
     * @param type  the entity class to search for
     * @param field the class representing the type of the field to be returned
     * @return a list containing all of the possible modifiers, ordered by index
     */
    <T> List<EntityModifier<Optional<T>>> lookupOptional(Class<? extends Entity> type, Class<T> field);
}
