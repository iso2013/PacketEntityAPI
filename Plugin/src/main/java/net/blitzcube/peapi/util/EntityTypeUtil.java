package net.blitzcube.peapi.util;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by iso2013 on 10/17/19.
 */
public class EntityTypeUtil {
    // 1.13 and below
    private static BiMap<EntityType, Integer> OBJECTS = HashBiMap.create();
    private static BiMap<EntityType, Integer> ENTITIES = HashBiMap.create();
    // 1.14
    private static Class<?> nmsEntityTypes;
    private static Method etGetName;
    private static Method mkGetKey;

    private static Object entityTypeRegistry;
    private static Method getByID;
    private static Method getByKey;

    private static Method cnkGetKey;
    private static Method getID;

    static {
        nmsEntityTypes = ReflectUtil.getNMSClass("EntityTypes");
        etGetName = ReflectUtil.getMethod(nmsEntityTypes, "getName", nmsEntityTypes);
        Class<?> mcKeyClazz = ReflectUtil.getNMSClass("MinecraftKey");
        mkGetKey = ReflectUtil.getMethod(mcKeyClazz, "getKey");

        try {
            entityTypeRegistry = ReflectUtil.getField(
                    ReflectUtil.getNMSClass("IRegistry"),
                    "ENTITY_TYPE"
            ).get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            entityTypeRegistry = null;
        }

        Class<?> registryBlocks = ReflectUtil.getNMSClass("RegistryBlocks");
        getByID = ReflectUtil.getMethod(registryBlocks, "fromId", int.class);
        getByKey = ReflectUtil.getMethod(registryBlocks, "get", mcKeyClazz);

        cnkGetKey = ReflectUtil.getMethod(
                ReflectUtil.getCBClass("util.CraftNamespacedKey"),
                "toMinecraft",
                NamespacedKey.class
        );
        getID = ReflectUtil.getMethod(registryBlocks, int.class);
    }

    public static void initialize(Map<EntityType, Integer> entitiesLoaded, Map<EntityType, Integer> objectsLoaded) {
        OBJECTS.putAll(objectsLoaded);
        ENTITIES.putAll(entitiesLoaded);
    }

    public static boolean isObject(EntityType type){
        return OBJECTS.containsKey(type);
    }

    public static EntityType read(PacketContainer c, int etIndex, int intIndex, boolean object) {
        try {
            StructureModifier<?> modifier = nmsEntityTypes != null ? c.getSpecificModifier(nmsEntityTypes) : null;
            if (modifier == null || modifier.size() == 0 || etIndex < 0) {
                int type = c.getIntegers().read(intIndex);
                if (ENTITIES.isEmpty()) {
                    return fromNMS(getByID.invoke(entityTypeRegistry, type));
                } else {
                    if(object && OBJECTS.containsValue(type)) {
                        return OBJECTS.inverse().get(type);
                    }
                    return ENTITIES.inverse().get(type);
                }
            } else {
                // Read an EntityTypes object from the modifier
                return fromNMS(modifier.read(etIndex));
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void write(EntityType type, PacketContainer c, int etIndex, int intIndex, boolean object) {
        try {
            StructureModifier modifier = nmsEntityTypes != null ? c.getSpecificModifier(nmsEntityTypes) : null;
            if (modifier == null || modifier.size() == 0 || etIndex < 0) {
                if (ENTITIES.isEmpty()) {
                    c.getIntegers().write(
                            intIndex,
                            (int) getID.invoke(entityTypeRegistry, toNMS(type))
                    );
                } else {
                    int id = -1;
                    if(object && OBJECTS.containsKey(type)) id = OBJECTS.get(type);
                    id = id < 0 ? ENTITIES.get(type) : id;
                    c.getIntegers().write(intIndex, id);
                }
            } else {
                //noinspection unchecked
                modifier.write(etIndex, toNMS(type));
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static EntityType fromNMS(Object o) throws InvocationTargetException, IllegalAccessException {
        //noinspection deprecation
        return EntityType.fromName((String) mkGetKey.invoke(etGetName.invoke(null, o)));
    }

    private static Object toNMS(EntityType type) throws InvocationTargetException, IllegalAccessException {
        return getByKey.invoke(entityTypeRegistry, cnkGetKey.invoke(null, type.getKey()));
    }

    public static boolean hasEntityType(PacketContainer c) {
        StructureModifier modifier = nmsEntityTypes != null ? c.getSpecificModifier(nmsEntityTypes) : null;
        return modifier != null && modifier.size() > 0;
    }
}
