package net.blitzcube.peapi.entity.loader.tree;

import net.blitzcube.peapi.entity.modifiers.BitmaskModifier;
import net.blitzcube.peapi.entity.modifiers.ByteBitmaskModifier;
import net.blitzcube.peapi.entity.modifiers.GenericModifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iso2013 on 4/18/2018.
 */
public class Bitmask extends Node.Attribute {
    private final List<Key> keys = new ArrayList<>();
    private int index;
    private byte def;

    public Bitmask(int index, byte def) {
        this.index = index;
        this.def = def;
    }

    public int getIndex() {
        return index;
    }

    public byte getDef() {
        return def;
    }

    public List<Key> getKeys() {
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
        private byte mask;
        private String label;

        public Key(byte mask, String label) {
            this.mask = mask;
            this.label = label;
        }

        public byte getMask() {
            return mask;
        }

        public String getLabel() {
            return label;
        }
    }
}
