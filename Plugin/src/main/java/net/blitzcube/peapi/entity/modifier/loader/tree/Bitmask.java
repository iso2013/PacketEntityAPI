package net.blitzcube.peapi.entity.modifier.loader.tree;

import net.blitzcube.peapi.entity.modifier.modifiers.BitmaskModifier;
import net.blitzcube.peapi.entity.modifier.modifiers.ByteBitmaskModifier;
import net.blitzcube.peapi.entity.modifier.modifiers.GenericModifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iso2013 on 4/18/2018.
 */
public class Bitmask extends Node.Attribute {
    private final List<Key> keys = new ArrayList<>();
    private final int index;
    private final byte def;

    Bitmask(int index, byte def) {
        this.index = index;
        this.def = def;
    }

    List<Key> getKeys() {
        return keys;
    }

    @Override
    public List<GenericModifier> asGenericModifier() {
        List<GenericModifier> modifiers = new ArrayList<>();
        for (Key k : keys) {
            if (Integer.toBinaryString(k.mask).replace(" ", "").length() > 1) {
                modifiers.add(new ByteBitmaskModifier(index, k.mask, k.label, def));
            } else {
                modifiers.add(new BitmaskModifier(index, k.mask, k.label, def));
            }
        }
        return modifiers;
    }

    public static class Key {
        private final byte mask;
        private final String label;

        Key(byte mask, String label) {
            this.mask = mask;
            this.label = label;
        }
    }
}
