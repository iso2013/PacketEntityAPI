package net.blitzcube.peapi.entity.modifier.modifiers;

import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.blitzcube.peapi.api.entity.modifier.IModifiableEntity;
import org.bukkit.util.Vector;

import java.util.Optional;

/**
 * Created by iso2013 on 4/20/2018.
 */
public class OptPositionModifier extends OptModifier<Vector> {
    private final WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.getBlockPositionSerializer
            (true);

    public OptPositionModifier(int index, String label, Optional<Vector> def) {
        super(Vector.class, index, label, def);
    }

    public Class getOptionalType() {
        return BlockPosition.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<Vector> getValue(IModifiableEntity target) {
        Object val = target.read(super.index);
        if (val == null) return null;
        if (!(val instanceof Optional))
            throw new IllegalStateException("Read inappropriate type from modifiable entity!");
        Optional<BlockPosition> bp = (Optional<BlockPosition>) val;
        return bp.map(BlockPosition::toVector);
    }

    @Override
    public void setValue(IModifiableEntity target, Optional<Vector> newValue) {
        if (newValue != null) {
            if (newValue.isPresent()) {
                Vector v = newValue.get();
                target.write(
                        super.index,
                        Optional.of(BlockPosition.getConverter().getGeneric(new BlockPosition(v.getBlockX(), v
                                .getBlockY(), v.getBlockZ()))),
                        serializer
                );
            } else {
                target.write(super.index, Optional.empty(), serializer);
            }
        } else super.unsetValue(target);
    }
}
