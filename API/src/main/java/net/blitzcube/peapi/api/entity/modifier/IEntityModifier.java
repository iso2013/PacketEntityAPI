package net.blitzcube.peapi.api.entity.modifier;

/**
 * @author iso2013
 * @version 0.1
 * @since 2018-04-18
 */
public interface IEntityModifier<T> {
    /**
     * Gets the value that is set for this metadata property for the given entity
     *
     * @param target the modifiable entity to read the value from
     * @return the value that has been retrieved from the parameter
     */
    T getValue(IModifiableEntity target);

    /**
     * Sets a new value for this metadata property for the given entity.
     *
     * @param target   the modifiable entity to write the value to
     * @param newValue the value to write to the parameter
     */
    void setValue(IModifiableEntity target, T newValue);

    /**
     * Removes the set value for this metadata property from the given entity.
     *
     * @param target the entity to remove the value from
     */
    void unsetValue(IModifiableEntity target);

    /**
     * Removes the set value for this metadata property from the given entity, and optionally resets it to the default
     * value.
     *
     * @param target       the entity to remove the value from
     * @param setToDefault whether or not to reset the value to the default
     */
    void unsetValue(IModifiableEntity target, boolean setToDefault);

    /**
     * Checks whether or not the current data of the entity specifies this property.
     *
     * @param target the entity to check
     * @return whether or not this property was contained
     */
    boolean specifies(IModifiableEntity target);

    /**
     * Gets the string identifier that this modifier is loaded and accessed with.
     *
     * @return the identifier for this modifier.
     */
    String getLabel();
}
