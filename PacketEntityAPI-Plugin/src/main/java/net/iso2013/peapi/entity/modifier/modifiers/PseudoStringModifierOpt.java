package net.iso2013.peapi.entity.modifier.modifiers;

import net.iso2013.peapi.api.entity.modifier.ModifiableEntity;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Optional;

/**
 * Created by iso2013 on 8/20/2018.
 */
public class PseudoStringModifierOpt extends GenericModifier<String> {
    private final OptChatModifier internal;

    public PseudoStringModifierOpt(OptChatModifier internal) {
        super(String.class, internal.index, internal.label, fromComponentOptional(internal.def));
        this.internal = internal;
    }

    private static String fromComponentOptional(Optional<BaseComponent[]> components) {
        return components.map(TextComponent::toLegacyText).orElse(null);
    }

    @Override
    public String getValue(ModifiableEntity target) {
        return fromComponentOptional(internal.getValue(target));
    }

    @Override
    public void setValue(ModifiableEntity target, String newValue) {
        internal.setValue(target, Optional.of(TextComponent.fromLegacyText(newValue)));
    }
}
