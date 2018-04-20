package net.blitzcube.peapi.entity.modifiers;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.blitzcube.peapi.api.entity.IModifiableEntity;

/**
 * Created by iso2013 on 4/20/2018.
 */
public class ByteBitmaskModifier extends GenericModifier<Byte> {
    private final WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class);
    private final byte def;
    private final byte mask;

    public ByteBitmaskModifier(int index, byte mask, String label, Byte def) {
        super(Byte.class, index, label, def);
        this.def = def;
        this.mask = mask;
    }

    public void clear(IModifiableEntity target) {
        target.clear(super.index);
    }

    @Override
    public Byte getValue(IModifiableEntity target) {
        return (byte) (getOrDef(target) & mask);
    }

    @Override
    public void setValue(IModifiableEntity target, Byte newValue) {
        target.write(
                super.index,
                (byte) ((getOrDef(target) & ~mask) | newValue),
                serializer
        );
    }

    private byte getOrDef(IModifiableEntity target) {
        Object val = target.read(super.index);
        if (val != null && !val.getClass().equals(Byte.class))
            throw new IllegalStateException("Read inappropriate type from modifiable entity!");
        return val != null ? (Byte) val : def;
    }

    @Override
    public void unsetValue(IModifiableEntity target) {
        super.unsetValue(target, true);
    }
}
