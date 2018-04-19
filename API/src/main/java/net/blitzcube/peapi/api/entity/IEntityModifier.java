package net.blitzcube.peapi.api.entity;

/**
 * Created by iso2013 on 4/18/2018.
 */
public interface IEntityModifier<T> {
    T getValue(IModifiableEntity target);

    void setValue(IModifiableEntity target, T newValue);

    void unsetValue(IModifiableEntity target);

    void unsetValue(IModifiableEntity target, boolean setToDefault);

    boolean specifies(IModifiableEntity target);

    String getLabel();
}
