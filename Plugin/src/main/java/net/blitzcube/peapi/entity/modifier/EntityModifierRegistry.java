package net.blitzcube.peapi.entity.modifier;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.blitzcube.peapi.api.entity.modifier.IEntityModifier;
import net.blitzcube.peapi.api.entity.modifier.IEntityModifierRegistry;
import net.blitzcube.peapi.entity.modifier.loader.EntityModifierLoader;
import net.blitzcube.peapi.entity.modifier.modifiers.GenericModifier;
import net.blitzcube.peapi.entity.modifier.modifiers.OptChatModifier;
import net.blitzcube.peapi.entity.modifier.modifiers.OptModifier;
import net.blitzcube.peapi.entity.modifier.modifiers.PseudoStringModifier;
import org.bukkit.entity.EntityType;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Created by iso2013 on 4/18/2018.
 */
public class EntityModifierRegistry implements IEntityModifierRegistry {
    private final ImmutableMap<EntityType, ImmutableSet<GenericModifier>> modifiers;

    public EntityModifierRegistry() {
        this.modifiers = EntityModifierLoader.getModifiers(this.getClass().getResourceAsStream("/structure.json"));
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
                .filter(modifier -> {
                    boolean instOfOptional = modifier instanceof OptModifier;
                    boolean stringException = field == String.class && modifier instanceof OptChatModifier;
                    if (!((optional == instOfOptional) || stringException)) return false;
                    if (!modifier.getLabel().equalsIgnoreCase(label)) return false;
                    if (field != String.class) {
                        return modifier.getFieldType() == field;
                    } else {
                        return modifier.getFieldType() == field || modifier instanceof OptChatModifier;
                    }
                })
                .map(genericModifier -> {
                    if (genericModifier instanceof OptChatModifier && field == String.class) {
                        return new PseudoStringModifier((OptChatModifier) genericModifier);
                    }
                    return genericModifier;
                })
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
                ).collect(new Collector<GenericModifier, HashSet<IEntityModifier<T>>, Set<IEntityModifier<T>>>() {
                    @Override
                    public Supplier<HashSet<IEntityModifier<T>>> supplier() {
                        return HashSet::new;
                    }

                    @Override
                    public BiConsumer<HashSet<IEntityModifier<T>>, GenericModifier> accumulator() {
                        return Set::add;
                    }

                    @Override
                    public BinaryOperator<HashSet<IEntityModifier<T>>> combiner() {
                        return (left, right) -> {
                            left.addAll(right);
                            return left;
                        };
                    }

                    @Override
                    public Function<HashSet<IEntityModifier<T>>, Set<IEntityModifier<T>>> finisher() {
                        return s -> s;
                    }

                    @Override
                    public Set<Characteristics> characteristics() {
                        return new HashSet<>();
                    }
                });
    }
}
