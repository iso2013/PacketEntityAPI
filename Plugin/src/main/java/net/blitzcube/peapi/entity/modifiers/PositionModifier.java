package net.blitzcube.peapi.entity.modifiers;

import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.blitzcube.peapi.api.entity.IModifiableEntity;
import org.bukkit.util.Vector;

/**
 * Created by iso2013 on 4/18/2018.
 */
public class PositionModifier extends GenericModifier<Vector> {
    private final WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(BlockPosition.class);

    public PositionModifier(int index, String label, Vector def) {
        super(Vector.class, index, label, def);
    }

    @Override
    public Vector getValue(IModifiableEntity target) {
        BlockPosition bp = (BlockPosition) target.read(super.index);
        if (bp == null) return null;
        return bp.toVector();
    }

    @Override
    public void setValue(IModifiableEntity target, Vector newValue) {
        if (newValue != null) {
            target.write(
                    super.index,
                    new BlockPosition(newValue.getBlockX(), newValue.getBlockY(), newValue.getBlockZ()),
                    serializer
            );
        } else super.unsetValue(target);
    }
}
