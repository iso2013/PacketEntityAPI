package net.blitzcube.peapi.entity.modifier.modifiers;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.blitzcube.peapi.api.entity.modifier.IModifiableEntity;
import org.bukkit.Bukkit;
import org.bukkit.Particle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by iso2013 on 4/18/2018.
 */
public class ParticleModifier extends GenericModifier<Particle> {
    private static Class<?> nmsParticleClazz;
    private static Method toBukkitParticle;
    private static Method toNMSParticle;

    static {
        try {
            String packageVer = Bukkit.getServer().getClass().getPackage().getName();
            packageVer = packageVer.substring(packageVer.lastIndexOf('.') + 1);

            Class<?> particleClazz = Class.forName("org.bukkit.craftbukkit." + packageVer + ".CraftParticle");
            nmsParticleClazz = Class.forName("net.minecraft.server." + packageVer + ".ParticleParam");

            toBukkitParticle = particleClazz.getDeclaredMethod(
                    "toBukkit",
                    Class.forName("net.minecraft.server." + packageVer + ".ParticleType")
            );
            toNMSParticle = particleClazz.getDeclaredMethod("toNMS", Particle.class);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            toBukkitParticle = null;
            toNMSParticle = null;
        }
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
