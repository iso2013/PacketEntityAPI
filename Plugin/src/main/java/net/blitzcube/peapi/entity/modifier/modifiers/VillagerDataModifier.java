package net.blitzcube.peapi.entity.modifier.modifiers;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.blitzcube.peapi.api.entity.modifier.IModifiableEntity;
import net.blitzcube.peapi.api.entity.wrappers.VillagerData;
import net.blitzcube.peapi.util.ReflectUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Villager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by iso2013 on 10/12/19.
 */
public class VillagerDataModifier extends GenericModifier<VillagerData> {
    private static Class<?> mcvd;
    private static Constructor mcvdCtor;

    private static Object villagerType;
    private static Object villagerProf;

    private static Method mcGetType;
    private static Method mcGetProf;
    private static Method mcGetLevel;
    private static Method rbGetKey;
    private static Method rbGet;
    private static Method mkGetKey;
    private static Method cnkToMinecraft;

    static {
        try {
            mcvd = ReflectUtil.getNMSClass("VillagerData");
            mcvdCtor = ReflectUtil.getConstructor(
                    mcvd, ReflectUtil.getNMSClass("VillagerType"), ReflectUtil.getNMSClass("VillagerProfession")
            );
            Class<?> iRegClazz = ReflectUtil.getNMSClass("IRegistry");
            villagerType = ReflectUtil.getField(iRegClazz, "VILLAGER_TYPE").get(null);
            villagerProf = ReflectUtil.getField(iRegClazz, "VILLAGER_PROFESSION").get(null);

            mcGetType = ReflectUtil.getMethod(mcvd, "getType");
            mcGetProf = ReflectUtil.getMethod(mcvd, "getProfession");
            mcGetLevel = ReflectUtil.getMethod(mcvd, "getLevel");

            Class<?> rbClazz = ReflectUtil.getNMSClass("RegistryBlocks");
            Class<?> mcKClazz = ReflectUtil.getNMSClass("MinecraftKey");
            rbGetKey = ReflectUtil.getMethod(rbClazz, "getKey", Object.class);
            rbGet = ReflectUtil.getMethod(rbClazz, "get", mcKClazz);
            mkGetKey = ReflectUtil.getMethod(mcKClazz, "getKey");
            cnkToMinecraft = ReflectUtil.getMethod(
                    ReflectUtil.getCBClass("util.CraftNamespacedKey"),
                    "toMinecraft",
                    NamespacedKey.class
            );
        } catch (IllegalAccessException e) {
            mcvdCtor = null;
        }
    }

    private final WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(mcvd);

    public VillagerDataModifier(int index, String label, VillagerData villagerData) {
        super(null, index, label, villagerData);
    }

    private static Object toNMS(VillagerData data) {
        try {
            return mcvdCtor.newInstance(
                    rbGet.invoke(villagerType, cnkToMinecraft.invoke(null, data.getType().getKey())),
                    rbGet.invoke(villagerProf, cnkToMinecraft.invoke(null, data.getProfession().getKey())),
                    data.getLevel()
            );
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    private static VillagerData fromNMS(Object data) {
        try {
            return new VillagerData(
                    Villager.Type.valueOf(
                            ((String) mkGetKey.invoke(rbGetKey.invoke(villagerType, mcGetType.invoke(data)))).toUpperCase()
                    ),
                    Villager.Profession.valueOf(
                            ((String) mkGetKey.invoke(rbGetKey.invoke(villagerProf, mcGetProf.invoke(data)))).toUpperCase()
                    ),
                    (int) mcGetLevel.invoke(data)
            );
        } catch (IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    @Override
    public VillagerData getValue(IModifiableEntity target) {
        return fromNMS(target.read(super.index));
    }

    @Override
    public void setValue(IModifiableEntity target, VillagerData newValue) {
        target.write(
                super.index,
                toNMS(newValue),
                serializer
        );
    }

    @Override
    public Class<?> getFieldType() {
        return VillagerData.class;
    }
}
