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
import java.util.Optional;
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
    @SuppressWarnings("unchecked")
    public <T> IEntityModifier<T> lookup(EntityType type, String label, Class<? extends T> field) {
        return modifiers.get(type).stream()
                .filter(modifier -> {
                    if ((modifier instanceof OptModifier)) return false;
                    if (!modifier.getLabel().equalsIgnoreCase(label)) return false;
                    return modifier.getFieldType() == field;
                })
                .findFirst()
                .orElse(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> IEntityModifier<Optional<T>> lookupOptional(EntityType type, String label, Class<? extends T> field) {
        return modifiers.get(type).stream()
                .filter(modifier -> {
                    if (!(modifier instanceof OptModifier)) return false;
                    if (!modifier.getLabel().equalsIgnoreCase(label)) return false;
                    return modifier.getFieldType() == field;
                })
                .findFirst()
                .orElse(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Set<IEntityModifier<T>> lookup(EntityType type, Class<? extends T> field) {
        return modifiers.get(type).stream()
                .filter(genericModifier -> !(genericModifier instanceof OptModifier))
                .filter(m -> field.equals(m.getFieldType())
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

    @Override
    @SuppressWarnings("unchecked")
    public <T> Set<IEntityModifier<Optional<T>>> lookupOptional(EntityType type, Class<? extends T> field) {
        return modifiers.get(type).stream()
                .filter(genericModifier -> (genericModifier instanceof OptModifier))
                .filter(m -> field.equals(((OptModifier) m).getOptionalType())
                ).collect(new Collector<GenericModifier, HashSet<IEntityModifier<Optional<T>>>,
                        Set<IEntityModifier<Optional<T>>>>() {
                    @Override
                    public Supplier<HashSet<IEntityModifier<Optional<T>>>> supplier() {
                        return HashSet::new;
                    }

                    @Override
                    public BiConsumer<HashSet<IEntityModifier<Optional<T>>>, GenericModifier> accumulator() {
                        return Set::add;
                    }

                    @Override
                    public BinaryOperator<HashSet<IEntityModifier<Optional<T>>>> combiner() {
                        return (left, right) -> {
                            left.addAll(right);
                            return left;
                        };
                    }

                    @Override
                    public Function<HashSet<IEntityModifier<Optional<T>>>, Set<IEntityModifier<Optional<T>>>> finisher() {
                        return s -> s;
                    }

                    @Override
                    public Set<Characteristics> characteristics() {
                        return new HashSet<>();
                    }
                });
    }
}
