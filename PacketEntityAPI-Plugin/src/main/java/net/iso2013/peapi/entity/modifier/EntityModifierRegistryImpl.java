package net.iso2013.peapi.entity.modifier;

import net.iso2013.peapi.api.entity.modifier.EntityModifier;
import net.iso2013.peapi.api.entity.modifier.EntityModifierRegistry;
import net.iso2013.peapi.database.EntityModifierEntry;
import net.iso2013.peapi.entity.modifier.modifiers.*;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.DyeColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.*;

/**
 * Created by iso2013 on 4/18/2018.
 */
@SuppressWarnings("unchecked")
public class EntityModifierRegistryImpl implements EntityModifierRegistry {
    private final List<EntityModifierEntry<?>> modifiers;

    public EntityModifierRegistryImpl(List<EntityModifierEntry<?>> modifiers) {
        this.modifiers = modifiers;
        Collections.reverse(modifiers);
    }

    @Override
    public List<EntityModifier<?>> lookup(EntityType type) {
        if (type == null) return new ArrayList<>();
        return lookup(type.getEntityClass());
    }

    @Override
    public List<EntityModifier<?>> lookup(Class<? extends Entity> type) {
        if (type == null) return new ArrayList<>();

        List<EntityModifier<?>> out = new ArrayList<>();
        Map<Integer, Class<? extends Entity>> contained = new HashMap<>();

        for (EntityModifierEntry<?> e : modifiers) {
            if (!e.getEntityClass().isAssignableFrom(type)) continue;

            if (contained.containsKey(e.getIndex()) && contained.get(e.getIndex()) != e.getEntityClass()) continue;
            contained.put(e.getIndex(), e.getEntityClass());

            out.add(e.getModifier());
        }

        Collections.reverse(out);
        return out;
    }

    @Override
    public EntityModifier<?> lookup(EntityType type, String label) {
        if (type == null) return null;
        return lookup(type.getEntityClass(), label);
    }

    @Override
    public EntityModifier<?> lookup(Class<? extends Entity> type, String label) {
        if (type == null) return null;

        for (EntityModifierEntry<?> e : modifiers) {
            if (e.getEntityClass().isAssignableFrom(type) && e.getLabel().equalsIgnoreCase(label))
                return e.getModifier();
        }
        return null;
    }

    @Override
    public <T> EntityModifier<T> lookup(EntityType type, String label, Class<T> field) {
        if (type == null) return null;
        return lookup(type.getEntityClass(), label, field);
    }

    @Override
    public <T> EntityModifier<T> lookup(Class<? extends Entity> type, String label, Class<T> field) {
        if (type == null) return null;

        for (EntityModifierEntry<?> e : modifiers) {
            if (e.getEntityClass().isAssignableFrom(type) && e.getLabel().equalsIgnoreCase(label)) {
                EntityModifier<?> compatible = produceCompatible(e.getModifier(), field, false);
                if (compatible != null) return (EntityModifier<T>) compatible;
            }
        }
        return null;
    }

    @Override
    public <T> EntityModifier<Optional<T>> lookupOptional(EntityType type, String label, Class<T> field) {
        if (type == null) return null;
        return lookupOptional(type.getEntityClass(), label, field);
    }

    @Override
    public <T> EntityModifier<Optional<T>> lookupOptional(Class<? extends Entity> type, String label, Class<T> field) {
        if (type == null) return null;

        for (EntityModifierEntry<?> e : modifiers) {
            if (e.getEntityClass().isAssignableFrom(type) && e.getLabel().equalsIgnoreCase(label)) {
                EntityModifier<?> compatible = produceCompatible(e.getModifier(), field, true);
                if (compatible != null) return (EntityModifier<Optional<T>>) compatible;
            }
        }
        return null;
    }

    @Override
    public <T> List<EntityModifier<T>> lookup(EntityType type, Class<T> field) {
        if (type == null) return new ArrayList<>();
        return lookup(type.getEntityClass(), field);
    }

    @Override
    public <T> List<EntityModifier<T>> lookup(Class<? extends Entity> type, Class<T> field) {
        if (type == null) return new ArrayList<>();

        List<EntityModifier<T>> out = new ArrayList<>();
        Map<Integer, Class<? extends Entity>> contained = new HashMap<>();

        for (EntityModifierEntry<?> e : modifiers) {
            if (!e.getEntityClass().isAssignableFrom(type)) continue;

            if (contained.containsKey(e.getIndex()) && contained.get(e.getIndex()) != e.getEntityClass()) continue;
            contained.put(e.getIndex(), e.getEntityClass());

            EntityModifier<?> produced = produceCompatible(e.getModifier(), field, false);
            if (produced == null) continue;

            out.add((EntityModifier<T>) produced);
        }

        Collections.reverse(out);
        return out;
    }

    @Override
    public <T> List<EntityModifier<Optional<T>>> lookupOptional(EntityType type, Class<T> field) {
        if (type == null) return new ArrayList<>();
        return lookupOptional(type.getEntityClass(), field);
    }

    @Override
    public <T> List<EntityModifier<Optional<T>>> lookupOptional(Class<? extends Entity> type, Class<T> field) {
        if (type == null) return new ArrayList<>();

        List<EntityModifier<Optional<T>>> out = new ArrayList<>();
        Map<Integer, Class<? extends Entity>> contained = new HashMap<>();

        for (EntityModifierEntry<?> e : modifiers) {
            if (!e.getEntityClass().isAssignableFrom(type)) continue;

            if (contained.containsKey(e.getIndex()) && contained.get(e.getIndex()) != e.getEntityClass()) continue;
            contained.put(e.getIndex(), e.getEntityClass());

            EntityModifier<?> produced = produceCompatible(e.getModifier(), field, true);
            if (produced == null) continue;

            out.add((EntityModifier<Optional<T>>) produced);
        }

        Collections.reverse(out);
        return out;
    }

    private <T> EntityModifier<?> produceCompatible(GenericModifier<?> input, Class<T> required, boolean optional) {
        Class<?> modifierType = input.getFieldType();
        if (optional && input instanceof OptModifier<?>) {
            if (required.isAssignableFrom(((OptModifier) input).getOptionalType())) {
                return input;
            }
        } else {
            if (required.isAssignableFrom(modifierType) && modifierType != Optional.class) return input;

            if (isStringType(input) && required.isAssignableFrom(String.class)) {
                if (input instanceof OptModifier<?>) {
                    return new PseudoStringModifierOpt((OptChatModifier) input);
                } else {
                    return new PseudoStringModifier((ChatModifier) input);
                }
            } else if (isColorType(input) && required.isAssignableFrom(DyeColor.class)) {
                if (modifierType == Byte.class) {
                    return new PseudoDyeColorModifier.ForByte((GenericModifier<Byte>) input);
                } else if (modifierType == Integer.class) {
                    return new PseudoDyeColorModifier.ForInt((GenericModifier<Integer>) input);
                }
            }
        }
        return null;
    }

    private boolean isStringType(GenericModifier<?> input) {
        if (input.getFieldType() == BaseComponent[].class) return true;
        if (input.getFieldType() == Optional.class) {
            return ((OptModifier<?>) input).getOptionalType() == BaseComponent[].class;
        }
        return false;
    }

    private boolean isColorType(GenericModifier<?> input) {
        return input.getFieldType() == Byte.class || input.getFieldType() == Integer.class;
    }
}
