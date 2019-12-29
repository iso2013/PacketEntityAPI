package net.iso2013.peapi.entity.modifier.modifiers;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.iso2013.peapi.api.entity.modifier.ModifiableEntity;
import org.bukkit.inventory.ItemStack;

/**
 * Created by iso2013 on 4/18/2018.
 */
public class ItemModifier extends GenericModifier<ItemStack> {
    private final WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.getItemStackSerializer(false);

    public ItemModifier(int index, String label, ItemStack def) {
        super(null, index, label, def);
    }

    @Override
    public ItemStack getValue(ModifiableEntity target) {
        return MinecraftReflection.getBukkitItemStack(target.read(super.index));
    }

    @Override
    public void setValue(ModifiableEntity target, ItemStack newValue) {
        target.write(
                super.index,
                MinecraftReflection.getMinecraftItemStack(newValue),
                serializer
        );
    }

    @Override
    public Class<?> getFieldType() {
        return ItemStack.class;
    }
}
