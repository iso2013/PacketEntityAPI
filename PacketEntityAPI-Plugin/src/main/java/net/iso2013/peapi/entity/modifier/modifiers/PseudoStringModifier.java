package net.iso2013.peapi.entity.modifier.modifiers;

import net.iso2013.peapi.api.entity.modifier.ModifiableEntity;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Created by iso2013 on 10/12/19.
 */
public class PseudoStringModifier extends GenericModifier<String> {
    private final ChatModifier internal;

    public PseudoStringModifier(ChatModifier internal) {
        super(String.class, internal.index, internal.label, fromComponent(internal.def));
        this.internal = internal;
    }

    private static String fromComponent(BaseComponent[] components) {
        return components != null ? TextComponent.toLegacyText(components) : null;
    }

    @Override
    public String getValue(ModifiableEntity target) {
        return fromComponent(internal.getValue(target));
    }

    @Override
    public void setValue(ModifiableEntity target, String newValue) {
        internal.setValue(target, TextComponent.fromLegacyText(newValue));
    }
}
