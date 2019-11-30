package net.iso2013.peapi.entity.modifier.modifiers;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.iso2013.peapi.api.entity.modifier.EntityModifier;
import net.iso2013.peapi.api.entity.modifier.ModifiableEntity;

/**
 * Created by iso2013 on 4/18/2018.
 */
public class GenericModifier<T> implements EntityModifier<T> {
    final int index;
    final String label;
    final T def;
    private final WrappedDataWatcher.Serializer serializer;
    private final Class<?> clazz;

    public GenericModifier(Class<?> clazz, int index, String label, T def) {
        this.clazz = clazz;
        this.serializer = clazz != null ? WrappedDataWatcher.Registry.get(clazz) : null;
        this.index = index;
        this.label = label;
        this.def = def;
    }

    public Class<?> getFieldType() {
        return clazz;
    }

    public String getLabel() {
        return label;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getValue(ModifiableEntity target) {
        Object val = target.read(index);
        if (val == null) return null;
        if (!clazz.equals(val.getClass()))
            throw new IllegalStateException("Read inappropriate type from modifiable entity!");
        return (T) target.read(index);
    }

    @Override
    public void setValue(ModifiableEntity target, T newValue) {
        if (newValue != null) {
            target.write(index, newValue, serializer);
        } else unsetValue(target);
    }

    @Override
    public void unsetValue(ModifiableEntity target) {
        target.clear(index);
    }

    @Override
    public void unsetValue(ModifiableEntity target, boolean setToDefault) {
        if (setToDefault)
            setValue(target, def);
        else unsetValue(target);
    }

    @Override
    public boolean specifies(ModifiableEntity target) {
        return target.contains(index);
    }
}
