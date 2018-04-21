package net.blitzcube.peapi.entity.modifier.modifiers;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.blitzcube.peapi.api.entity.modifier.IEntityModifier;
import net.blitzcube.peapi.api.entity.modifier.IModifiableEntity;

/**
 * Created by iso2013 on 4/18/2018.
 */
public class GenericModifier<T> implements IEntityModifier<T> {
    final WrappedDataWatcher.Serializer serializer;
    final int index;
    private final Class<T> clazz;
    private final String label;
    private final T def;

    public GenericModifier(Class<T> clazz, int index, String label, T def) {
        this.clazz = clazz;
        this.serializer = clazz != null ? WrappedDataWatcher.Registry.get(clazz) : null;
        this.index = index;
        this.label = label;
        this.def = def;
    }

    public Class<T> getFieldType() {
        return clazz;
    }

    public String getLabel() {
        return label;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getValue(IModifiableEntity target) {
        Object val = target.read(index);
        if (!clazz.equals(val.getClass()))
            throw new IllegalStateException("Read inappropriate type from modifiable entity!");
        return (T) target.read(index);
    }

    @Override
    public void setValue(IModifiableEntity target, T newValue) {
        if (newValue != null) {
            target.write(index, newValue, serializer);
        } else unsetValue(target);
    }

    @Override
    public void unsetValue(IModifiableEntity target) {
        target.clear(index);
    }

    @Override
    public void unsetValue(IModifiableEntity target, boolean setToDefault) {
        if (setToDefault)
            setValue(target, def);
        else unsetValue(target);
    }

    @Override
    public boolean specifies(IModifiableEntity target) {
        return target.contains(index);
    }
}
