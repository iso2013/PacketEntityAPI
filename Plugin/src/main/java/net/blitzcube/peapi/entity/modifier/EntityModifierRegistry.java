package net.blitzcube.peapi.entity.modifier;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.blitzcube.peapi.api.entity.modifier.IEntityModifier;
import net.blitzcube.peapi.api.entity.modifier.IEntityModifierRegistry;
import net.blitzcube.peapi.entity.modifier.loader.EntityModifierLoader;
import net.blitzcube.peapi.entity.modifier.modifiers.GenericModifier;
import net.blitzcube.peapi.entity.modifier.modifiers.OptModifier;
import org.bukkit.entity.EntityType;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by iso2013 on 4/18/2018.
 */
public class EntityModifierRegistry implements IEntityModifierRegistry {
    private final ImmutableMap<EntityType, ImmutableSet<GenericModifier>> modifiers;

    public EntityModifierRegistry() {
        this.modifiers = EntityModifierLoader.getModifiers(this.getClass().getResourceAsStream("/EntityDataJSON.json"));
    }

    public Set<IEntityModifier> lookup(EntityType type) {
        return new HashSet<>((modifiers.get(type)));
    }

    @Override
    public IEntityModifier lookup(EntityType type, String label) {
        return modifiers.get(type).stream()
                .filter(m -> m.getLabel().equalsIgnoreCase(label))
                .findFirst()
                .orElse(null);
    }

    @Override
    public <T> Set<IEntityModifier<T>> lookup(EntityType type, Class<? extends T> field) {
        return lookup(type, field, false);
    }

    @Override
    public <T> IEntityModifier<T> lookup(EntityType type, String label, Class<? extends T> field) {
        return lookup(type, label, field, false);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> IEntityModifier<T> lookup(EntityType type, String label, Class<? extends T> field, boolean optional) {
        return modifiers.get(type).stream()
                .filter(genericModifier -> (optional && genericModifier instanceof OptModifier) ||
                        (!optional && !(genericModifier instanceof OptModifier)))
                .filter(m -> m.getLabel().equalsIgnoreCase(label))
                .findFirst()
                .orElse(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Set<IEntityModifier<T>> lookup(EntityType type, Class<? extends T> field, boolean optional) {
        return modifiers.get(type).stream()
                .filter(genericModifier -> (optional && genericModifier instanceof OptModifier) ||
                        (!optional && !(genericModifier instanceof OptModifier)))
                .filter(m -> optional ? field.equals(((OptModifier) m).getOptionalType()) :
                        field.equals(m.getFieldType())
                ).collect(Collectors.toSet());
    }
}
