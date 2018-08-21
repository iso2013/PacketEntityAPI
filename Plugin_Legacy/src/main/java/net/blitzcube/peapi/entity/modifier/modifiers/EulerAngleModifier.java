package net.blitzcube.peapi.entity.modifier.modifiers;

import com.comphenix.protocol.wrappers.Vector3F;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.blitzcube.peapi.api.entity.modifier.IModifiableEntity;
import org.bukkit.util.EulerAngle;

/**
 * Created by iso2013 on 4/18/2018.
 */
public class EulerAngleModifier extends GenericModifier<EulerAngle> {
    private final WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.getVectorSerializer();

    public EulerAngleModifier(int index, String label, EulerAngle def) {
        super(null, index, label, def);
    }

    @Override
    public EulerAngle getValue(IModifiableEntity target) {
        Vector3F vector = (Vector3F) target.read(super.index);
        if (vector == null) return null;
        return new EulerAngle(vector.getX(), vector.getY(), vector.getZ());
    }

    @Override
    public void setValue(IModifiableEntity target, EulerAngle newValue) {
        if (newValue != null) {
            target.write(
                    super.index,
                    Vector3F.getConverter().getGeneric(new Vector3F((float) newValue.getX(), (float) newValue.getY(),
                            (float) newValue.getZ())),
                    serializer
            );
        } else super.unsetValue(target);
    }
}
