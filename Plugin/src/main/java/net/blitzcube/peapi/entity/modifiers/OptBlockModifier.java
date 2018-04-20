package net.blitzcube.peapi.entity.modifiers;

import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.google.common.base.Optional;
import net.blitzcube.peapi.api.entity.IModifiableEntity;
import org.bukkit.material.MaterialData;

/**
 * Created by iso2013 on 4/20/2018.
 */
public class OptBlockModifier extends OptModifier<MaterialData> {
    private final WrappedDataWatcher.Serializer serializer =
            WrappedDataWatcher.Registry.getBlockDataSerializer(true);

    public OptBlockModifier(int index, String label, Optional<MaterialData> def) {
        super(MaterialData.class, index, label, def);
    }

    @Override
    public Optional<MaterialData> getValue(IModifiableEntity target) {
        //TODO: Make this less gross.
        Object val = target.read(super.index);
        if (val == null) return null;
        if (!Optional.class.isAssignableFrom(val.getClass()))
            throw new IllegalStateException("Read inappropriate type from modifiable entity!");
        Optional<WrappedBlockData> bp = (Optional<WrappedBlockData>) val;
        if (!bp.isPresent()) return Optional.absent();
        return Optional.of(new MaterialData(bp.get().getType(), (byte) bp.get().getData()));
    }

    @Override
    public void setValue(IModifiableEntity target, Optional<MaterialData> newValue) {
        if (newValue != null) {
            if (newValue.isPresent()) {
                MaterialData v = newValue.get();
                WrappedBlockData wbd = WrappedBlockData.createData(v.getItemType(), 0);

                target.write(
                        index,
                        Optional.of(wbd.getHandle()),
                        serializer
                );
            } else {
                target.write(super.index, Optional.absent(), serializer);
            }
        } else super.unsetValue(target);
    }
}
