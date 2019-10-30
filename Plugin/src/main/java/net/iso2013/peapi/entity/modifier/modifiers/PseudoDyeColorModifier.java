package net.iso2013.peapi.entity.modifier.modifiers;

import net.iso2013.peapi.api.entity.modifier.ModifiableEntity;
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
        public DyeColor getValue(ModifiableEntity target) {
            //noinspection deprecation
            return DyeColor.getByDyeData(internal.getValue(target).byteValue());
        }

        @Override
        public void setValue(ModifiableEntity target, DyeColor newValue) {
            //noinspection deprecation
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
        public DyeColor getValue(ModifiableEntity target) {
            //noinspection deprecation
            return DyeColor.getByDyeData(internal.getValue(target));
        }

        @Override
        public void setValue(ModifiableEntity target, DyeColor newValue) {
            //noinspection deprecation
            internal.setValue(target, newValue.getDyeData());
        }

        @Override
        public Class<?> getFieldType() {
            return DyeColor.class;
        }
    }
}
