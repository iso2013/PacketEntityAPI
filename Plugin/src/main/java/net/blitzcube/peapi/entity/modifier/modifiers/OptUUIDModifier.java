package net.blitzcube.peapi.entity.modifier.modifiers;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.blitzcube.peapi.api.entity.modifier.IModifiableEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by iso2013 on 4/20/2018.
 */
public class OptUUIDModifier extends OptModifier<UUID> {
    private final WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(UUID.class, true);

    public OptUUIDModifier(int index, String label, Optional<UUID> def) {
        super(UUID.class, index, label, def);
    }

    public Class getOptionalType() {
        return UUID.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<UUID> getValue(IModifiableEntity target) {
        Object val = target.read(super.index);
        if (val == null) return null;
        if (!(val instanceof Optional))
            throw new IllegalStateException("Read inappropriate type from modifiable entity!");
        Optional<UUID> bp = (Optional<UUID>) val;
        if (!bp.isPresent()) return Optional.empty();
        return bp;
    }

    @Override
    public void setValue(IModifiableEntity target, Optional<UUID> newValue) {
        if (newValue != null) {
            target.write(
                    super.index,
                    newValue,
                    serializer
            );
        } else super.unsetValue(target);
    }
}
