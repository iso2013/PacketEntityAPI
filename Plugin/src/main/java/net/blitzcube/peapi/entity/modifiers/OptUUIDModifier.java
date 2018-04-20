package net.blitzcube.peapi.entity.modifiers;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.google.common.base.Optional;
import net.blitzcube.peapi.api.entity.IModifiableEntity;

import java.util.UUID;

/**
 * Created by iso2013 on 4/20/2018.
 */
public class OptUUIDModifier extends GenericModifier<Optional<UUID>> {
    private final WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(UUID.class, true);

    public OptUUIDModifier(int index, String label, Optional<UUID> def) {
        super(null, index, label, def);
    }

    @Override
    public Optional<UUID> getValue(IModifiableEntity target) {
        //TODO: Make this less gross.
        Object val = target.read(super.index);
        if (val == null) return null;
        if (!Optional.class.isAssignableFrom(val.getClass()))
            throw new IllegalStateException("Read inappropriate type from modifiable entity!");
        Optional<UUID> bp = (Optional<UUID>) val;
        if (!bp.isPresent()) return Optional.absent();
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
