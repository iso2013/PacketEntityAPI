package net.iso2013.peapi.entity.modifier.modifiers;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.iso2013.peapi.api.entity.modifier.ModifiableEntity;
import org.bukkit.Color;

/**
 * Created by iso2013 on 6/8/2018.
 */
public class ColorModifier extends GenericModifier<Color> {
    private final WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Integer.class);

    public ColorModifier(int index, String label, int def) {
        super(null, index, label, def >= 0 ? Color.fromRGB(def) : null);
    }

    @Override
    public Color getValue(ModifiableEntity target) {
        Integer i = (Integer) target.read(super.index);
        return i != null && i >= 0 ? Color.fromRGB(i) : null;
    }

    @Override
    public void setValue(ModifiableEntity target, Color newValue) {
        target.write(super.index, newValue != null ? newValue.asRGB() : -1, serializer);
    }

    @Override
    public Class<Color> getFieldType() {
        return Color.class;
    }
}
