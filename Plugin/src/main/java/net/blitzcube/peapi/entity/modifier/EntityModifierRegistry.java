package net.blitzcube.peapi.entity.modifier;

import net.blitzcube.peapi.api.entity.modifier.IEntityModifier;
import net.blitzcube.peapi.api.entity.modifier.IEntityModifierRegistry;
import net.blitzcube.peapi.database.EntityModifierEntry;
import net.blitzcube.peapi.entity.modifier.modifiers.*;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.DyeColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.*;

/**
 * Created by iso2013 on 4/18/2018.
 */
@SuppressWarnings("unchecked")
public class EntityModifierRegistry implements IEntityModifierRegistry {
    private final List<EntityModifierEntry<?>> modifiers;

    public EntityModifierRegistry(List<EntityModifierEntry<?>> modifiers) {
        this.modifiers = modifiers;
        Collections.reverse(modifiers);
    }

    @Override
    public List<IEntityModifier<?>> lookup(EntityType type) {
        if (type == null) return new ArrayList<>();
        return lookup(type.getEntityClass());
    }

    @Override
    public List<IEntityModifier<?>> lookup(Class<? extends Entity> type) {
        if (type == null) return new ArrayList<>();

        List<IEntityModifier<?>> out = new ArrayList<>();
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
    public IEntityModifier<?> lookup(EntityType type, String label) {
        if (type == null) return null;
        return lookup(type.getEntityClass(), label);
    }

    @Override
    public IEntityModifier<?> lookup(Class<? extends Entity> type, String label) {
        if (type == null) return null;

        for (EntityModifierEntry<?> e : modifiers) {
            if (e.getEntityClass().isAssignableFrom(type) && e.getLabel().equalsIgnoreCase(label))
                return e.getModifier();
        }
        return null;
    }

    @Override
    public <T> IEntityModifier<T> lookup(EntityType type, String label, Class<T> field) {
        if (type == null) return null;
        return lookup(type.getEntityClass(), label, field);
    }

    @Override
    public <T> IEntityModifier<T> lookup(Class<? extends Entity> type, String label, Class<T> field) {
        if (type == null) return null;

        for (EntityModifierEntry<?> e : modifiers) {
            if (e.getEntityClass().isAssignableFrom(type) && e.getLabel().equalsIgnoreCase(label)) {
                IEntityModifier<?> compatible = produceCompatible(e.getModifier(), field, false);
                if (compatible != null) return (IEntityModifier<T>) compatible;
            }
        }
        return null;
    }

    @Override
    public <T> IEntityModifier<Optional<T>> lookupOptional(EntityType type, String label, Class<T> field) {
        if (type == null) return null;
        return lookupOptional(type.getEntityClass(), label, field);
    }

    @Override
    public <T> IEntityModifier<Optional<T>> lookupOptional(Class<? extends Entity> type, String label, Class<T> field) {
        if (type == null) return null;

        for (EntityModifierEntry<?> e : modifiers) {
            if (e.getEntityClass().isAssignableFrom(type) && e.getLabel().equalsIgnoreCase(label)) {
                IEntityModifier<?> compatible = produceCompatible(e.getModifier(), field, true);
                if (compatible != null) return (IEntityModifier<Optional<T>>) compatible;
            }
        }
        return null;
    }

    @Override
    public <T> List<IEntityModifier<T>> lookup(EntityType type, Class<T> field) {
        if (type == null) return new ArrayList<>();
        return lookup(type.getEntityClass(), field);
    }

    @Override
    public <T> List<IEntityModifier<T>> lookup(Class<? extends Entity> type, Class<T> field) {
        if (type == null) return new ArrayList<>();

        List<IEntityModifier<T>> out = new ArrayList<>();
        Map<Integer, Class<? extends Entity>> contained = new HashMap<>();

        for (EntityModifierEntry<?> e : modifiers) {
            if (!e.getEntityClass().isAssignableFrom(type)) continue;

            if (contained.containsKey(e.getIndex()) && contained.get(e.getIndex()) != e.getEntityClass()) continue;
            contained.put(e.getIndex(), e.getEntityClass());

            IEntityModifier<?> produced = produceCompatible(e.getModifier(), field, false);
            if (produced == null) continue;

            out.add((IEntityModifier<T>) produced);
        }

        Collections.reverse(out);
        return out;
    }

    @Override
    public <T> List<IEntityModifier<Optional<T>>> lookupOptional(EntityType type, Class<T> field) {
        if (type == null) return new ArrayList<>();
        return lookupOptional(type.getEntityClass(), field);
    }

    @Override
    public <T> List<IEntityModifier<Optional<T>>> lookupOptional(Class<? extends Entity> type, Class<T> field) {
        if (type == null) return new ArrayList<>();

        List<IEntityModifier<Optional<T>>> out = new ArrayList<>();
        Map<Integer, Class<? extends Entity>> contained = new HashMap<>();

        for (EntityModifierEntry<?> e : modifiers) {
            if (!e.getEntityClass().isAssignableFrom(type)) continue;

            if (contained.containsKey(e.getIndex()) && contained.get(e.getIndex()) != e.getEntityClass()) continue;
            contained.put(e.getIndex(), e.getEntityClass());

            IEntityModifier<?> produced = produceCompatible(e.getModifier(), field, true);
            if (produced == null) continue;

            out.add((IEntityModifier<Optional<T>>) produced);
        }

        Collections.reverse(out);
        return out;
    }

    private <T> IEntityModifier<?> produceCompatible(GenericModifier<?> input, Class<T> required, boolean optional) {
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
