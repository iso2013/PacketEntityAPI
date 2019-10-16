package net.blitzcube.peapi.entity.modifier.modifiers;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.blitzcube.peapi.api.entity.modifier.IModifiableEntity;
import net.blitzcube.peapi.util.ReflectUtil;
import org.bukkit.Particle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by iso2013 on 4/18/2018.
 */
public class ParticleModifier extends GenericModifier<Particle> {
    private static final Class<?> nmsParticleClazz;
    private static final Method toBukkitParticle;
    private static final Method toNMSParticle;

    static {
        Class<?> particleClazz = ReflectUtil.getCBClass("CraftParticle");
        
        nmsParticleClazz = ReflectUtil.getNMSClass("ParticleParam");
        toBukkitParticle = ReflectUtil.getMethod(
                particleClazz, "toBukkit", ReflectUtil.getNMSClass("ParticleType")
        );
        toNMSParticle = ReflectUtil.getMethod(particleClazz, "toNMS", Particle.class);
    }

    public ParticleModifier(int index, String label, Particle def) {
        super(null, index, label, def);
    }

    @Override
    public Particle getValue(IModifiableEntity target) {
        try {
            return (Particle) toBukkitParticle.invoke(target.read(super.index));
        } catch (IllegalAccessException | InvocationTargetException | NullPointerException e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot use particle modifier on this version!");
        }
    }

    @Override
    public void setValue(IModifiableEntity target, Particle newValue) {
        try {
            target.write(
                    super.index,
                    toNMSParticle.invoke(newValue),
                    WrappedDataWatcher.Registry.get(nmsParticleClazz)
            );
        } catch (IllegalAccessException | InvocationTargetException | NullPointerException e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot use particle modifier on this version!");
        }
    }

    @Override
    public Class<?> getFieldType() {
        return Particle.class;
    }
}
