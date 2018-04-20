package net.blitzcube.peapi.entity.modifiers;

import com.google.common.base.Optional;

/**
 * Created by iso2013 on 4/20/2018.
 */
public class OptModifier<T> extends GenericModifier<Optional<T>> {
    private Class<T> clazz;

    public OptModifier(Class<T> clazz, int index, String label, Optional<T> def) {
        super(null, index, label, def);
        this.clazz = clazz;
    }

    public Class getOptionalType() {
        return clazz;
    }
}
