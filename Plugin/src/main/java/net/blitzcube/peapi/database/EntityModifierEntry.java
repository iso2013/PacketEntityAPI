package net.blitzcube.peapi.database;

import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import net.blitzcube.peapi.api.entity.wrappers.VillagerData;
import net.blitzcube.peapi.entity.modifier.modifiers.*;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pose;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Created by iso2013 on 10/11/19.
 */
public class EntityModifierEntry<T> {
    private final Class<? extends Entity> entityClass;
    private final String label;
    private final GenericModifier<T> modifier;
    private final Class<T> fieldType;
    private final int index;

    private EntityModifierEntry(Class<? extends Entity> entityClass, String label, GenericModifier<T> modifier,
                                Class<T> fieldType, int index) {
        this.entityClass = entityClass;
        this.label = label;
        this.modifier = modifier;
        this.fieldType = fieldType;
        this.index = index;
    }

    @SuppressWarnings("unchecked")
    static EntityModifierEntry create(String[] data, Logger logger) {
        Class<? extends Entity> entityClass;
        try {
            entityClass = (Class<? extends Entity>) Class.forName("org.bukkit.entity." + data[0]);
        } catch (ClassNotFoundException e) {
            try {
                entityClass = (Class<? extends Entity>) Class.forName("org.bukkit.entity.minecart." + data[0]);
            } catch (ClassNotFoundException e1) {
                logger.severe("Failed to locate class " + data[0] + " - skipping modifier entry!");
                return null;
            }
        }

        int index = Integer.valueOf(data[1]);

        GenericModifier modifier = getModifier(data[3], index, data[2], data.length > 4 ? data[4] : "", logger);
        if (modifier == null) return null;
        return new EntityModifierEntry(entityClass, data[2], modifier, modifier.getFieldType(), index);
    }

    private static GenericModifier getModifier(String type, int index, String label, String def, Logger logger) {
        switch (type) {
            case "Integer":
                return new GenericModifier<>(Integer.class, index, label, Integer.valueOf(def));
            case "OptChat":
                Optional<BaseComponent[]> realDef;
                if (def.equalsIgnoreCase("Absent")) {
                    realDef = Optional.empty();
                } else {
                    realDef = Optional.of(ComponentSerializer.parse(def));
                }
                return new OptChatModifier(index, label, realDef);
            case "Boolean":
                return new GenericModifier<>(Boolean.class, index, label, Boolean.valueOf(def));
            case "String":
                return new GenericModifier<>(String.class, index, label, def);
            case "Pose":
                return new PoseModifier(index, label, Pose.valueOf(def));
            case "ItemStack":
                return new ItemModifier(index, label, new ItemStack(Material.AIR));
            case "Location":
                return new PositionModifier(index, label, new Vector());
            case "Float":
                return new GenericModifier<>(Float.class, index, label, Float.valueOf(def));
            case "Particle":
                return new ParticleModifier(index, label, Particle.valueOf(def));
            case "OptUUID":
                return new OptUUIDModifier(index, label, Optional.empty());
            case "Byte":
                return new GenericModifier<>(Byte.class, index, label, Byte.valueOf(def));
            case "OptLocation":
                Optional<Vector> realLocDef;
                if (def.equalsIgnoreCase("Absent")) {
                    realLocDef = Optional.empty();
                } else {
                    def = def.substring(1, def.length() - 1);
                    String[] vals = def.split(",");
                    float x = Float.valueOf(vals[0]);
                    float y = Float.valueOf(vals[1]);
                    float z = Float.valueOf(vals[2]);
                    realLocDef = Optional.of(new Vector(x, y, z));
                }
                return new OptPositionModifier(index, label, realLocDef);
            case "NBTTag":
                NbtCompound realNbtDef;
                if (def.equalsIgnoreCase("null")) {
                    realNbtDef = null;
                } else {
                    realNbtDef = NbtFactory.ofCompound(def);
                }
                return new NbtCompoundModifier(index, label, realNbtDef);
            case "EulerAngle":
                def = def.substring(1, def.length() - 1);
                String[] vals = def.split(",");
                float x = Float.valueOf(vals[0]);
                float y = Float.valueOf(vals[1]);
                float z = Float.valueOf(vals[2]);
                return new EulerAngleModifier(index, label, new EulerAngle(x, y, z));
            case "VillagerData":
                return new VillagerDataModifier(index, label, new VillagerData());
            case "Direction":
                return new DirectionModifier(index, label, BlockFace.valueOf(def.toUpperCase().trim()));
            case "ByteMask":
                return new ByteBitmaskModifier(index, (byte) 15, label, (byte) 0);
            case "OptBlockData":
                return new OptBlockModifier(index, label, Optional.empty());
            case "Chat":
                return new ChatModifier(index, label, "{\"text\":\"\"}");
            case "Color":
                return new ColorModifier(index, label, -1);
        }
        if (type.startsWith("Mask")) {
            int mask = Integer.valueOf(type.substring(4));
            return new BitmaskModifier(index, (byte) mask, label, Byte.valueOf(def));
        }

        logger.severe("Warning: failed to find suitable modifier for " + type + ".");
        return null;
    }

    public Class<? extends Entity> getEntityClass() {
        return entityClass;
    }

    public String getLabel() {
        return label;
    }

    public GenericModifier<T> getModifier() {
        return modifier;
    }

    public Class<?> getFieldType() {
        return fieldType;
    }

    public int getIndex() {
        return index;
    }
}
