package net.blitzcube.peapi.entity.modifier.modifiers;

import net.blitzcube.peapi.api.entity.modifier.IModifiableEntity;
import org.bukkit.DyeColor;

/**
 * Created by iso2013 on 10/12/19.
 */
public class PseudoDyeColorModifier {
    public static class ForInt extends GenericModifier<DyeColor> {
        private final GenericModifier<Integer> internal;

        public ForInt(GenericModifier<Integer> internal) {
            super(null, internal.index, internal.label, DyeColor.BLACK);
            this.internal = internal;
        }

        @Override
        public DyeColor getValue(IModifiableEntity target) {
            return DyeColor.getByDyeData(internal.getValue(target).byteValue());
        }

        @Override
        public void setValue(IModifiableEntity target, DyeColor newValue) {
            internal.setValue(target, (int) newValue.getDyeData());
        }

        @Override
        public Class<?> getFieldType() {
            return DyeColor.class;
        }
    }

    public static class ForByte extends GenericModifier<DyeColor> {
        private final GenericModifier<Byte> internal;

        public ForByte(GenericModifier<Byte> internal) {
            super(null, internal.index, internal.label, DyeColor.BLACK);
            this.internal = internal;
        }

        @Override
        public DyeColor getValue(IModifiableEntity target) {
            return DyeColor.getByDyeData(internal.getValue(target));
        }

        @Override
        public void setValue(IModifiableEntity target, DyeColor newValue) {
            internal.setValue(target, newValue.getDyeData());
        }

        @Override
        public Class<?> getFieldType() {
            return DyeColor.class;
        }
    }
}
