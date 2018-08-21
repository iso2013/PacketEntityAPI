package net.blitzcube.peapi.entity.modifier.modifiers;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.blitzcube.peapi.api.entity.modifier.IModifiableEntity;
import org.bukkit.block.BlockFace;

/**
 * Created by iso2013 on 4/20/2018.
 */
public class DirectionModifier extends GenericModifier<BlockFace> {
    private final WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.getDirectionSerializer();

    public DirectionModifier(int index, String label, EnumWrappers.Direction def) {
        super(null, index, label, fromWrapped(def));
    }

    private static BlockFace fromWrapped(EnumWrappers.Direction wrapped) {
        if (wrapped == null) return null;
        switch (wrapped) {
            case DOWN:
                return BlockFace.DOWN;
            case UP:
                return BlockFace.UP;
            case NORTH:
                return BlockFace.NORTH;
            case SOUTH:
                return BlockFace.SOUTH;
            case WEST:
                return BlockFace.WEST;
            case EAST:
                return BlockFace.EAST;
        }
        return null;
    }

    @Override
    public BlockFace getValue(IModifiableEntity target) {
        return fromWrapped((EnumWrappers.Direction) target.read(super.index));
    }

    @Override
    public void setValue(IModifiableEntity target, BlockFace newValue) {
        if (newValue != null) {
            EnumWrappers.Direction d;
            switch (newValue) {
                case DOWN:
                    d = EnumWrappers.Direction.DOWN;
                    break;
                case UP:
                    d = EnumWrappers.Direction.UP;
                    break;
                case NORTH:
                    d = EnumWrappers.Direction.NORTH;
                    break;
                case SOUTH:
                    d = EnumWrappers.Direction.SOUTH;
                    break;
                case WEST:
                    d = EnumWrappers.Direction.WEST;
                    break;
                case EAST:
                    d = EnumWrappers.Direction.EAST;
                    break;
                default:
                    throw new IllegalArgumentException("Incorrect value for block face!");
            }
            target.write(super.index, EnumWrappers.getDirectionConverter().getGeneric(d), serializer);
        } else super.unsetValue(target);
    }

    @Override
    public Class<?> getFieldType() {
        return BlockFace.class;
    }
}
