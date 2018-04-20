package net.blitzcube.peapi.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.blitzcube.peapi.api.entity.IEntityModifier;
import net.blitzcube.peapi.api.entity.IEntityModifierRegistry;
import net.blitzcube.peapi.entity.loader.EntityModifierLoader;
import net.blitzcube.peapi.entity.modifiers.GenericModifier;
import net.blitzcube.peapi.entity.modifiers.OptModifier;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by iso2013 on 4/18/2018.
 */
public class EntityModifierRegistry implements IEntityModifierRegistry {
    private final ImmutableMap<EntityType, ImmutableList<GenericModifier>> modifiers;

    public EntityModifierRegistry() {
        this.modifiers = EntityModifierLoader.getModifiers(this.getClass().getResourceAsStream("/EntityDataJSON.json"));
    }

    public List<IEntityModifier> lookup(EntityType type) {
        return new ArrayList<>(modifiers.get(type));
    }

    @Override
    public IEntityModifier lookup(EntityType type, String label) {
        return modifiers.get(type).stream()
                .filter(m -> m.getLabel().equalsIgnoreCase(label))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<IEntityModifier> lookup(EntityType type, Class field) {
        return modifiers.get(type).stream()
                .filter(m -> m instanceof OptModifier ?
                        field.equals(((OptModifier) m).getOptionalType()) :
                        field.equals(m.getFieldType()))
                .collect(Collectors.toList());
    }
}
