package net.blitzcube.peapi.entity.modifier.modifiers;

import java.util.Optional;

/**
 * Created by iso2013 on 4/20/2018.
 */
public class OptModifier<T> extends GenericModifier<Optional<T>> {
    private final Class<T> clazz;

    OptModifier(Class<T> clazz, int index, String label, Optional<T> def) {
        super(null, index, label, def);
        this.clazz = clazz;
    }

    public Class getOptionalType() {
        return clazz;
    }
}
