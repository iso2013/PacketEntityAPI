package net.blitzcube.peapi.entity.modifier.modifiers;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.blitzcube.peapi.api.entity.modifier.IModifiableEntity;
import org.bukkit.Particle;

/**
 * Created by iso2013 on 8/20/2018.
 */
public class ParticleModifier extends GenericModifier<Particle> {
    private final WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Integer.class);

    public ParticleModifier(int index, String label, int def) {
        super(null, index, label, Particle.values()[def]);
    }

    private Particle fromWrapped(EnumWrappers.Particle wrapped) {
        if (wrapped == null) return null;
        Particle t;
        try {
            t = Particle.valueOf(wrapped.name());
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot decode invalid particle type " + wrapped.name() + "!");
        }
        return t;
    }

    @Override
    public Particle getValue(IModifiableEntity target) {
        return fromWrapped((EnumWrappers.Particle) target.read(super.index));
    }

    @Override
    public void setValue(IModifiableEntity target, Particle newValue) {
        if (newValue != null) {
            EnumWrappers.Particle wrapped = wrap(newValue);
            if (wrapped == null) {
                throw new IllegalArgumentException("Cannot encode invalid particle type " + newValue.name() + "!");
            }
            target.write(super.index, wrapped.getId(), serializer);
        } else super.unsetValue(target);
    }

    private EnumWrappers.Particle wrap(Particle toWrap) {
        try {
            return EnumWrappers.Particle.valueOf(toWrap.name());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Class<Particle> getFieldType() {
        return Particle.class;
    }
}
