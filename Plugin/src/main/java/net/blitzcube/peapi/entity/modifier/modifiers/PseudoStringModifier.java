package net.blitzcube.peapi.entity.modifier.modifiers;

import net.blitzcube.peapi.api.entity.modifier.IModifiableEntity;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Optional;

/**
 * Created by iso2013 on 8/20/2018.
 */
public class PseudoStringModifier extends GenericModifier<String> {
    private final OptChatModifier internal;

    public PseudoStringModifier(OptChatModifier internal) {
        super(String.class, internal.index, internal.label, fromComponentOptional(internal.def));
        this.internal = internal;
    }

    private static String fromComponentOptional(Optional<BaseComponent[]> components) {
        return components.map(TextComponent::toLegacyText).orElse(null);
    }

    @Override
    public String getValue(IModifiableEntity target) {
        return fromComponentOptional(internal.getValue(target));
    }

    @Override
    public void setValue(IModifiableEntity target, String newValue) {
        internal.setValue(target, Optional.of(TextComponent.fromLegacyText(newValue)));
    }
}
